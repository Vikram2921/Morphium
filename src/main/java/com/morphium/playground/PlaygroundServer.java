package com.morphium.playground;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PlaygroundServer {
    private static final Logger LOGGER = Logger.getLogger(PlaygroundServer.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final MorphiumEngine engine = new MorphiumEngine();
    
    // Configuration
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    private static final int MAX_REQUEST_SIZE = 10 * 1024 * 1024; // 10MB
    private static final int TRANSFORM_TIMEOUT_MS = 30000; // 30 seconds
    
    private final int port;
    private HttpServer server;

    public PlaygroundServer(int port) {
        this.port = port;
        setupLogging();
    }

    private void setupLogging() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(false);
    }

    public static void main(String[] args) {
        int port = parsePort(args);
        PlaygroundServer playgroundServer = new PlaygroundServer(port);
        
        try {
            playgroundServer.start();
        } catch (Exception e) {
            LOGGER.severe("Failed to start server: " + e.getMessage());
            System.exit(1);
        }
    }

    private static int parsePort(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid port number provided, using default: " + DEFAULT_PORT);
            }
        }
        return DEFAULT_PORT;
    }

    public void start() throws IOException {
        printBanner();
        
        Path resourcePath = Paths.get("src/main/resources");
        if (!resourcePath.toFile().exists()) {
            LOGGER.warning("Resource path does not exist: " + resourcePath.toAbsolutePath());
        }
        engine.getModuleResolver().setBasePath(resourcePath);
        
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", this::handleIndex);
        server.createContext("/api/transform", this::handleTransform);
        server.createContext("/api/health", this::handleHealth);
        
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        server.setExecutor(threadPoolExecutor);
        
        server.start();
        
        printServerInfo();
        
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private void printBanner() {
        System.out.println();
        System.out.println("  __  __                 _     _                 ");
        System.out.println(" |  \\/  | ___  _ __ _ __| |__ (_)_   _ _ __ ___  ");
        System.out.println(" | |\\/| |/ _ \\| '__| '_ \\ '_ \\| | | | | '_ ` _ \\ ");
        System.out.println(" | |  | | (_) | |  | |_) | | | | | |_| | | | | | |");
        System.out.println(" |_|  |_|\\___/|_|  | .__/|_| |_|_|\\__,_|_| |_| |_|");
        System.out.println("                   |_|                            ");
        System.out.println();
        System.out.println(" Playground Server v1.0.0");
        System.out.println(" Professional JSON Transformation DSL");
        System.out.println();
    }

    private void printServerInfo() {
        LOGGER.info("=".repeat(60));
        LOGGER.info("Server started successfully");
        LOGGER.info("=".repeat(60));
        LOGGER.info("  Server URL:        http://localhost:" + port);
        LOGGER.info("  Health Check:      http://localhost:" + port + "/api/health");
        LOGGER.info("  Transform API:     http://localhost:" + port + "/api/transform");
        LOGGER.info("=".repeat(60));
        LOGGER.info("Configuration:");
        LOGGER.info("  Thread Pool Size:  " + THREAD_POOL_SIZE);
        LOGGER.info("  Max Request Size:  " + (MAX_REQUEST_SIZE / 1024 / 1024) + "MB");
        LOGGER.info("  Transform Timeout: " + (TRANSFORM_TIMEOUT_MS / 1000) + "s");
        LOGGER.info("=".repeat(60));
        LOGGER.info("Server is ready. Press Ctrl+C to stop.");
        LOGGER.info("=".repeat(60));
    }

    private void shutdown() {
        if (server != null) {
            LOGGER.info("Shutting down server...");
            server.stop(2);
            LOGGER.info("Server stopped successfully");
        }
    }

    private void handleIndex(HttpExchange exchange) throws IOException {
        long startTime = System.currentTimeMillis();
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        
        try {
            String html = PlaygroundHtml.getHtml();
            sendResponse(exchange, 200, html.getBytes(StandardCharsets.UTF_8), "text/html; charset=UTF-8");
            
            logRequest(exchange, 200, startTime, clientIp);
        } catch (Exception e) {
            LOGGER.severe("Error handling index request: " + e.getMessage());
            sendErrorResponse(exchange, 500, "Internal server error");
            logRequest(exchange, 500, startTime, clientIp);
        }
    }

    private void handleTransform(HttpExchange exchange) throws IOException {
        long startTime = System.currentTimeMillis();
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJsonError(exchange, 405, "Method not allowed");
            logRequest(exchange, 405, startTime, clientIp);
            return;
        }

        try {
            byte[] requestBody = exchange.getRequestBody().readAllBytes();
            
            if (requestBody.length > MAX_REQUEST_SIZE) {
                sendJsonError(exchange, 413, "Request too large. Maximum size: " + (MAX_REQUEST_SIZE / 1024 / 1024) + "MB");
                logRequest(exchange, 413, startTime, clientIp);
                return;
            }
            
            String body = new String(requestBody, StandardCharsets.UTF_8);
            JsonNode request = mapper.readTree(body);
            
            if (!request.has("transform") || !request.has("input")) {
                sendJsonError(exchange, 400, "Missing required fields: 'transform' and 'input'");
                logRequest(exchange, 400, startTime, clientIp);
                return;
            }
            
            String transform = request.get("transform").asText();
            String inputJson = request.get("input").asText();
            
            if (transform.isEmpty() || inputJson.isEmpty()) {
                sendJsonError(exchange, 400, "Transform and input cannot be empty");
                logRequest(exchange, 400, startTime, clientIp);
                return;
            }
            
            JsonNode input = mapper.readTree(inputJson);
            long transformStartTime = System.nanoTime();
            JsonNode result = engine.transformFromString(transform, input);
            long transformEndTime = System.nanoTime();
            long executionTimeMs = (transformEndTime - transformStartTime) / 1_000_000;
            
            TransformResponse response = new TransformResponse(true, result, null, executionTimeMs);
            String jsonResponse = mapper.writeValueAsString(response);
            
            sendJsonResponse(exchange, 200, jsonResponse);
            logRequest(exchange, 200, startTime, clientIp);
            LOGGER.info("Transform executed successfully in " + executionTimeMs + "ms");
            
        } catch (Exception e) {
            LOGGER.warning("Transform error: " + e.getMessage());
            TransformResponse response = new TransformResponse(false, null, e.getMessage(), null);
            String jsonResponse = mapper.writeValueAsString(response);
            sendJsonResponse(exchange, 200, jsonResponse);
            logRequest(exchange, 200, startTime, clientIp);
        }
    }

    private void handleHealth(HttpExchange exchange) throws IOException {
        long startTime = System.currentTimeMillis();
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        
        try {
            HealthResponse health = new HealthResponse(
                "UP",
                System.currentTimeMillis(),
                Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(),
                Runtime.getRuntime().maxMemory()
            );
            
            String jsonResponse = mapper.writeValueAsString(health);
            sendJsonResponse(exchange, 200, jsonResponse);
            logRequest(exchange, 200, startTime, clientIp);
        } catch (Exception e) {
            LOGGER.severe("Error handling health check: " + e.getMessage());
            sendJsonError(exchange, 500, "Health check failed");
            logRequest(exchange, 500, startTime, clientIp);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, byte[] content, String contentType) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("X-Content-Type-Options", "nosniff");
        exchange.getResponseHeaders().set("X-Frame-Options", "DENY");
        exchange.getResponseHeaders().set("X-XSS-Protection", "1; mode=block");
        exchange.sendResponseHeaders(statusCode, content.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(content);
        }
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, String json) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("X-Content-Type-Options", "nosniff");
        sendResponseHeaders(exchange, statusCode, json.getBytes(StandardCharsets.UTF_8));
    }

    private void sendJsonError(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        ErrorResponse error = new ErrorResponse(false, errorMessage);
        String json = mapper.writeValueAsString(error);
        sendJsonResponse(exchange, statusCode, json);
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        String html = "<html><body><h1>Error " + statusCode + "</h1><p>" + message + "</p></body></html>";
        sendResponse(exchange, statusCode, html.getBytes(StandardCharsets.UTF_8), "text/html; charset=UTF-8");
    }

    private void sendResponseHeaders(HttpExchange exchange, int statusCode, byte[] content) throws IOException {
        exchange.sendResponseHeaders(statusCode, content.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(content);
        }
    }

    private void logRequest(HttpExchange exchange, int statusCode, long startTime, String clientIp) {
        long duration = System.currentTimeMillis() - startTime;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String logMessage = String.format(
            "[%s] %s %s - Status: %d - Duration: %dms - Client: %s",
            timestamp,
            exchange.getRequestMethod(),
            exchange.getRequestURI().getPath(),
            statusCode,
            duration,
            clientIp
        );
        LOGGER.info(logMessage);
    }

    // Response DTOs
    private static class TransformResponse {
        public final boolean success;
        public final JsonNode result;
        public final String error;
        public final Long executionTime;

        public TransformResponse(boolean success, JsonNode result, String error, Long executionTime) {
            this.success = success;
            this.result = result;
            this.error = error;
            this.executionTime = executionTime;
        }
    }

    private static class ErrorResponse {
        public final boolean success;
        public final String error;

        public ErrorResponse(boolean success, String error) {
            this.success = success;
            this.error = error;
        }
    }

    private static class HealthResponse {
        public final String status;
        public final long timestamp;
        public final long usedMemoryBytes;
        public final long maxMemoryBytes;

        public HealthResponse(String status, long timestamp, long usedMemoryBytes, long maxMemoryBytes) {
            this.status = status;
            this.timestamp = timestamp;
            this.usedMemoryBytes = usedMemoryBytes;
            this.maxMemoryBytes = maxMemoryBytes;
        }
    }
}
