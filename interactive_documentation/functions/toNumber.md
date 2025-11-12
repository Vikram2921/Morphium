# toNumber() - Convert to Number

## Syntax
```javascript
toNumber(value)
```

## Description
Converts a value to a number. Strings are parsed as numbers, booleans become 0/1, null becomes 0.

## Parameters
- `value` - The value to convert to a number

## Returns
Numeric value, or NaN if conversion fails.

## Examples

### Convert String to Number
```javascript
// Input
{
  "price": "99.99",
  "quantity": "5"
}

// Morph
{
  price: toNumber($.price),
  quantity: toNumber($.quantity),
  total: toNumber($.price) * toNumber($.quantity)
}

// Output
{
  "price": 99.99,
  "quantity": 5,
  "total": 499.95
}
```

### Convert Array of Strings
```javascript
// Input
{
  "scores": ["85", "92", "78", "95"]
}

// Morph
{
  numbers: map($.scores, "s", toNumber(s)),
  average: avg(map($.scores, "s", toNumber(s)))
}

// Output
{
  "numbers": [85, 92, 78, 95],
  "average": 87.5
}
```

### Boolean to Number
```javascript
// Input
{
  "flags": {
    "active": true,
    "verified": false,
    "premium": true
  }
}

// Morph
{
  count: toNumber($.flags.active) + toNumber($.flags.verified) + toNumber($.flags.premium)
}

// Output
{
  "count": 2
}
```

### Parse User Input
```javascript
// Input
{
  "form": {
    "age": "25",
    "salary": "75000",
    "yearsExp": "3"
  }
}

// Morph
{
  data: {
    age: toNumber($.form.age),
    salary: toNumber($.form.salary),
    yearsExp: toNumber($.form.yearsExp)
  }
}

// Output
{
  "data": {
    "age": 25,
    "salary": 75000,
    "yearsExp": 3
  }
}
```

### Handle Null and Undefined
```javascript
// Input
{
  "data": {
    "value1": "42",
    "value2": null,
    "value3": "0"
  }
}

// Morph
{
  num1: toNumber($.data.value1),
  num2: toNumber($.data.value2),
  num3: toNumber($.data.value3)
}

// Output
{
  "num1": 42,
  "num2": 0,
  "num3": 0
}
```

### Floating Point Numbers
```javascript
// Input
{
  "measurements": ["3.14", "2.718", "1.414"]
}

// Morph
{
  numbers: map($.measurements, "m", toNumber(m)),
  sum: sum(map($.measurements, "m", toNumber(m)))
}

// Output
{
  "numbers": [3.14, 2.718, 1.414],
  "sum": 7.272
}
```

### Negative Numbers
```javascript
// Input
{
  "values": ["-10", "-5.5", "0", "5.5", "10"]
}

// Morph
{
  numbers: map($.values, "v", toNumber(v))
}

// Output
{
  "numbers": [-10, -5.5, 0, 5.5, 10]
}
```

## Common Use Cases

1. **Form Processing**: Convert form string inputs to numbers
2. **Data Import**: Parse CSV or text data
3. **Calculations**: Prepare strings for arithmetic
4. **Type Coercion**: Ensure numeric type for operations

## Tips and Best Practices

- Returns NaN for invalid number strings
- Null converts to 0
- True converts to 1, false to 0
- Use for user input validation
- Check result with exists() if NaN is possible

## Related Functions

- [toString()](toString.md) - Convert to string
- [toBool()](toBool.md) - Convert to boolean
- [jsonParse()](jsonParse.md) - Parse JSON string

## Performance Notes

- O(1) operation
- Very fast conversion
- No memory allocation for primitives

---

[← Back to Functions](../README.md#type-conversion)
