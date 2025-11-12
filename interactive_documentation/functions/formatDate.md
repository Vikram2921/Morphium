# formatDate() - Format Date String

## Syntax
```javascript
formatDate(timestamp, pattern)
```

## Description
Formats a timestamp (in milliseconds) as a date string according to the specified pattern.

## Parameters
- `timestamp` - Timestamp in milliseconds since Unix epoch
- `pattern` - Date format pattern (e.g., "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss")

## Returns
Formatted date string.

## Examples

### Basic Date Formatting
```javascript
// Input
{
  "timestamp": 1699876543210
}

// Morph
{
  date: formatDate($.timestamp, "yyyy-MM-dd"),
  datetime: formatDate($.timestamp, "yyyy-MM-dd HH:mm:ss"),
  time: formatDate($.timestamp, "HH:mm:ss")
}

// Output
{
  "date": "2023-11-13",
  "datetime": "2023-11-13 14:15:43",
  "time": "14:15:43"
}
```

### Format Current Time
```javascript
// Input
{}

// Morph
{
  currentDate: formatDate(now(), "yyyy-MM-dd"),
  currentTime: formatDate(now(), "HH:mm:ss"),
  fullTimestamp: formatDate(now(), "yyyy-MM-dd'T'HH:mm:ss")
}

// Output
{
  "currentDate": "2025-01-12",
  "currentTime": "15:30:45",
  "fullTimestamp": "2025-01-12T15:30:45"
}
```

### Format Event Timestamps
```javascript
// Input
{
  "events": [
    {"type": "login", "timestamp": 1699876543210},
    {"type": "logout", "timestamp": 1699880143210}
  ]
}

// Morph
{
  formatted: map($.events, "e", {
    type: e.type,
    time: formatDate(e.timestamp, "HH:mm:ss"),
    date: formatDate(e.timestamp, "yyyy-MM-dd")
  })
}

// Output
{
  "formatted": [
    {"type": "login", "time": "14:15:43", "date": "2023-11-13"},
    {"type": "logout", "time": "15:15:43", "date": "2023-11-13"}
  ]
}
```

### Custom Formats
```javascript
// Input
{
  "timestamp": 1699876543210
}

// Morph
{
  iso8601: formatDate($.timestamp, "yyyy-MM-dd'T'HH:mm:ss"),
  us: formatDate($.timestamp, "MM/dd/yyyy"),
  eu: formatDate($.timestamp, "dd/MM/yyyy"),
  readable: formatDate($.timestamp, "MMMM dd, yyyy")
}

// Output
{
  "iso8601": "2023-11-13T14:15:43",
  "us": "11/13/2023",
  "eu": "13/11/2023",
  "readable": "November 13, 2023"
}
```

### Add Formatted Timestamp to Log
```javascript
// Input
{
  "log": {
    "level": "INFO",
    "message": "User logged in",
    "timestamp": 1699876543210
  }
}

// Morph
{
  formattedLog: formatDate($.log.timestamp, "yyyy-MM-dd HH:mm:ss") + " [" + $.log.level + "] " + $.log.message
}

// Output
{
  "formattedLog": "2023-11-13 14:15:43 [INFO] User logged in"
}
```

### Create Human-Readable Reports
```javascript
// Input
{
  "orders": [
    {"id": 1, "createdAt": 1699876543210, "amount": 100},
    {"id": 2, "createdAt": 1699880143210, "amount": 200}
  ]
}

// Morph
{
  report: map($.orders, "o", {
    orderId: "ORD-" + toString(o.id),
    date: formatDate(o.createdAt, "MMM dd, yyyy"),
    amount: "$" + toString(o.amount)
  })
}

// Output
{
  "report": [
    {"orderId": "ORD-1", "date": "Nov 13, 2023", "amount": "$100"},
    {"orderId": "ORD-2", "date": "Nov 13, 2023", "amount": "$200"}
  ]
}
```

## Common Format Patterns

| Pattern | Example | Description |
|---------|---------|-------------|
| yyyy-MM-dd | 2023-11-13 | ISO date |
| yyyy-MM-dd HH:mm:ss | 2023-11-13 14:15:43 | ISO datetime |
| MM/dd/yyyy | 11/13/2023 | US format |
| dd/MM/yyyy | 13/11/2023 | European format |
| HH:mm:ss | 14:15:43 | 24-hour time |
| hh:mm a | 02:15 PM | 12-hour time |
| MMMM dd, yyyy | November 13, 2023 | Long format |

## Common Use Cases

1. **Display Formatting**: Format timestamps for user display
2. **Report Generation**: Create readable date strings in reports
3. **Logging**: Format log timestamps
4. **API Responses**: Convert timestamps to readable dates

## Tips and Best Practices

- Use ISO 8601 format for APIs (yyyy-MM-dd'T'HH:mm:ss)
- Choose format based on locale/region
- Combine with now() for current date/time
- Pattern is case-sensitive

## Related Functions

- [now()](now.md) - Get current timestamp
- [toString()](toString.md) - Convert to string
- [log()](logging.md) - Logging functions

## Performance Notes

- O(1) operation
- Fast formatting
- Minimal memory allocation

---

[← Back to Functions](../README.md#utility-functions)
