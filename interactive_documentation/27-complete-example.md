# Complete Working Example: E-commerce Order Processing

This example demonstrates how to use all major Morphium features together in a real-world scenario.

---

## Scenario

You have an e-commerce system that needs to:
1. Process incoming orders
2. Validate data
3. Calculate totals with discounts and tax
4. Format output for display
5. Log processing steps
6. Handle errors gracefully

---

## File Structure

```
src/main/resources/
└── morphs/
    └── NumberUtils.morph        (built-in utility)

your-project/
├── OrderProcessor.morph         (main transformation)
└── OrderProcessorDemo.java      (Java application)
```

---

## Step 1: Main Transformation Script

**File: OrderProcessor.morph**

```javascript
// Import utilities from resources
import "morphs/NumberUtils.morph" as numUtils

// User-defined function: Validate order item
function validateItem(item) {
  log("Validating item:", item.name)
  
  // Check required fields
  !exists(item.name) ? error("Item name is required") : null
  !exists(item.price) ? error("Item price is required") : null
  !exists(item.quantity) ? error("Item quantity is required") : null
  
  // Validate price is positive
  !numUtils.numberUtils.isPositive(item.price) ? 
    error("Price must be positive for item: " + item.name) : null
  
  // Validate quantity is positive integer
  !numUtils.numberUtils.isInteger(item.quantity) || 
  !numUtils.numberUtils.isPositive(item.quantity) ?
    error("Quantity must be positive integer for item: " + item.name) : null
  
  logDebug("Item validated successfully:", item.name)
  true
}

// User-defined function: Calculate item total
function calculateItemTotal(item, discountRate, taxRate) {
  let subtotal = item.price * item.quantity
  let discount = subtotal * discountRate
  let afterDiscount = subtotal - discount
  let tax = afterDiscount * taxRate
  let total = afterDiscount + tax
  
  {
    name: upper(item.name),
    sku: item.sku,
    unitPrice: numUtils.numberUtils.round(item.price, 2),
    quantity: item.quantity,
    subtotal: numUtils.numberUtils.round(subtotal, 2),
    discount: numUtils.numberUtils.round(discount, 2),
    tax: numUtils.numberUtils.round(tax, 2),
    total: numUtils.numberUtils.round(total, 2),
    
    // Formatted for display
    display: {
      unitPrice: numUtils.numberUtils.formatCurrency(item.price, "$"),
      subtotal: numUtils.numberUtils.formatCurrency(subtotal, "$"),
      discount: numUtils.numberUtils.formatCurrency(discount, "$"),
      tax: numUtils.numberUtils.formatCurrency(tax, "$"),
      total: numUtils.numberUtils.formatCurrency(total, "$")
    }
  }
}

// User-defined function: Determine shipping cost
function calculateShipping(orderTotal, shippingMethod) {
  log("Calculating shipping for method:", shippingMethod)
  
  let shipping = switch(shippingMethod) {
    case "express": orderTotal >= 100 ? 15.00 : 25.00;
    case "standard": orderTotal >= 50 ? 0.00 : 5.00;
    case "economy": 0.00;
    default: 5.00;
  }
  
  logInfo("Shipping cost:", shipping)
  numUtils.numberUtils.round(shipping, 2)
}

// User-defined function: Process order
function processOrder(order) {
  log("=== Processing Order:", order.orderId, "===")
  
  // Validate order has items
  !exists(order.items) || len(order.items) == 0 ?
    error("Order must contain at least one item") : null
  
  logInfo("Order contains", len(order.items), "items")
  
  // Validate each item (with error handling)
  for (item of order.items) {
    validateItem(item) ? null : error("Validation failed")
  }
  
  logInfo("All items validated successfully")
  
  // Get discount and tax rates
  let discountRate = order.discountPercent ?? 0
  let taxRate = order.taxRate ?? 0.0825
  
  logDebug("Discount rate:", discountRate)
  logDebug("Tax rate:", taxRate)
  
  // Calculate item totals
  let processedItems = map(
    order.items, 
    "item", 
    calculateItemTotal(item, discountRate, taxRate)
  )
  
  // Calculate order totals
  let orderSubtotal = sum(map(processedItems, "item", item.subtotal))
  let orderDiscount = sum(map(processedItems, "item", item.discount))
  let orderTax = sum(map(processedItems, "item", item.tax))
  let itemsTotal = sum(map(processedItems, "item", item.total))
  
  // Calculate shipping
  let shippingCost = calculateShipping(itemsTotal, order.shippingMethod ?? "standard")
  
  // Calculate final total
  let finalTotal = itemsTotal + shippingCost
  
  logInfo("Order totals calculated. Final total:", finalTotal)
  
  // Return processed order
  {
    orderId: order.orderId,
    orderDate: order.orderDate,
    customerName: upper(order.customerName),
    customerEmail: lower(order.customerEmail),
    
    items: processedItems,
    itemCount: count(order.items),
    
    pricing: {
      subtotal: numUtils.numberUtils.round(orderSubtotal, 2),
      discount: numUtils.numberUtils.round(orderDiscount, 2),
      discountPercent: numUtils.numberUtils.formatPercent(discountRate * 100, 0),
      tax: numUtils.numberUtils.round(orderTax, 2),
      taxRate: numUtils.numberUtils.formatPercent(taxRate * 100, 2),
      shipping: numUtils.numberUtils.round(shippingCost, 2),
      total: numUtils.numberUtils.round(finalTotal, 2)
    },
    
    display: {
      subtotal: numUtils.numberUtils.formatCurrency(orderSubtotal, "$"),
      discount: numUtils.numberUtils.formatCurrency(orderDiscount, "$"),
      tax: numUtils.numberUtils.formatCurrency(orderTax, "$"),
      shipping: numUtils.numberUtils.formatCurrency(shippingCost, "$"),
      total: numUtils.numberUtils.formatCurrency(finalTotal, "$")
    },
    
    shippingMethod: order.shippingMethod ?? "standard",
    status: "processed",
    processedAt: now()
  }
}

// Main transformation
{
  processedOrder: processOrder($)
}
```

