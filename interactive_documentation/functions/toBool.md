# toBool() - Convert to Boolean

## Syntax
```javascript
toBool(value)
```

## Description
Converts a value to boolean. Numbers (0=false, non-zero=true), strings ("true"/"false"), null/undefined (false).

## Parameters
- `value` - The value to convert to boolean

## Returns
Boolean value.

## Examples

### Convert Numbers
```javascript
// Input
{
  "values": [0, 1, -1, 42, 0.0]
}

// Morph
{
  bools: map($.values, "v", toBool(v))
}

// Output
{
  "bools": [false, true, true, true, false]
}
```

### Convert Strings
```javascript
// Input
{
  "str1": "true",
  "str2": "false",
  "str3": "yes",
  "str4": ""
}

// Morph
{
  bool1: toBool($.str1),
  bool2: toBool($.str2),
  bool3: toBool($.str3),
  bool4: toBool($.str4)
}

// Output
{
  "bool1": true,
  "bool2": false,
  "bool3": true,
  "bool4": false
}
```

### Feature Flags
```javascript
// Input
{
  "flags": {
    "feature1": 1,
    "feature2": 0,
    "feature3": "true",
    "feature4": "false"
  }
}

// Morph
{
  enabled: {
    feature1: toBool($.flags.feature1),
    feature2: toBool($.flags.feature2),
    feature3: toBool($.flags.feature3),
    feature4: toBool($.flags.feature4)
  }
}

// Output
{
  "enabled": {
    "feature1": true,
    "feature2": false,
    "feature3": true,
    "feature4": false
  }
}
```

### Filter Based on Boolean Conversion
```javascript
// Input
{
  "items": [
    {"name": "item1", "active": 1},
    {"name": "item2", "active": 0},
    {"name": "item3", "active": "true"}
  ]
}

// Morph
{
  activeItems: filter($.items, "i", toBool(i.active))
}

// Output
{
  "activeItems": [
    {"name": "item1", "active": 1},
    {"name": "item3", "active": "true"}
  ]
}
```

### Null and Undefined
```javascript
// Input
{
  "val1": null,
  "val2": 0,
  "val3": 1
}

// Morph
{
  bool1: toBool($.val1),
  bool2: toBool($.val2),
  bool3: toBool($.val3)
}

// Output
{
  "bool1": false,
  "bool2": false,
  "bool3": true
}
```

## Common Use Cases

1. **Feature Flags**: Convert numeric or string flags to booleans
2. **Validation**: Convert validation results
3. **Configuration**: Parse boolean config values
4. **Filtering**: Convert to boolean for conditions

## Tips and Best Practices

- 0, null, undefined, empty string → false
- Non-zero numbers, "true", non-empty strings → true
- Case-insensitive for "true"/"false" strings
- Use for config parsing

## Related Functions

- [toNumber()](toNumber.md) - Convert to number
- [toString()](toString.md) - Convert to string
- [exists()](exists.md) - Check if value exists

## Performance Notes

- O(1) operation
- Very fast
- No memory allocation

---

[← Back to Functions](../README.md#type-conversion)
