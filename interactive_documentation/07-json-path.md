# JSON Path Access

Comprehensive guide to accessing and navigating JSON data structures in Morphium DSL.

---

## Overview

Morphium DSL provides powerful JSON path access capabilities using the `$` operator and various navigation patterns. You can access nested properties, array elements, and traverse complex data structures with ease.

---

## The Root Operator `$`

### Basic Usage

The `$` symbol represents the root input JSON object.

```javascript
// Input
{
  "name": "Alice",
  "age": 30
}

// Script
{
  userName: $.name,
  userAge: $.age
}

// Output
{
  "userName": "Alice",
  "userAge": 30
}
```

---

## Dot Notation

### Simple Property Access

```javascript
// Input
{
  "user": {
    "firstName": "John",
    "lastName": "Doe"
  }
}

// Script
{
  first: $.user.firstName,
  last: $.user.lastName
}

// Output
{
  "first": "John",
  "last": "Doe"
}
```

### Nested Property Access

```javascript
// Input
{
  "company": {
    "department": {
      "team": {
        "lead": {
          "name": "Alice"
        }
      }
    }
  }
}

// Script
{
  leadName: $.company.department.team.lead.name
}

// Output
{
  "leadName": "Alice"
}
```

---

## Bracket Notation

### Property with Special Characters

```javascript
// Input
{
  "user-info": {
    "first-name": "John",
    "last-name": "Doe",
    "email@address": "john@example.com"
  }
}

// Script
{
  firstName: $["user-info"]["first-name"],
  lastName: $["user-info"]["last-name"],
  email: $["user-info"]["email@address"]
}

// Output
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com"
}
```

### Dynamic Property Access

```javascript
// Input
{
  "data": {
    "temperature": 72,
    "humidity": 65,
    "pressure": 1013
  },
  "field": "temperature"
}

// Script
let fieldName = $.field;
{
  value: $.data[fieldName]
}

// Output
{
  "value": 72
}
```

---

## Array Access

### Index-Based Access

```javascript
// Input
{
  "items": [10, 20, 30, 40, 50]
}

// Script
{
  first: $.items[0],
  second: $.items[1],
  last: $.items[4]
}

// Output
{
  "first": 10,
  "second": 20,
  "last": 50
}
```

### Accessing Objects in Arrays

```javascript
// Input
{
  "users": [
    {"id": 1, "name": "Alice", "age": 25},
    {"id": 2, "name": "Bob", "age": 30},
    {"id": 3, "name": "Charlie", "age": 35}
  ]
}

// Script
{
  firstUser: $.users[0].name,
  secondUserId: $.users[1].id,
  thirdUserAge: $.users[2].age
}

// Output
{
  "firstUser": "Alice",
  "secondUserId": 2,
  "thirdUserAge": 35
}
```

### Nested Arrays

```javascript
// Input
{
  "matrix": [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
  ]
}

// Script
{
  topLeft: $.matrix[0][0],
  center: $.matrix[1][1],
  bottomRight: $.matrix[2][2],
  firstRow: $.matrix[0]
}

// Output
{
  "topLeft": 1,
  "center": 5,
  "bottomRight": 9,
  "firstRow": [1, 2, 3]
}
```

---

## Mixed Access Patterns

### Combining Dots and Brackets

```javascript
// Input
{
  "company": {
    "departments": [
      {
        "name": "Engineering",
        "employees": [
          {"name": "Alice", "role": "Engineer"},
          {"name": "Bob", "role": "Manager"}
        ]
      },
      {
        "name": "Sales",
        "employees": [
          {"name": "Charlie", "role": "Sales Rep"}
        ]
      }
    ]
  }
}

// Script
{
  firstDept: $.company.departments[0].name,
  firstEmployee: $.company.departments[0].employees[0].name,
  managerRole: $.company.departments[0].employees[1].role,
  salesRep: $.company.departments[1].employees[0].name
}

// Output
{
  "firstDept": "Engineering",
  "firstEmployee": "Alice",
  "managerRole": "Manager",
  "salesRep": "Charlie"
}
```

