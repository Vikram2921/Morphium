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

public class PlaygroundServer {
    private static final int PORT = 8080;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final MorphiumEngine engine = new MorphiumEngine();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        server.createContext("/", PlaygroundServer::handleIndex);
        server.createContext("/api/transform", PlaygroundServer::handleTransform);
        
        server.setExecutor(null);
        server.start();
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
        System.out.println("Morphium Playground started at http://localhost:" + PORT);
        System.out.println("Press Ctrl+C to stop the server");
    }

    private static void handleIndex(HttpExchange exchange) throws IOException {
        String html = PlaygroundHtml.getHtml();
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(html.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static void handleTransform(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJsonResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            JsonNode request = mapper.readTree(body);
            
            String transform = request.get("transform").asText();
            String inputJson = request.get("input").asText();
            
            JsonNode input = mapper.readTree(inputJson);
            long startTime = System.nanoTime();
            JsonNode result = engine.transformFromString(transform, input);
            long endTime = System.nanoTime();
            
            String response = String.format(
                "{\"success\":true,\"result\":%s,\"executionTime\":%d}",
                mapper.writeValueAsString(result),
                (endTime - startTime) / 1_000_000
            );
            
            sendJsonResponse(exchange, 200, response);
            
        } catch (Exception e) {
            String errorResponse = String.format(
                "{\"success\":false,\"error\":%s}",
                mapper.writeValueAsString(e.getMessage())
            );
            sendJsonResponse(exchange, 200, errorResponse);
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, String json) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, json.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }
}
