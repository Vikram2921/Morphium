package com.morphium.examples;

import com.morphium.core.MorphiumEngine;
import com.morphium.function.MorphiumFunction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Complete demonstration of Morphium DSL features
 */
public class ComprehensiveDemo {
    
    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        ObjectMapper mapper = new ObjectMapper();
        
        System.out.println("=== Morphium DSL - Complete Demo ===\n");
        
        // Demo 1: Basic $ usage
        demo1Basic(engine, mapper);
        
        // Demo 2: User-defined functions
        demo2Functions(engine, mapper);
        
        // Demo 3: Global variables
        demo3GlobalVars(engine, mapper);
        
        // Demo 4: Custom Java functions
        demo4CustomFunctions(engine, mapper);
        
        // Demo 5: Complex nested transformation
        demo5ComplexTransform(engine, mapper);
        
        // Demo 6: Array operations
        demo6ArrayOps(engine, mapper);
        
        System.out.println("\n=== Demo Complete! ===");
        System.out.println("Try the playground: mvn compile exec:java");
        System.out.println("Then visit: http://localhost:8080");
    }
    
    static void demo1Basic(MorphiumEngine engine, ObjectMapper mapper) throws Exception {
        System.out.println("📝 Demo 1: Basic $ Variable Usage");
        System.out.println("-----------------------------------");
        
        String input = "{\"name\":\"Alice\",\"age\":25,\"city\":\"NYC\"}";
        String transform = "{\n" +
            "  greeting: 'Hello ' + $.name,\n" +
            "  location: $.city,\n" +
            "  isAdult: $.age >= 18\n" +
            "}";
        
        System.out.println("Input: " + input);
        System.out.println("Transform:\n" + transform);
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Output: " + result.toPrettyString());
        System.out.println();
    }
    
    static void demo2Functions(MorphiumEngine engine, ObjectMapper mapper) throws Exception {
        System.out.println("🔧 Demo 2: User-Defined Functions");
        System.out.println("-----------------------------------");
        
        String input = "{\"price\":100,\"quantity\":5}";
        String transform = "function calculateTotal(price, qty) {\n" +
            "  return price * qty\n" +
            "}\n\n" +
            "function applyDiscount(amount, percent) {\n" +
            "  return amount * (1 - percent)\n" +
            "}\n\n" +
            "{\n" +
            "  subtotal: calculateTotal($.price, $.quantity),\n" +
            "  discounted: applyDiscount(calculateTotal($.price, $.quantity), 0.1),\n" +
            "  saved: calculateTotal($.price, $.quantity) - applyDiscount(calculateTotal($.price, $.quantity), 0.1)\n" +
            "}";
        
        System.out.println("Input: " + input);
        System.out.println("Transform:\n" + transform);
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Output: " + result.toPrettyString());
        System.out.println();
    }
    
    static void demo3GlobalVars(MorphiumEngine engine, ObjectMapper mapper) throws Exception {
        System.out.println("🌍 Demo 3: Global Variables");
        System.out.println("-----------------------------------");
        
        String input = "{\"price\":1000}";
        String transform = "global TAX_RATE = 0.18\n" +
            "global DISCOUNT = 0.15\n\n" +
            "function addTax(amount) {\n" +
            "  return amount * (1 + TAX_RATE)\n" +
            "}\n\n" +
            "let discountedPrice = $.price * (1 - DISCOUNT)\n\n" +
            "{\n" +
            "  original: $.price,\n" +
            "  afterDiscount: discountedPrice,\n" +
            "  withTax: addTax(discountedPrice),\n" +
            "  taxRate: TAX_RATE,\n" +
            "  discount: DISCOUNT\n" +
            "}";
        
        System.out.println("Input: " + input);
        System.out.println("Transform:\n" + transform);
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Output: " + result.toPrettyString());
        System.out.println();
    }
    
    static void demo4CustomFunctions(MorphiumEngine engine, ObjectMapper mapper) throws Exception {
        System.out.println("⚙️ Demo 4: Custom Java Functions");
        System.out.println("-----------------------------------");
        
        // Register custom functions
        engine.registerFunction(new PowerFunction());
        engine.registerFunction(new FormatCurrencyFunction());
        
        String input = "{\"base\":2,\"exponent\":8,\"amount\":1234.56}";
        String transform = "{\n" +
            "  power: power($.base, $.exponent),\n" +
            "  formatted: formatCurrency($.amount, 'USD')\n" +
            "}";
        
        System.out.println("Input: " + input);
        System.out.println("Transform:\n" + transform);
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Output: " + result.toPrettyString());
        System.out.println();
    }
    
    static void demo5ComplexTransform(MorphiumEngine engine, ObjectMapper mapper) throws Exception {
        System.out.println("🎯 Demo 5: Complex Nested Transformation");
        System.out.println("-----------------------------------");
        
        String input = "{\n" +
            "  \"user\":{\"id\":123,\"name\":\"Bob\",\"email\":\"bob@example.com\"},\n" +
            "  \"orders\":[{\"id\":1,\"amount\":100},{\"id\":2,\"amount\":200}]\n" +
            "}";
        
        String transform = "let root = $\n" +
            "let userData = root.user\n" +
            "let orderData = root.orders\n\n" +
            "function total(orders) {\n" +
            "  return reduce(orders, 'sum', 'order', 0, sum + order.amount)\n" +
            "}\n\n" +
            "{\n" +
            "  userId: userData.id,\n" +
            "  profile: {\n" +
            "    name: userData.name,\n" +
            "    email: userData.email\n" +
            "  },\n" +
            "  summary: {\n" +
            "    orderCount: len(orderData),\n" +
            "    totalSpent: total(orderData),\n" +
            "    avgOrder: total(orderData) / len(orderData)\n" +
            "  }\n" +
            "}";
        
        System.out.println("Input: " + input);
        System.out.println("Transform:\n" + transform);
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Output: " + result.toPrettyString());
        System.out.println();
    }
    
    static void demo6ArrayOps(MorphiumEngine engine, ObjectMapper mapper) throws Exception {
        System.out.println("📊 Demo 6: Array Operations");
        System.out.println("-----------------------------------");
        
        String input = "{\n" +
            "  \"products\":[\n" +
            "    {\"id\":\"A\",\"name\":\"Laptop\",\"price\":1000,\"stock\":5},\n" +
            "    {\"id\":\"B\",\"name\":\"Mouse\",\"price\":20,\"stock\":50},\n" +
            "    {\"id\":\"C\",\"name\":\"Keyboard\",\"price\":80,\"stock\":0}\n" +
            "  ]\n" +
            "}";
        
        String transform = "{\n" +
            "  inStock: filter($.products, 'p', p.stock > 0),\n" +
            "  expensive: filter($.products, 'p', p.price > 100),\n" +
            "  productNames: pluck($.products, 'name'),\n" +
            "  byId: indexBy($.products, 'id'),\n" +
            "  totalValue: reduce(\n" +
            "    $.products,\n" +
            "    'sum',\n" +
            "    'p',\n" +
            "    0,\n" +
            "    sum + (p.price * p.stock)\n" +
            "  )\n" +
            "}";
        
        System.out.println("Input: " + input);
        System.out.println("Transform:\n" + transform);
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Output: " + result.toPrettyString());
        System.out.println();
    }
    
    // Custom function: power(base, exponent)
    static class PowerFunction implements MorphiumFunction {
        @Override
        public String getName() {
            return "power";
        }
        
        @Override
        public int getMinParams() {
            return 2;
        }
        
        @Override
        public int getMaxParams() {
            return 2;
        }
        
        @Override
        public JsonNode call(JsonNode root, JsonNode[] params) {
            double base = params[0].asDouble();
            double exp = params[1].asDouble();
            return DoubleNode.valueOf(Math.pow(base, exp));
        }
    }
    
    // Custom function: formatCurrency(amount, currency)
    static class FormatCurrencyFunction implements MorphiumFunction {
        @Override
        public String getName() {
            return "formatCurrency";
        }
        
        @Override
        public int getMinParams() {
            return 2;
        }
        
        @Override
        public int getMaxParams() {
            return 2;
        }
        
        @Override
        public JsonNode call(JsonNode root, JsonNode[] params) {
            double amount = params[0].asDouble();
            String currency = params[1].asText();
            return TextNode.valueOf(String.format("%s %.2f", currency, amount));
        }
    }
}