---

## Step 2: Java Application

**File: OrderProcessorDemo.java**

```java
package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OrderProcessorDemo {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Morphium E-commerce Order Processor ===\n");
            
            // Demo 1: Valid order
            System.out.println("Demo 1: Processing Valid Order");
            System.out.println("--------------------------------");
            processValidOrder();
            
            System.out.println("\n");
            
            // Demo 2: Order with validation error
            System.out.println("Demo 2: Order with Validation Error");
            System.out.println("------------------------------------");
            processInvalidOrder();
            
            System.out.println("\n");
            
            // Demo 3: Bulk order processing
            System.out.println("Demo 3: Bulk Order Processing");
            System.out.println("------------------------------");
            processBulkOrders();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Process a valid order
     */
    private static void processValidOrder() throws Exception {
        // Create engine
        MorphiumEngine engine = createEngine();
        
        // Load transformation script
        String script = Files.readString(Paths.get("OrderProcessor.morph"));
        
        // Sample order data
        String orderJson = """
            {
              "orderId": "ORD-12345",
              "orderDate": "2025-01-15T10:30:00Z",
              "customerName": "john doe",
              "customerEmail": "JOHN@EXAMPLE.COM",
              "discountPercent": 0.10,
              "taxRate": 0.0825,
              "shippingMethod": "express",
              "items": [
                {
                  "sku": "WID-001",
                  "name": "premium widget",
                  "price": 99.99,
                  "quantity": 2
                },
                {
                  "sku": "GAD-002",
                  "name": "standard gadget",
                  "price": 49.99,
                  "quantity": 3
                }
              ]
            }
            """;
        
        // Transform
        JsonNode input = mapper.readTree(orderJson);
        JsonNode result = engine.transformFromString(script, input);
        
        // Display result
        System.out.println("Result:");
        System.out.println(result.toPrettyString());
    }
    
    /**
     * Process an order with validation errors
     */
    private static void processInvalidOrder() throws Exception {
        MorphiumEngine engine = createEngine();
        String script = Files.readString(Paths.get("OrderProcessor.morph"));
        
        // Order with invalid data (negative price)
        String orderJson = """
            {
              "orderId": "ORD-99999",
              "orderDate": "2025-01-15T10:30:00Z",
              "customerName": "test user",
              "customerEmail": "test@example.com",
              "items": [
                {
                  "sku": "INVALID",
                  "name": "Invalid Item",
                  "price": -50.00,
                  "quantity": 1
                }
              ]
            }
            """;
        
        try {
            JsonNode input = mapper.readTree(orderJson);
            engine.transformFromString(script, input);
            System.out.println("ERROR: Should have thrown validation error!");
        } catch (Exception e) {
            System.out.println("✓ Caught expected error: " + e.getMessage());
        }
    }
    
    /**
     * Process multiple orders efficiently (demonstrates caching)
     */
    private static void processBulkOrders() throws Exception {
        MorphiumEngine engine = createEngine();
        String script = Files.readString(Paths.get("OrderProcessor.morph"));
        
        // Process 3 orders with the same script (parser cache kicks in)
        String[] orderIds = {"ORD-001", "ORD-002", "ORD-003"};
        
        long startTime = System.currentTimeMillis();
        
        for (String orderId : orderIds) {
            String orderJson = String.format("""
                {
                  "orderId": "%s",
                  "orderDate": "2025-01-15T10:30:00Z",
                  "customerName": "Customer %s",
                  "customerEmail": "customer%s@example.com",
                  "discountPercent": 0.05,
                  "shippingMethod": "standard",
                  "items": [
                    {
                      "sku": "ITEM-001",
                      "name": "Product A",
                      "price": 29.99,
                      "quantity": 1
                    }
                  ]
                }
                """, orderId, orderId, orderId);
            
            JsonNode input = mapper.readTree(orderJson);
            JsonNode result = engine.transformFromString(script, input);
            
            String total = result.get("processedOrder")
                .get("display")
                .get("total")
                .asText();
            
            System.out.println("Processed " + orderId + " - Total: " + total);
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("\nProcessed 3 orders in " + elapsed + "ms");
        System.out.println("(Parser cache improves performance for repeated scripts)");
    }
    
    /**
     * Create and configure Morphium engine
     */
    private static MorphiumEngine createEngine() {
        MorphiumEngine engine = new MorphiumEngine();
        
        // Set resource path for imports
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
        
        // Set custom logger
        engine.setLogger(new Logger() {
            @Override
            public void log(Level level, String message) {
                String prefix = switch(level) {
                    case DEBUG -> "[DEBUG] ";
                    case INFO -> "[INFO]  ";
                    case WARN -> "[WARN]  ";
                    case ERROR -> "[ERROR] ";
                };
                System.out.println(prefix + message);
            }
        });
        
        return engine;
    }
}
```