---

## Safe Navigation

### Null-Safe Access with Conditional

```javascript
// Input
{
  "user": {
    "profile": null
  }
}

// Script
let profile = $.user.profile;
{
  name: if (profile != null) { profile.name } else { "Unknown" },
  // Or using ternary
  age: profile != null ? profile.age : 0
}

// Output
{
  "name": "Unknown",
  "age": 0
}
```

### Using exists() Function

```javascript
// Input
{
  "data": {
    "optional": null
  }
}

// Script
{
  hasOptional: exists($.data.optional),
  hasRequired: exists($.data.required),
  value: $.data.optional ?? "default"
}

// Output
{
  "hasOptional": true,
  "hasRequired": false,
  "value": "default"
}
```

### Safe Deep Access

```javascript
// Input
{
  "order": {
    "customer": {
      "address": null
    }
  }
}

// Script
let order = $.order;
let customer = order.customer;
let address = customer.address;

{
  city: if (address != null && exists(address.city)) {
    address.city
  } else {
    "No city"
  }
}

// Output
{
  "city": "No city"
}
```

---

## Array Traversal

### Using map()

```javascript
// Input
{
  "products": [
    {"name": "Widget", "price": 10},
    {"name": "Gadget", "price": 20},
    {"name": "Tool", "price": 15}
  ]
}

// Script
{
  names: map($.products, "p", p.name),
  prices: map($.products, "p", p.price),
  formatted: map($.products, "p", p.name + ": $" + toString(p.price))
}

// Output
{
  "names": ["Widget", "Gadget", "Tool"],
  "prices": [10, 20, 15],
  "formatted": ["Widget: $10", "Gadget: $20", "Tool: $15"]
}
```

### Using filter()

```javascript
// Input
{
  "orders": [
    {"id": 1, "status": "completed", "amount": 100},
    {"id": 2, "status": "pending", "amount": 50},
    {"id": 3, "status": "completed", "amount": 75}
  ]
}

// Script
{
  completed: filter($.orders, "o", o.status == "completed"),
  highValue: filter($.orders, "o", o.amount > 60)
}

// Output
{
  "completed": [
    {"id": 1, "status": "completed", "amount": 100},
    {"id": 3, "status": "completed", "amount": 75}
  ],
  "highValue": [
    {"id": 1, "status": "completed", "amount": 100},
    {"id": 3, "status": "completed", "amount": 75}
  ]
}
```

---

## Advanced Path Expressions

### Computed Paths

```javascript
// Input
{
  "config": {
    "prod": {"host": "prod.example.com"},
    "dev": {"host": "dev.example.com"}
  },
  "env": "prod"
}

// Script
let env = $.env;
let host = $.config[env].host;

{ environment: env, hostname: host }

// Output
{
  "environment": "prod",
  "hostname": "prod.example.com"
}
```

### Path with Variables

```javascript
// Input
{
  "data": {
    "users": [
      {"id": 1, "name": "Alice"},
      {"id": 2, "name": "Bob"},
      {"id": 3, "name": "Charlie"}
    ]
  },
  "userId": 2
}

// Script
let userId = $.userId;
let user = findFirst($.data.users, "u", u.id == userId);

{ user: user }

// Output
{
  "user": {"id": 2, "name": "Bob"}
}
```

---

## Complex Examples

### Example 1: E-commerce Order Processing

```javascript
// Input
{
  "order": {
    "id": "ORD-123",
    "customer": {
      "id": 456,
      "name": "Alice",
      "email": "alice@example.com",
      "address": {
        "street": "123 Main St",
        "city": "New York",
        "zip": "10001"
      }
    },
    "items": [
      {"sku": "WIDGET-1", "name": "Widget", "price": 29.99, "qty": 2},
      {"sku": "GADGET-1", "name": "Gadget", "price": 49.99, "qty": 1}
    ],
    "shipping": {
      "method": "express",
      "cost": 15.00
    }
  }
}

// Script
let order = $.order;
let customer = order.customer;
let items = order.items;

let subtotal = sum(map(items, "item", item.price * item.qty));
let shipping = order.shipping.cost;
let tax = subtotal * 0.08;
let total = subtotal + shipping + tax;

{
  orderId: order.id,
  customerName: customer.name,
  customerEmail: customer.email,
  shippingAddress: customer.address.street + ", " + 
                   customer.address.city + " " + 
                   customer.address.zip,
  items: map(items, "item", {
    name: item.name,
    quantity: item.qty,
    price: item.price,
    total: item.price * item.qty
  }),
  summary: {
    subtotal: subtotal,
    shipping: shipping,
    tax: tax,
    total: total
  }
}
```

