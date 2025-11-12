# now() - Get Current Timestamp

## Syntax
```javascript
now()
```

## Description
Returns the current timestamp in milliseconds since Unix epoch (January 1, 1970, 00:00:00 UTC).

## Parameters
None

## Returns
Number representing current timestamp in milliseconds.

## Examples

### Get Current Timestamp
```javascript
// Input
{}

// Morph
{
  timestamp: now()
}

// Output
{
  "timestamp": 1699876543210
}
```

### Add Timestamp to Records
```javascript
// Input
{
  "event": {
    "type": "user_login",
    "userId": 12345
  }
}

// Morph
{
  event: {
    type: $.event.type,
    userId: $.event.userId,
    timestamp: now()
  }
}

// Output
{
  "event": {
    "type": "user_login",
    "userId": 12345,
    "timestamp": 1699876543210
  }
}
```

### Timestamp Multiple Records
```javascript
// Input
{
  "events": [
    {"type": "login", "user": "alice"},
    {"type": "logout", "user": "bob"}
  ]
}

// Morph
{
  timestamped: map($.events, "e", merge(e, {"timestamp": now()}))
}

// Output
{
  "timestamped": [
    {"type": "login", "user": "alice", "timestamp": 1699876543210},
    {"type": "logout", "user": "bob", "timestamp": 1699876543210}
  ]
}
```

### Calculate Expiry Time
```javascript
// Input
{
  "ttlSeconds": 3600
}

// Morph
{
  createdAt: now(),
  expiresAt: now() + ($.ttlSeconds * 1000)
}

// Output
{
  "createdAt": 1699876543210,
  "expiresAt": 1699880143210
}
```

### Add Metadata with Timestamp
```javascript
// Input
{
  "data": {
    "name": "Document",
    "content": "Some content"
  }
}

// Morph
{
  document: merge($.data, {
    "_meta": {
      "createdAt": now(),
      "version": 1
    }
  })
}

// Output
{
  "document": {
    "name": "Document",
    "content": "Some content",
    "_meta": {
      "createdAt": 1699876543210,
      "version": 1
    }
  }
}
```

### Generate Unique IDs
```javascript
// Input
{
  "prefix": "ORDER"
}

// Morph
{
  orderId: $.prefix + "-" + toString(now())
}

// Output
{
  "orderId": "ORDER-1699876543210"
}
```

### Log Entry with Timestamp
```javascript
// Input
{
  "level": "INFO",
  "message": "Application started"
}

// Morph
{
  logEntry: {
    timestamp: now(),
    level: $.level,
    message: $.message
  }
}

// Output
{
  "logEntry": {
    "timestamp": 1699876543210,
    "level": "INFO",
    "message": "Application started"
  }
}
```

## Common Use Cases

1. **Timestamping**: Add timestamps to events or records
2. **Unique IDs**: Generate time-based unique identifiers
3. **Expiry Calculation**: Calculate future expiry times
4. **Logging**: Add timestamp to log entries

## Tips and Best Practices

- Returns milliseconds since Unix epoch
- All calls in same transformation get same timestamp
- Use for consistent timestamping across records
- Divide by 1000 for seconds

## Related Functions

- [formatDate()](formatDate.md) - Format timestamp as date string
- [toString()](toString.md) - Convert timestamp to string
- [log()](logging.md) - Logging with timestamps

## Performance Notes

- O(1) operation
- Very fast
- Cached within single transformation

---

[← Back to Functions](../README.md#utility-functions)
