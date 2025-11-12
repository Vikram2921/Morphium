package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;

/**
 * Examples demonstrating if-else, switch, and for-of control flow
 */
public class ControlFlowExamples {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Morphium Control Flow Examples ===\n");
        
        demoIfElse();
        demoSwitch();
        demoForOf();
        demoRealWorldExample();
    }
    
    /**
     * Demonstrates if-else statements
     */
    private static void demoIfElse() throws Exception {
        System.out.println("Example 1: If-Else Statements");
        System.out.println("------------------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Simple if-else
        String script1 = 
            "{ " +
            "  name: $.name, " +
            "  status: if ($.age >= 18) { 'adult' } else { 'minor' } " +
            "}";
        
        JsonNode input1 = mapper.readTree("{\"name\":\"Alice\",\"age\":25}");
        JsonNode result1 = engine.transformFromString(script1, input1);
        System.out.println("Adult check: " + result1);
        
        // Nested if-else (else-if chain)
        String script2 = 
            "{ " +
            "  score: $.score, " +
            "  grade: if ($.score >= 90) { " +
            "    'A' " +
            "  } else if ($.score >= 80) { " +
            "    'B' " +
            "  } else if ($.score >= 70) { " +
            "    'C' " +
            "  } else { " +
            "    'F' " +
            "  } " +
            "}";
        
        JsonNode input2 = mapper.readTree("{\"score\":85}");
        JsonNode result2 = engine.transformFromString(script2, input2);
        System.out.println("Grade: " + result2);
        System.out.println();
    }
    
    /**
     * Demonstrates switch statements
     */
    private static void demoSwitch() throws Exception {
        System.out.println("Example 2: Switch Statements");
        System.out.println("----------------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Switch with strings
        String script1 = 
            "{ " +
            "  day: $.day, " +
            "  type: switch ($.day) { " +
            "    case 'Monday': 'weekday' " +
            "    case 'Tuesday': 'weekday' " +
            "    case 'Saturday': 'weekend' " +
            "    case 'Sunday': 'weekend' " +
            "    default: 'unknown' " +
            "  } " +
            "}";
        
        JsonNode input1 = mapper.readTree("{\"day\":\"Saturday\"}");
        JsonNode result1 = engine.transformFromString(script1, input1);
        System.out.println("Day type: " + result1);
        
        // Switch with calculations
        String script2 = 
            "{ " +
            "  operation: $.op, " +
            "  result: switch ($.op) { " +
            "    case 'add': $.a + $.b " +
            "    case 'subtract': $.a - $.b " +
            "    case 'multiply': $.a * $.b " +
            "    case 'divide': $.a / $.b " +
            "    default: 0 " +
            "  } " +
            "}";
        
        JsonNode input2 = mapper.readTree("{\"op\":\"multiply\",\"a\":5,\"b\":3}");
        JsonNode result2 = engine.transformFromString(script2, input2);
        System.out.println("Calculation: " + result2);
        System.out.println();
    }
    
    /**
     * Demonstrates for-of loops
     */
    private static void demoForOf() throws Exception {
        System.out.println("Example 3: For-Of Loops");
        System.out.println("-----------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Simple transformation
        String script1 = 
            "{ " +
            "  original: $.numbers, " +
            "  doubled: for (n of $.numbers) { n * 2 } " +
            "}";
        
        JsonNode input1 = mapper.readTree("{\"numbers\":[1,2,3,4,5]}");
        JsonNode result1 = engine.transformFromString(script1, input1);
        System.out.println("Doubled: " + result1);
        
        // Object transformation
        String script2 = 
            "{ " +
            "  users: for (user of $.users) { " +
            "    { " +
            "      fullName: user.firstName + ' ' + user.lastName, " +
            "      email: user.email " +
            "    } " +
            "  } " +
            "}";
        
        JsonNode input2 = mapper.readTree(
            "{\"users\":[" +
            "  {\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@example.com\"}," +
            "  {\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"email\":\"jane@example.com\"}" +
            "]}"
        );
        JsonNode result2 = engine.transformFromString(script2, input2);
        System.out.println("Users: " + result2);
        System.out.println();
    }
    
    /**
     * Real-world example combining all control flow features
     */
    private static void demoRealWorldExample() throws Exception {
        System.out.println("Example 4: Real-World Order Processing");
        System.out.println("--------------------------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "{ " +
            "  orderId: $.orderId, " +
            "  items: for (item of $.items) { " +
            "    { " +
            "      name: item.name, " +
            "      quantity: item.quantity, " +
            "      price: item.price, " +
            "      discount: switch (item.category) { " +
            "        case 'electronics': item.price * 0.1 " +
            "        case 'books': item.price * 0.15 " +
            "        case 'clothing': item.price * 0.2 " +
            "        default: 0 " +
            "      }, " +
            "      finalPrice: item.price - (switch (item.category) { " +
            "        case 'electronics': item.price * 0.1 " +
            "        case 'books': item.price * 0.15 " +
            "        case 'clothing': item.price * 0.2 " +
            "        default: 0 " +
            "      }) " +
            "    } " +
            "  }, " +
            "  shippingCost: if ($.total > 100) { 0 } else { 10 }, " +
            "  status: if ($.isPaid) { 'confirmed' } else { 'pending' } " +
            "}";
        
        JsonNode input = mapper.readTree(
            "{" +
            "  \"orderId\":\"ORD-123\"," +
            "  \"total\":150," +
            "  \"isPaid\":true," +
            "  \"items\":[" +
            "    {\"name\":\"Laptop\",\"category\":\"electronics\",\"price\":100,\"quantity\":1}," +
            "    {\"name\":\"Book\",\"category\":\"books\",\"price\":20,\"quantity\":2}," +
            "    {\"name\":\"Shirt\",\"category\":\"clothing\",\"price\":30,\"quantity\":1}" +
            "  ]" +
            "}"
        );
        
        JsonNode result = engine.transformFromString(script, input);
        System.out.println("Processed order:");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
}