### Example 2: Nested Data Extraction

```javascript
// Input
{
  "company": {
    "name": "TechCorp",
    "locations": [
      {
        "city": "New York",
        "departments": [
          {
            "name": "Engineering",
            "employees": [
              {"name": "Alice", "salary": 120000},
              {"name": "Bob", "salary": 115000}
            ]
          },
          {
            "name": "Sales",
            "employees": [
              {"name": "Charlie", "salary": 90000}
            ]
          }
        ]
      },
      {
        "city": "San Francisco",
        "departments": [
          {
            "name": "Engineering",
            "employees": [
              {"name": "David", "salary": 130000}
            ]
          }
        ]
      }
    ]
  }
}

// Script
let company = $.company;

{
  companyName: company.name,
  locations: map(company.locations, "loc", {
    city: loc.city,
    departments: map(loc.departments, "dept", {
      name: dept.name,
      employeeCount: count(dept.employees),
      avgSalary: avg(map(dept.employees, "emp", emp.salary)),
      employees: map(dept.employees, "emp", emp.name)
    })
  })
}
```

### Example 3: Dynamic Path Navigation

```javascript
// Input
{
  "data": {
    "users": {
      "alice": {"age": 30, "role": "admin"},
      "bob": {"age": 25, "role": "user"}
    }
  },
  "lookup": {
    "username": "alice",
    "field": "role"
  }
}

// Script
let username = $.lookup.username;
let field = $.lookup.field;
let user = $.data.users[username];
let value = user[field];

{
  username: username,
  field: field,
  value: value
}

// Output
{
  "username": "alice",
  "field": "role",
  "value": "admin"
}
```

---

## Common Patterns

### Pattern 1: Extract Multiple Fields

```javascript
let user = $.user;
{
  id: user.id,
  name: user.name,
  email: user.email
}
```

### Pattern 2: Flatten Nested Structure

```javascript
let order = $.order;
let customer = order.customer;
let address = customer.address;

{
  orderId: order.id,
  customerName: customer.name,
  city: address.city,
  country: address.country
}
```

### Pattern 3: Array Element Access

```javascript
let items = $.items;
{
  first: items[0],
  last: items[len(items) - 1],
  middle: items[len(items) / 2]
}
```

---

## Best Practices

### ✅ Do:
- Use dot notation for simple property access
- Use bracket notation for special characters or dynamic keys
- Check for null before accessing nested properties
- Store frequently accessed paths in variables
- Use helper functions for complex navigation

### ❌ Don't:
- Don't assume properties exist without checking
- Don't chain too many levels without null checks
- Don't use hardcoded indices for dynamic arrays
- Don't mix access patterns unnecessarily

---

## Tips

1. **Variable Storage**: Store complex paths in variables for reuse
2. **Null Safety**: Always check for null when navigating deep structures
3. **Array Bounds**: Use `len()` to check array bounds before accessing
4. **Performance**: Avoid repeated path access - store in variables
5. **Readability**: Break complex paths into intermediate variables

---

## Related Topics

- [Variables and Scope](04-variables-scope.md) - Storing path results
- [Data Types](05-data-types.md) - Understanding accessed data
- [Array Functions](functions/map.md) - Processing accessed arrays
- [Object Functions](functions/keys.md) - Working with objects

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Basic Concepts](02-basic-concepts.md)
- [Syntax Overview](03-syntax-overview.md)
