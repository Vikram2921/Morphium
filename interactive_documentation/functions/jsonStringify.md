# jsonStringify() - Convert to JSON String

## Syntax
```javascript
jsonStringify(value)
```

## Description
Converts a value to its JSON string representation.

## Parameters
- `value` - Any value (object, array, string, number, boolean, null)

## Returns
JSON string representation of the value.

## Examples

### Stringify Object
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "active": true
  }
}

// Morph
{
  jsonStr: jsonStringify($.user)
}

// Output
{
  "jsonStr": "{\"name\":\"Alice\",\"age\":25,\"active\":true}"
}
```

### Stringify Array
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Morph
{
  jsonStr: jsonStringify($.numbers)
}

// Output
{
  "jsonStr": "[1,2,3,4,5]"
}
```

### Store Complex Data
```javascript
// Input
{
  "data": {
    "users": [
      {"id": 1, "name": "Alice"},
      {"id": 2, "name": "Bob"}
    ],
    "meta": {"count": 2}
  }
}

// Morph
{
  serialized: jsonStringify($.data)
}

// Output
{
  "serialized": "{\"users\":[{\"id\":1,\"name\":\"Alice\"},{\"id\":2,\"name\":\"Bob\"}],\"meta\":{\"count\":2}}"
}
```

### Create API Payload
```javascript
// Input
{
  "order": {
    "orderId": "ORD-001",
    "items": ["item1", "item2"],
    "total": 99.99
  }
}

// Morph
{
  payload: jsonStringify({
    order: $.order,
    timestamp: now()
  })
}

// Output
{
  "payload": "{\"order\":{\"orderId\":\"ORD-001\",\"items\":[\"item1\",\"item2\"],\"total\":99.99},\"timestamp\":1699876543210}"
}
```

### Stringify for Logging
```javascript
// Input
{
  "event": {
    "type": "user_login",
    "userId": 12345,
    "timestamp": "2025-01-12T10:30:00Z"
  }
}

// Morph
{
  logEntry: "Event: " + jsonStringify($.event)
}

// Output
{
  "logEntry": "Event: {\"type\":\"user_login\",\"userId\":12345,\"timestamp\":\"2025-01-12T10:30:00Z\"}"
}
```

### Stringify Primitive Values
```javascript
// Input
{
  "str": "hello",
  "num": 42,
  "bool": true,
  "nullVal": null
}

// Morph
{
  strings: {
    str: jsonStringify($.str),
    num: jsonStringify($.num),
    bool: jsonStringify($.bool),
    nullVal: jsonStringify($.nullVal)
  }
}

// Output
{
  "strings": {
    "str": "\"hello\"",
    "num": "42",
    "bool": "true",
    "nullVal": "null"
  }
}
```

### Round Trip with jsonParse
```javascript
// Input
{
  "data": {"name": "Alice", "age": 25}
}

// Morph
{
  stringified: jsonStringify($.data),
  parsed: jsonParse(jsonStringify($.data))
}

// Output
{
  "stringified": "{\"name\":\"Alice\",\"age\":25}",
  "parsed": {"name": "Alice", "age": 25}
}
```

## Common Use Cases

1. **API Calls**: Serialize data for API requests
2. **Logging**: Convert objects to log strings
3. **Storage**: Serialize data for storage
4. **Message Passing**: Create JSON messages

## Tips and Best Practices

- Produces compact JSON (no formatting)
- Handles nested structures
- Use for serialization
- Pair with jsonParse() for round-trip

## Related Functions

- [jsonParse()](jsonParse.md) - Parse JSON string
- [toString()](toString.md) - Convert to string
- [log()](logging.md) - Logging functions

## Performance Notes

- O(n) where n is object size
- Fast serialization
- Memory proportional to output size

---

[← Back to Functions](../README.md#type-conversion)