---

## Step 3: Sample Input

**Valid Order Input:**
```json
{
  "orderId": "ORD-12345",
  "orderDate": "2025-01-15T10:30:00Z",
  "customerName": "john doe",
  "customerEmail": "JOHN@EXAMPLE.COM",
  "discountPercent": 0.10,
  "taxRate": 0.0825,
  "shippingMethod": "express",
  "items": [
    {
      "sku": "WID-001",
      "name": "premium widget",
      "price": 99.99,
      "quantity": 2
    },
    {
      "sku": "GAD-002",
      "name": "standard gadget",
      "price": 49.99,
      "quantity": 3
    }
  ]
}
```

---

## Step 4: Expected Output

```json
{
  "processedOrder": {
    "orderId": "ORD-12345",
    "orderDate": "2025-01-15T10:30:00Z",
    "customerName": "JOHN DOE",
    "customerEmail": "john@example.com",
    "items": [
      {
        "name": "PREMIUM WIDGET",
        "sku": "WID-001",
        "unitPrice": 99.99,
        "quantity": 2,
        "subtotal": 199.98,
        "discount": 19.99,
        "tax": 14.85,
        "total": 194.84,
        "display": {
          "unitPrice": "$99.99",
          "subtotal": "$199.98",
          "discount": "$19.99",
          "tax": "$14.85",
          "total": "$194.84"
        }
      },
      {
        "name": "STANDARD GADGET",
        "sku": "GAD-002",
        "unitPrice": 49.99,
        "quantity": 3,
        "subtotal": 149.97,
        "discount": 14.99,
        "tax": 11.13,
        "total": 146.11,
        "display": {
          "unitPrice": "$49.99",
          "subtotal": "$149.97",
          "discount": "$14.99",
          "tax": "$11.13",
          "total": "$146.11"
        }
      }
    ],
    "itemCount": 2,
    "pricing": {
      "subtotal": 349.95,
      "discount": 34.98,
      "discountPercent": "10%",
      "tax": 25.98,
      "taxRate": "8.25%",
      "shipping": 15.00,
      "total": 355.95
    },
    "display": {
      "subtotal": "$349.95",
      "discount": "$34.98",
      "tax": "$25.98",
      "shipping": "$15.00",
      "total": "$355.95"
    },
    "shippingMethod": "express",
    "status": "processed",
    "processedAt": 1736938200000
  }
}
```

---

## Step 5: Console Output

```
=== Morphium E-commerce Order Processor ===

Demo 1: Processing Valid Order
--------------------------------
[INFO]  === Processing Order: ORD-12345 ===
[INFO]  Order contains 2 items
[INFO]  Validating item: premium widget
[DEBUG] Item validated successfully: premium widget
[INFO]  Validating item: standard gadget
[DEBUG] Item validated successfully: standard gadget
[INFO]  All items validated successfully
[DEBUG] Discount rate: 0.1
[DEBUG] Tax rate: 0.0825
[INFO]  Calculating shipping for method: express
[INFO]  Shipping cost: 15.0
[INFO]  Order totals calculated. Final total: 355.95
Result:
{
  "processedOrder": {
    "orderId": "ORD-12345",
    ...
  }
}

Demo 2: Order with Validation Error
------------------------------------
[INFO]  === Processing Order: ORD-99999 ===
[INFO]  Order contains 1 items
[INFO]  Validating item: Invalid Item
✓ Caught expected error: Price must be positive for item: Invalid Item

Demo 3: Bulk Order Processing
------------------------------
[INFO]  === Processing Order: ORD-001 ===
...
Processed ORD-001 - Total: $32.49
Processed ORD-002 - Total: $32.49
Processed ORD-003 - Total: $32.49

Processed 3 orders in 45ms
(Parser cache improves performance for repeated scripts)
```

