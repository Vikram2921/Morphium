# Morphium Playground

Professional JSON Transformation DSL Development Environment

## Overview

The Morphium Playground is a professional-grade, enterprise-ready web application for developing, testing, and debugging Morphium JSON transformations in real-time. It provides a rich development environment with syntax highlighting, auto-completion, and comprehensive examples.

## Features

- **Real-time Transformation**: Auto-transform on code changes with configurable delay
- **Syntax Highlighting**: Professional code editor with CodeMirror
- **Comprehensive Examples**: Pre-built examples covering all Morphium features
- **Error Handling**: Detailed error messages with execution metrics
- **Health Monitoring**: Built-in health check endpoint
- **Professional UI**: Modern, responsive design with accessibility features
- **Logging**: Structured logging with request tracking
- **Security**: XSS protection, CORS support, request size limits
- **Performance Metrics**: Execution time tracking for transformations

## Quick Start

### Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher

### Running the Playground

**On Windows:**
```bash
run-playground.bat
```

**On Linux/Mac:**
```bash
chmod +x run-playground.sh
./run-playground.sh
```

**With Custom Port:**
```bash
run-playground.bat 8090
./run-playground.sh 8090
```

### Access the Application

Once started, open your browser and navigate to:
```
http://localhost:8080
```

## API Endpoints

### Transform Endpoint
```
POST /api/transform
Content-Type: application/json

{
  "transform": "{ name: $.user.name }",
  "input": "{\"user\": {\"name\": \"John\"}}"
}
```

### Health Check Endpoint
```
GET /api/health
```

Returns:
```json
{
  "status": "UP",
  "timestamp": 1699876543210,
  "usedMemoryBytes": 104857600,
  "maxMemoryBytes": 536870912
}
```

## Configuration

The playground can be configured via `playground.properties`:

- **server.port**: Server port (default: 8080)
- **server.threadPool.size**: Thread pool size (default: 10)
- **server.maxRequestSize**: Maximum request size in bytes (default: 10MB)
- **server.transformTimeout**: Transformation timeout in milliseconds (default: 30s)
- **logging.level**: Logging level (INFO, DEBUG, WARNING, SEVERE)

## Examples

The playground includes comprehensive examples:

- **Basic Transform**: Simple field mapping and concatenation
- **Array Map**: Transforming arrays with calculations
- **Filter**: Filtering data based on conditions
- **Merge**: Combining multiple objects
- **Conditional**: Using ternary operators
- **Custom Functions**: Defining and using functions
- **Variables**: Working with variables and scope
- **Streams API**: Java Streams-like operations
- **Advanced Streams**: Complex stream operations
- **Aggregation**: Sum, avg, min, max operations

## Architecture

### Components

- **PlaygroundServer**: Main HTTP server with routing and request handling
- **PlaygroundHtml**: HTML/CSS/JavaScript frontend generator
- **MorphiumEngine**: Core transformation engine
- **ThreadPoolExecutor**: Concurrent request processing

### Request Flow

1. Client sends transformation request
2. Server validates request (size, format, required fields)
3. Request logged with timestamp and client IP
4. Transformation executed with timeout protection
5. Response sent with execution metrics
6. Request logged with status and duration

### Security Features

- Request size limits (default 10MB)
- XSS protection headers
- CORS support with configurable origins
- Frame protection
- Content type sniffing prevention
- Input sanitization

## Logging

All requests are logged with:
- Timestamp
- HTTP method
- Request path
- Status code
- Duration (ms)
- Client IP address

Example log entry:
```
[2024-11-13T10:38:01] POST /api/transform - Status: 200 - Duration: 45ms - Client: 127.0.0.1
```

## Performance

- **Thread Pool**: Configurable thread pool for concurrent requests
- **Execution Metrics**: Real-time execution time tracking
- **Memory Monitoring**: Health endpoint provides memory usage stats
- **Graceful Shutdown**: Proper resource cleanup on shutdown

## Development

### Building from Source

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

## Troubleshooting

### Port Already in Use

Change the port using command line argument:
```bash
./run-playground.sh 8090
```

### Memory Issues

Increase JVM heap size:
```bash
export MAVEN_OPTS="-Xmx2g"
mvn compile exec:java -Dexec.mainClass="com.morphium.playground.PlaygroundServer"
```

### Connection Refused

Check firewall settings and ensure the server started successfully. Check logs for errors.

## Production Deployment

For production deployment, consider:

1. **Reverse Proxy**: Use nginx or Apache as reverse proxy
2. **SSL/TLS**: Enable HTTPS for secure communication
3. **Authentication**: Add authentication layer if needed
4. **Monitoring**: Integrate with monitoring tools (Prometheus, Grafana)
5. **Logging**: Configure centralized logging (ELK, Splunk)
6. **Resource Limits**: Set appropriate memory and CPU limits
7. **Load Balancing**: Use load balancer for high availability

## License

See main project LICENSE file.

## Support

For issues and questions, please use the main project's issue tracker.
