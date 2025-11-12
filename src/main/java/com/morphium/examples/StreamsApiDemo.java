package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.function.MorphiumFunction;

public class StreamsApiDemo {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        System.out.println("================================================================================");
        System.out.println("Morphium DSL - Java Streams API Demo");
        System.out.println("================================================================================");
        
        demo1StreamOperations(engine);
        demo2AdvancedOperations(engine);
        demo3Aggregation(engine);
        demo4GroupingOperations(engine);
        demo5FlatMapOperations(engine);
        demo6CustomFunctions(engine);
        demo7RunMorph(engine);
        
        System.out.println("\n================================================================================");
        System.out.println("All demos completed successfully!");
        System.out.println("================================================================================");
    }
    
    private static void demo1StreamOperations(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 1] Basic Stream Operations");
        System.out.println("--------------------------------------------------------------------------------");
        
        String input = "{ \"numbers\": [5, 12, 3, 18, 7, 9, 15, 22, 1, 14] }";
        
        String transform = "let numbers = $.numbers\n\n" +
                "{\n" +
                "  original: numbers,\n" +
                "  filtered: filter(numbers, \"n\", n > 10),\n" +
                "  mapped: map(numbers, \"n\", n * 2),\n" +
                "  sorted: sorted(numbers),\n" +
                "  first: findFirst(numbers, \"n\", n > 15),\n" +
                "  anyGreaterThan20: anyMatch(numbers, \"n\", n > 20),\n" +
                "  allPositive: allMatch(numbers, \"n\", n > 0),\n" +
                "  noneNegative: noneMatch(numbers, \"n\", n < 0),\n" +
                "  count: count(numbers),\n" +
                "  limit3: limit(numbers, 3),\n" +
                "  skip2: skip(numbers, 2)\n" +
                "}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
    
    private static void demo2AdvancedOperations(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 2] Advanced Filtering and Mapping");
        System.out.println("--------------------------------------------------------------------------------");
        
        String input = "{ \"products\": [" +
                "{\"id\": \"P1\", \"name\": \"Laptop\", \"price\": 999, \"category\": \"Electronics\", \"stock\": 15}," +
                "{\"id\": \"P2\", \"name\": \"Mouse\", \"price\": 29, \"category\": \"Electronics\", \"stock\": 150}," +
                "{\"id\": \"P3\", \"name\": \"Desk\", \"price\": 299, \"category\": \"Furniture\", \"stock\": 8}" +
                "]}";
        
        String transform = "let products = $.products\n\n" +
                "{\n" +
                "  expensiveProducts: filter(products, \"p\", p.price > 200),\n" +
                "  lowStock: filter(products, \"p\", p.stock < 10),\n" +
                "  sortedByPrice: sorted(products, \"price\"),\n" +
                "  electronics: filter(products, \"p\", p.category == \"Electronics\")\n" +
                "}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
    
    private static void demo3Aggregation(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 3] Aggregation Functions");
        System.out.println("--------------------------------------------------------------------------------");
        
        String input = "{ \"sales\": [" +
                "{\"month\": \"Jan\", \"amount\": 15000}," +
                "{\"month\": \"Feb\", \"amount\": 18000}," +
                "{\"month\": \"Mar\", \"amount\": 22000}" +
                "]}";
        
        String transform = "let sales = $.sales\n" +
                "let amounts = pluck(sales, \"amount\")\n\n" +
                "{\n" +
                "  sales: sales,\n" +
                "  statistics: {\n" +
                "    totalSales: sum(amounts),\n" +
                "    averageSales: avg(amounts),\n" +
                "    minSales: min(amounts),\n" +
                "    maxSales: max(amounts),\n" +
                "    monthCount: count(sales)\n" +
                "  }\n" +
                "}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
    
    private static void demo4GroupingOperations(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 4] GroupBy and Partition");
        System.out.println("--------------------------------------------------------------------------------");
        
        String input = "{ \"employees\": [" +
                "{\"name\": \"Alice\", \"department\": \"Engineering\", \"salary\": 90000, \"active\": true}," +
                "{\"name\": \"Bob\", \"department\": \"Sales\", \"salary\": 70000, \"active\": true}," +
                "{\"name\": \"Charlie\", \"department\": \"Engineering\", \"salary\": 95000, \"active\": false}" +
                "]}";
        
        String transform = "let employees = $.employees\n\n" +
                "{\n" +
                "  byDepartment: groupBy(employees, \"department\"),\n" +
                "  activeEmployees: partition(employees, \"e\", e.active),\n" +
                "  highEarners: partition(employees, \"e\", e.salary > 80000)\n" +
                "}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
    
    private static void demo5FlatMapOperations(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 5] FlatMap and Distinct");
        System.out.println("--------------------------------------------------------------------------------");
        
        String input = "{ \"users\": [" +
                "{\"name\": \"Alice\", \"skills\": [\"Java\", \"Python\", \"SQL\"]}," +
                "{\"name\": \"Bob\", \"skills\": [\"JavaScript\", \"Python\", \"React\"]}," +
                "{\"name\": \"Charlie\", \"skills\": [\"Java\", \"C++\", \"Python\"]}" +
                "]}";
        
        String transform = "let users = $.users\n\n" +
                "{\n" +
                "  allSkills: flatMap(users, \"u\", u.skills),\n" +
                "  uniqueSkills: distinct(flatMap(users, \"u\", u.skills)),\n" +
                "  skillCount: count(distinct(flatMap(users, \"u\", u.skills)))\n" +
                "}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
    
    private static void demo6CustomFunctions(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 6] Custom Functions");
        System.out.println("--------------------------------------------------------------------------------");
        
        engine.registerFunction(new MorphiumFunction() {
            @Override
            public String getName() {
                return "calculateDiscount";
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
                double price = params[0].asDouble();
                String customerType = params[1].asText();
                
                double discountRate;
                if (customerType.equals("vip")) {
                    discountRate = 0.25;
                } else if (customerType.equals("premium")) {
                    discountRate = 0.15;
                } else {
                    discountRate = 0.05;
                }
                
                return mapper.valueToTree(price * (1 - discountRate));
            }
        });
        
        String input = "{ \"orders\": [" +
                "{\"customer\": \"Alice\", \"type\": \"vip\", \"amount\": 1000}," +
                "{\"customer\": \"Bob\", \"type\": \"premium\", \"amount\": 500}," +
                "{\"customer\": \"Charlie\", \"type\": \"regular\", \"amount\": 300}" +
                "]}";
        
        String transform = "{\n" +
                "  orders: map($.orders, \"o\", {\n" +
                "    customer: o.customer,\n" +
                "    type: o.type,\n" +
                "    originalAmount: o.amount,\n" +
                "    finalAmount: calculateDiscount(o.amount, o.type)\n" +
                "  })\n" +
                "}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
    
    private static void demo7RunMorph(MorphiumEngine engine) throws Exception {
        System.out.println("\n[Demo 7] Run Another Morph File");
        System.out.println("--------------------------------------------------------------------------------");
        
        String input = "{ \"orderId\": \"ORD-12345\", \"customerType\": \"premium\", \"items\": [" +
                "{\"id\": \"ITM-1\", \"name\": \"Widget\", \"price\": 29.99, \"quantity\": 3}," +
                "{\"id\": \"ITM-2\", \"name\": \"Gadget\", \"price\": 49.99, \"quantity\": 2}" +
                "]}";
        
        String transform = "runMorph(\"examples/morphs/process-orders.morph\", $)";
        
        try {
            JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
            System.out.println("Result:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception e) {
            System.out.println("Note: runMorph demo requires example morph files.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
