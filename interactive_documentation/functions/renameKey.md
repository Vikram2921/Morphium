# renameKey() - Rename Object Key

## Syntax
```javascript
renameKey(object, oldKey, newKey)
```

## Description
Renames a key in an object. The value associated with the old key is moved to the new key. Returns a new object with the renamed key. The original object is not modified.

## Parameters
- `object` - The source object
- `oldKey` - String name of the key to rename
- `newKey` - String name for the new key

## Returns
New object with the key renamed.

## Examples

### Basic Key Rename
```javascript
// Input
{
  "user": {
    "first_name": "Alice",
    "last_name": "Smith"
  }
}

// Morph
{
  user: renameKey(
    renameKey($.user, "first_name", "firstName"),
    "last_name", "lastName"
  )
}

// Output
{
  "user": {
    "firstName": "Alice",
    "lastName": "Smith"
  }
}
```

### Convert Snake Case to Camel Case
```javascript
// Input
{
  "product": {
    "product_id": 123,
    "product_name": "Widget",
    "unit_price": 29.99,
    "in_stock": true
  }
}

// Morph
{
  product: renameKey(
    renameKey(
      renameKey(
        renameKey($.product, "product_id", "productId"),
        "product_name", "productName"),
      "unit_price", "unitPrice"),
    "in_stock", "inStock"
  )
}

// Output
{
  "product": {
    "productId": 123,
    "productName": "Widget",
    "unitPrice": 29.99,
    "inStock": true
  }
}
```

### Standardize API Fields
```javascript
// Input
{
  "order": {
    "ord_id": 456,
    "cust_name": "Bob",
    "tot_amt": 100.00
  }
}

// Morph
{
  order: renameKey(
    renameKey(
      renameKey($.order, "ord_id", "orderId"),
      "cust_name", "customerName"),
    "tot_amt", "totalAmount"
  )
}

// Output
{
  "order": {
    "orderId": 456,
    "customerName": "Bob",
    "totalAmount": 100.00
  }
}
```

### Update Legacy Field Names
```javascript
// Input
{
  "config": {
    "db_host": "localhost",
    "db_port": 5432,
    "db_name": "mydb"
  }
}

// Morph
{
  config: renameKey(
    renameKey(
      renameKey($.config, "db_host", "databaseHost"),
      "db_port", "databasePort"),
    "db_name", "databaseName"
  )
}

// Output
{
  "config": {
    "databaseHost": "localhost",
    "databasePort": 5432,
    "databaseName": "mydb"
  }
}
```

### Rename with Complex Values
```javascript
// Input
{
  "data": {
    "old_items": [
      {"id": 1, "name": "Item 1"},
      {"id": 2, "name": "Item 2"}
    ],
    "status": "active"
  }
}

// Morph
{
  data: renameKey($.data, "old_items", "items")
}

// Output
{
  "data": {
    "items": [
      {"id": 1, "name": "Item 1"},
      {"id": 2, "name": "Item 2"}
    ],
    "status": "active"
  }
}
```

### Rename Nested Object Key
```javascript
// Input
{
  "response": {
    "user_data": {
      "id": 789,
      "name": "Charlie"
    }
  }
}

// Morph
{
  response: renameKey($.response, "user_data", "userData")
}

// Output
{
  "response": {
    "userData": {
      "id": 789,
      "name": "Charlie"
    }
  }
}
```

### Multiple Renames in Transformation
```javascript
// Input
{
  "record": {
    "rec_id": 111,
    "rec_type": "payment",
    "rec_amount": 50.00,
    "rec_date": "2024-01-15"
  }
}

// Morph
{
  record: renameKey(
    renameKey(
      renameKey(
        renameKey($.record, "rec_id", "id"),
        "rec_type", "type"),
      "rec_amount", "amount"),
    "rec_date", "date"
  )
}

// Output
{
  "record": {
    "id": 111,
    "type": "payment",
    "amount": 50.00,
    "date": "2024-01-15"
  }
}
```

## Common Use Cases

1. **API Compatibility**: Convert between different API schemas
2. **Code Migration**: Update field names during refactoring
3. **Naming Convention**: Standardize camelCase vs snake_case
4. **Legacy Support**: Rename deprecated fields
5. **Data Normalization**: Unify inconsistent field names

## Tips and Best Practices

- Returns new object (immutable operation)
- Original object is never modified
- If oldKey doesn't exist, returns object unchanged
- If newKey already exists, it will be overwritten
- Chain multiple renameKey() calls for multiple renames
- Works only with objects (returns input unchanged if not an object)
- Preserves all other keys and values

## Related Functions

- [removeKey()](removeKey.md) - Remove object keys
- [set()](set.md) - Set nested values
- [keys()](keys.md) - Get all object keys
- [merge()](merge.md) - Merge multiple objects

## Performance Notes

- O(n) where n is number of keys (creates deep copy)
- Efficient for single key renames
- Consider preprocessing for bulk field mapping

---

[← Back to Functions](../README.md#object-functions)
