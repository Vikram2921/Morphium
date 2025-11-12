# Morphium DSL - $ Variable Examples

## 1. Direct Access (Most Common)
```javascript
{
  name: $.person.name,
  email: $.person.email,
  age: $.person.age
}
```

**Input:**
```json
{
  "person": {
    "name": "John",
    "email": "john@example.com",
    "age": 30
  }
}
```

**Output:**
```json
{
  "name": "John",
  "email": "john@example.com",
  "age": 30
}
```

---

## 2. Assign to Local Variable
```javascript
let root = $
let person = root.person

{
  fullName: person.first + " " + person.last,
  contact: person.email
}
```

**Input:**
```json
{
  "person": {
    "first": "Jane",
    "last": "Doe",
    "email": "jane@example.com"
  }
}
```

**Output:**
```json
{
  "fullName": "Jane Doe",
  "contact": "jane@example.com"
}
```

---

## 3. Extract Configuration Objects
```javascript
let root = $
let config = root.config
let items = root.items

{
  status: config.enabled ? "Active" : "Inactive",
  multiplier: config.multiplier,
  processedItems: map(items, "item", {
    id: item.id,
    value: item.value * config.multiplier
  })
}
```

**Input:**
```json
{
  "config": {
    "enabled": true,
    "multiplier": 2
  },
  "items": [
    {"id": "A", "value": 10},
    {"id": "B", "value": 20}
  ]
}
```

**Output:**
```json
{
  "status": "Active",
  "multiplier": 2,
  "processedItems": [
    {"id": "A", "value": 20},
    {"id": "B", "value": 40}
  ]
}
```

---

## 4. Use in Built-in Functions
```javascript
{
  allItems: map($.items, "item", item.name),
  expensiveItems: filter($.items, "item", item.price > 100),
  total: reduce($.items, "acc", "item", 0, acc + item.price)
}
```

**Input:**
```json
{
  "items": [
    {"name": "Widget", "price": 50},
    {"name": "Gadget", "price": 150},
    {"name": "Tool", "price": 75}
  ]
}
```

**Output:**
```json
{
  "allItems": ["Widget", "Gadget", "Tool"],
  "expensiveItems": [{"name": "Gadget", "price": 150}],
  "total": 275
}
```

---

## 5. Safe Navigation with $
```javascript
{
  city: $?.person?.address?.city ?? "Unknown",
  country: $?.person?.address?.country ?? "Unknown"
}
```

**Input:**
```json
{
  "person": {
    "name": "John"
  }
}
```

**Output:**
```json
{
  "city": "Unknown",
  "country": "Unknown"
}
```

---

## 6. Global Variables with $
```javascript
global root = $
global config = root.settings

function applyTax(amount) {
  return amount * (1 + config.taxRate)
}

{
  items: map(root.items, "item", {
    name: item.name,
    price: item.price,
    priceWithTax: applyTax(item.price)
  })
}
```

**Input:**
```json
{
  "settings": {
    "taxRate": 0.1
  },
  "items": [
    {"name": "Book", "price": 10},
    {"name": "Pen", "price": 2}
  ]
}
```

**Output:**
```json
{
  "items": [
    {"name": "Book", "price": 10, "priceWithTax": 11},
    {"name": "Pen", "price": 2, "priceWithTax": 2.2}
  ]
}
```

---

## 7. Complex Nested Access
```javascript
let root = $
let company = root.company
let employees = company.employees
let activeEmployees = filter(employees, "emp", emp.status == "active")

{
  companyName: company.name,
  totalEmployees: len(employees),
  activeCount: len(activeEmployees),
  activeNames: map(activeEmployees, "emp", emp.name)
}
```

**Input:**
```json
{
  "company": {
    "name": "TechCorp",
    "employees": [
      {"name": "Alice", "status": "active"},
      {"name": "Bob", "status": "inactive"},
      {"name": "Charlie", "status": "active"}
    ]
  }
}
```

**Output:**
```json
{
  "companyName": "TechCorp",
  "totalEmployees": 3,
  "activeCount": 2,
  "activeNames": ["Alice", "Charlie"]
}
```

---

## 8. Combining Everything
```javascript
// Global configuration
global root = $
global settings = root.settings

// Helper function
function calculateDiscount(price) {
  let discount = settings.discountRate
  return price * (1 - discount)
}

// Extract data sections
let products = root.products
let customer = root.customer

// Transform
{
  customer: {
    name: customer.name,
    tier: customer.tier
  },
  products: map(products, "p", {
    id: p.id,
    name: p.name,
    originalPrice: p.price,
    discountedPrice: calculateDiscount(p.price),
    savings: p.price - calculateDiscount(p.price)
  }),
  summary: {
    totalProducts: len(products),
    discountRate: settings.discountRate * 100 + "%"
  }
}
```

**Input:**
```json
{
  "settings": {
    "discountRate": 0.15
  },
  "customer": {
    "name": "John Doe",
    "tier": "Gold"
  },
  "products": [
    {"id": "P1", "name": "Laptop", "price": 1000},
    {"id": "P2", "name": "Mouse", "price": 50}
  ]
}
```

**Output:**
```json
{
  "customer": {
    "name": "John Doe",
    "tier": "Gold"
  },
  "products": [
    {
      "id": "P1",
      "name": "Laptop",
      "originalPrice": 1000,
      "discountedPrice": 850,
      "savings": 150
    },
    {
      "id": "P2",
      "name": "Mouse",
      "originalPrice": 50,
      "discountedPrice": 42.5,
      "savings": 7.5
    }
  ],
  "summary": {
    "totalProducts": 2,
    "discountRate": "15%"
  }
}
```

---

## Key Takeaways

1. **`$` is the root** - Always refers to the input JSON
2. **Assignable** - `let root = $` creates a local copy
3. **Works everywhere** - Functions, expressions, safe navigation
4. **Flexible** - Extract sub-objects for cleaner code
5. **Global scope** - `global root = $` makes it available in functions

## Try It!

Start the playground:
```bash
mvn compile exec:java
```

Open **http://localhost:8080** and try the "$ Variables" example!