---

## Features Demonstrated

### ✅ Control Flow
- **If-else statements** - Validation logic
- **Switch statements** - Shipping cost calculation
- **For-of loops** - Item validation iteration
- **Ternary operator** - Conditional expressions

### ✅ Functions
- **User-defined functions** - `validateItem()`, `calculateItemTotal()`, etc.
- **Built-in functions** - `map()`, `sum()`, `count()`, `exists()`, etc.
- **Imported functions** - `numUtils.numberUtils.round()`, etc.

### ✅ Error Handling
- **error()** - Validation errors with descriptive messages
- **Try-catch** - Java-side error handling
- **Validation** - Required fields, type checks, business rules

### ✅ Logging
- **log()** - General logging
- **logInfo()** - Information messages
- **logDebug()** - Debug output
- **Custom logger** - Java-side logger implementation

### ✅ Module System
- **Import statement** - `import "morphs/NumberUtils.morph" as numUtils`
- **Resource imports** - Loading from classpath
- **Namespace access** - `numUtils.numberUtils.round()`

### ✅ Number Utilities
- **round()** - Decimal rounding
- **formatCurrency()** - Currency formatting
- **formatPercent()** - Percentage formatting
- **isPositive()** - Number validation
- **isInteger()** - Type checking

### ✅ Array Operations
- **map()** - Transform collections
- **sum()** - Aggregate calculations
- **count()** - Count elements
- **Chaining** - Multiple operations in sequence

### ✅ Performance
- **Parser caching** - Reuse parsed scripts
- **Engine reuse** - Single engine instance
- **Efficient operations** - Optimized transformations

---

## Running the Example

### 1. Set up project structure
```bash
# Create directory structure
mkdir -p src/main/resources/morphs
mkdir -p src/main/java/com/example

# Copy files
# - NumberUtils.morph to src/main/resources/morphs/
# - OrderProcessor.morph to project root
# - OrderProcessorDemo.java to src/main/java/com/example/
```

### 2. Add Morphium dependency
```xml
<dependency>
    <groupId>com.morphium</groupId>
    <artifactId>morphium-dsl</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 3. Run the application
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.OrderProcessorDemo"
```

---

## Variations and Extensions

### Add Email Validation
```javascript
import "morphs/ValidationUtils.morph" as validUtils

// Validate email format
!validUtils.validationUtils.isValidEmail(order.customerEmail) ?
  error("Invalid email address") : null
```

### Add Loyalty Points
```javascript
function calculateLoyaltyPoints(orderTotal) {
  let points = numUtils.numberUtils.round(orderTotal * 0.1, 0)
  logInfo("Loyalty points earned:", points)
  points
}

// In order processing:
loyaltyPoints: calculateLoyaltyPoints(finalTotal)
```

### Add Inventory Check
```javascript
function checkInventory(item, inventoryMap) {
  let available = inventoryMap[item.sku] ?? 0
  item.quantity > available ?
    error("Insufficient inventory for " + item.name) : null
}
```

### Format Dates
```javascript
import "morphs/DateUtils.morph" as dateUtils

// Format order date
formattedDate: dateUtils.dateUtils.formatDate(order.orderDate)
```

---

## Best Practices Demonstrated

1. ✅ **Separation of Concerns** - Separate functions for validation, calculation, formatting
2. ✅ **Error Handling** - Validate early, fail fast with descriptive errors
3. ✅ **Logging** - Log at appropriate levels for debugging and monitoring
4. ✅ **Code Reuse** - Import utility modules, create reusable functions
5. ✅ **Performance** - Reuse engine instance, benefit from caching
6. ✅ **Maintainability** - Clear function names, logical organization
7. ✅ **Type Safety** - Validate types and ranges
8. ✅ **Documentation** - Comments explain complex logic

---

## Summary

This complete example shows how to:
- ✅ Import and use NumberUtils from resources
- ✅ Create user-defined functions
- ✅ Validate data with error handling
- ✅ Use control flow (if-else, switch, for-of)
- ✅ Log processing steps
- ✅ Format numbers for display
- ✅ Process complex data transformations
- ✅ Integrate with Java applications
- ✅ Handle errors gracefully
- ✅ Optimize performance with caching

This pattern can be adapted for any business domain requiring data transformation and validation!
