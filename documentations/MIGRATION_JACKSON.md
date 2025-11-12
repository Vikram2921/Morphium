# Morphium DSL - Jackson Migration Guide

## Changes Required

### 1. Replace Gson with Jackson in all files:
- `com.google.gson.JsonElement` → `com.fasterxml.jackson.databind.JsonNode`
- `com.google.gson.JsonObject` → `com.fasterxml.jackson.databind.node.ObjectNode`
- `com.google.gson.JsonArray` → `com.fasterxml.jackson.databind.node.ArrayNode`
- `com.google.gson.JsonPrimitive` → Use appropriate Jackson node types
- `com.google.gson.JsonNull` → `com.fasterxml.jackson.databind.node.NullNode`
- `JsonParser.parseString()` → `ObjectMapper.readTree()`

### 2. Key Jackson Classes:
```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
```

### 3. Creating Nodes:
```java
// Gson
new JsonPrimitive(value)
new JsonObject()
new JsonArray()

// Jackson
new TextNode(str)
new IntNode(i)
new DoubleNode(d)
new BooleanNode(b)
NullNode.getInstance()
new ObjectMapper().createObjectNode()
new ObjectMapper().createArrayNode()
```

### 4. MorphiumFunction Interface

New interface for user-implemented functions:
```java
public interface MorphiumFunction {
    String getName();
    int getMinParams();
    int getMaxParams();
    JsonNode call(JsonNode root, JsonNode[] params);
}
```

Usage:
```java
public class AddFunction implements MorphiumFunction {
    public String getName() { return "add"; }
    public int getMinParams() { return 2; }
    public int getMaxParams() { return 2; }
    
    public JsonNode call(JsonNode root, JsonNode[] params) {
        double a = params[0].asDouble();
        double b = params[1].asDouble();
        return DoubleNode.valueOf(a + b);
    }
}

// Register
MorphiumEngine engine = new MorphiumEngine();
engine.registerFunction(new AddFunction());
```

## Files to Update

All files in:
- `src/main/java/com/morphium/**/*.java`
- `src/test/java/com/morphium/**/*.java`

This is a complete API change affecting ~30 files.
