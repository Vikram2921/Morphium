# entries() - Get Key-Value Pairs

## Syntax
```javascript
entries(object)
```

## Description
Returns an array of key-value pairs from an object. Each pair is represented as a two-element array `[key, value]`.

## Parameters
- `object` - The object to get entries from

## Returns
Array of `[key, value]` pairs.

## Examples

### Basic Entries
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "email": "alice@example.com"
  }
}

// Morph
{
  userEntries: entries($.user)
}

// Output
{
  "userEntries": [
    ["name", "Alice"],
    ["age", 25],
    ["email", "alice@example.com"]
  ]
}
```

### Convert to Array of Objects
```javascript
// Input
{
  "prices": {
    "apple": 1.5,
    "banana": 0.75,
    "orange": 2.0
  }
}

// Morph
{
  priceList: map(entries($.prices), "e", {
    item: e[0],
    price: e[1]
  })
}

// Output
{
  "priceList": [
    {"item": "apple", "price": 1.5},
    {"item": "banana", "price": 0.75},
    {"item": "orange", "price": 2.0}
  ]
}
```

### Filter Entries
```javascript
// Input
{
  "inventory": {
    "laptop": 5,
    "mouse": 0,
    "keyboard": 12,
    "monitor": 0
  }
}

// Morph
{
  allEntries: entries($.inventory),
  inStock: filter(entries($.inventory), "e", e[1] > 0)
}

// Output
{
  "allEntries": [
    ["laptop", 5],
    ["mouse", 0],
    ["keyboard", 12],
    ["monitor", 0]
  ],
  "inStock": [
    ["laptop", 5],
    ["keyboard", 12]
  ]
}
```

### Create Formatted List
```javascript
// Input
{
  "config": {
    "host": "localhost",
    "port": 8080,
    "debug": true
  }
}

// Morph
{
  configList: join(map(entries($.config), "e", e[0] + "=" + e[1]), ", ")
}

// Output
{
  "configList": "host=localhost, port=8080, debug=true"
}
```

### Sum Values with Filtering
```javascript
// Input
{
  "sales": {
    "january": 1000,
    "february": 1500,
    "march": 1200,
    "april": 800
  }
}

// Morph
{
  entries: entries($.sales),
  highSalesMonths: filter(entries($.sales), "e", e[1] > 1000),
  highSalesTotal: sum(map(filter(entries($.sales), "e", e[1] > 1000), "e", e[1]))
}

// Output
{
  "entries": [
    ["january", 1000],
    ["february", 1500],
    ["march", 1200],
    ["april", 800]
  ],
  "highSalesMonths": [
    ["february", 1500],
    ["march", 1200]
  ],
  "highSalesTotal": 2700
}
```

### Rebuild Object with Transformation
```javascript
// Input
{
  "data": {
    "a": 10,
    "b": 20,
    "c": 30
  }
}

// Morph
{
  doubled: merge(for (entry of entries($.data)) {
    {[entry[0]]: entry[1] * 2}
  })
}

// Output
{
  "doubled": {
    "a": 20,
    "b": 40,
    "c": 60
  }
}
```

## Common Use Cases

1. **Object Iteration**: Process objects as arrays
2. **Transformation**: Convert object format
3. **Filtering**: Filter object by keys or values
4. **Reporting**: Create formatted lists from objects

## Tips and Best Practices

- Each entry is `[key, value]` array
- Use `e[0]` for key, `e[1]` for value
- Useful for filtering or transforming objects
- Combine with map() for powerful transformations

## Related Functions

- [keys()](keys.md) - Get only keys
- [values()](values.md) - Get only values
- [map()](map.md) - Transform entries
- [filter()](filter.md) - Filter entries

## Performance Notes

- O(n) where n is number of properties
- Creates new array with all entries
- Memory proportional to object size

---

[← Back to Functions](../README.md#object-functions)
