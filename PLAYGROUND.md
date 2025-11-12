# 🚀 Morphium Playground

## Quick Start

The Morphium Playground is now running at **http://localhost:8080**

## How to Run

### Option 1: Using Maven (Recommended)
```bash
mvn compile exec:java
```

### Option 2: Using the convenience scripts

**On Windows:**
```cmd
run-playground.bat
```

**On Linux/Mac:**
```bash
chmod +x run-playground.sh
./run-playground.sh
```

## Features

### ✨ Real-Time JSON Transformation
- Write Morphium DSL transforms in the left editor
- Enter your input JSON in the middle editor
- See instant results in the right output panel

### 🎯 Interactive Examples
Click any example button to load pre-built transformations:
- **Basic Transform** - Simple field mapping and renaming
- **Array Map** - Transform array items with calculations
- **Filter** - Filter arrays based on conditions
- **Merge** - Merge multiple objects
- **Conditional** - Ternary operators and conditionals
- **Custom Function** - Define and use custom functions

### ⌨️ Keyboard Shortcuts
- **Ctrl+Enter** - Execute transform (works in both transform and input editors)

### 🎨 Features
- Syntax highlighting for better readability
- JSON formatting button
- Execution time display
- Clear error messages
- Responsive design (works on mobile too!)

## Example Transform

```javascript
{
  fullName: input.person.first + " " + input.person.last,
  age: input.person.age,
  email: input.person.email,
  isAdult: input.person.age >= 18
}
```

**Input JSON:**
```json
{
  "person": {
    "first": "John",
    "last": "Doe",
    "age": 25,
    "email": "john.doe@example.com"
  }
}
```

**Output:**
```json
{
  "fullName": "John Doe",
  "age": 25,
  "email": "john.doe@example.com",
  "isAdult": true
}
```

## Advanced Examples

### Array Mapping
```javascript
{
  items: map(input.items, "item", {
    id: item.id,
    total: item.qty * item.price,
    discounted: (item.qty * item.price) * 0.9
  })
}
```

### Custom Functions
```javascript
function calculateTax(amount) {
  return amount * 0.1
}

function calculateTotal(price, qty) {
  let subtotal = price * qty
  let tax = calculateTax(subtotal)
  return subtotal + tax
}

{
  items: map(input.items, "item", {
    name: item.name,
    subtotal: item.price * item.qty,
    tax: calculateTax(item.price * item.qty),
    total: calculateTotal(item.price, item.qty)
  })
}
```

### Filtering
```javascript
{
  adults: filter(input.users, "u", u.age >= 18),
  minors: filter(input.users, "u", u.age < 18)
}
```

### Safe Navigation
```javascript
{
  city: input?.address?.city ?? "Unknown",
  country: input?.address?.country ?? "Unknown"
}
```

## Stop the Server

Press **Ctrl+C** in the terminal to stop the playground server.

## Technical Details

- **Server**: Java HTTP Server (built-in)
- **Port**: 8080
- **Engine**: Morphium DSL Engine with Jackson
- **No external dependencies required** - Everything is bundled!

## Troubleshooting

### Port Already in Use
If port 8080 is already in use, you can modify the port in:
```
src/main/java/com/morphium/playground/PlaygroundServer.java
```
Change the line:
```java
private static final int PORT = 8080;
```

### Clear Browser Cache
If changes don't appear, clear your browser cache or use incognito mode.

## Happy Transforming! 🎉
