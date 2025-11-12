package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.Logger;

/**
 * Complete example showing all new Morphium features:
 * - If-else statements
 * - Switch statements
 * - For-of loops
 * - Error handling
 * - Logging
 */
public class CompleteFeatureDemo {
    
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MorphiumEngine engine = new MorphiumEngine();
        
        // Setup logging
        engine.setLogger(new Logger() {
            @Override
            public void log(Level level, String message) {
                if (level != Level.DEBUG) {  // Skip debug for cleaner output
                    System.out.println("[" + level + "] " + message);
                }
            }
        });
        
        System.out.println("=== Complete Morphium Feature Demo ===\n");
        
        // Sample input: E-commerce order data
        String inputJson = 
            "{" +
            "  \"orderId\": \"ORD-2025-001\"," +
            "  \"customer\": {" +
            "    \"name\": \"John Doe\"," +
            "    \"age\": 28," +
            "    \"membershipLevel\": \"gold\"" +
            "  }," +
            "  \"items\": [" +
            "    {\"id\": 1, \"name\": \"Laptop\", \"category\": \"electronics\", \"price\": 1200, \"quantity\": 1}," +
            "    {\"id\": 2, \"name\": \"Mouse\", \"category\": \"electronics\", \"price\": 25, \"quantity\": 2}," +
            "    {\"id\": 3, \"name\": \"Book\", \"category\": \"books\", \"price\": 15, \"quantity\": 3}," +
            "    {\"id\": 4, \"name\": \"Shirt\", \"category\": \"clothing\", \"price\": 30, \"quantity\": 2}" +
            "  ]," +
            "  \"couponCode\": \"SAVE10\"" +
            "}";
        
        // Transformation script using ALL new features
        String script = 
            "log('Processing order:', $.orderId);" +
            "" +
            "let customerType = if ($.customer.age < 18) {" +
            "  'minor'" +
            "} else if ($.customer.age >= 65) {" +
            "  'senior'" +
            "} else {" +
            "  'adult'" +
            "};" +
            "logInfo('Customer type:', customerType);" +
            "" +
            "let processedItems = for (item of $.items) {" +
            "  logDebug('Processing item:', item.name);" +
            "  " +
            "  let categoryDiscount = switch (item.category) {" +
            "    case 'electronics': 0.10" +
            "    case 'books': 0.15" +
            "    case 'clothing': 0.20" +
            "    default: 0" +
            "  };" +
            "  " +
            "  let membershipMultiplier = switch ($.customer.membershipLevel) {" +
            "    case 'bronze': 1.0" +
            "    case 'silver': 1.5" +
            "    case 'gold': 2.0" +
            "    case 'platinum': 3.0" +
            "    default: 1.0" +
            "  };" +
            "  " +
            "  let baseDiscount = item.price * item.quantity * categoryDiscount;" +
            "  let finalDiscount = baseDiscount * membershipMultiplier;" +
            "  let finalPrice = (item.price * item.quantity) - finalDiscount;" +
            "  " +
            "  {" +
            "    id: item.id," +
            "    name: item.name," +
            "    category: item.category," +
            "    quantity: item.quantity," +
            "    unitPrice: item.price," +
            "    subtotal: item.price * item.quantity," +
            "    discount: finalDiscount," +
            "    finalPrice: finalPrice" +
            "  }" +
            "};" +
            "" +
            "let total = 0;" +
            "for (item of processedItems) {" +
            "  total = total + item.finalPrice" +
            "};" +
            "" +
            "let shippingCost = if (total > 1000) {" +
            "  logInfo('Free shipping applied');" +
            "  0" +
            "} else if (total > 500) {" +
            "  5" +
            "} else {" +
            "  15" +
            "};" +
            "" +
            "let validationError = if (total < 0) {" +
            "  error('Invalid total: total cannot be negative')" +
            "};" +
            "" +
            "logInfo('Order total:', total);" +
            "logInfo('Shipping cost:', shippingCost);" +
            "" +
            "{" +
            "  orderId: $.orderId," +
            "  customer: {" +
            "    name: $.customer.name," +
            "    type: customerType," +
            "    membershipLevel: $.customer.membershipLevel" +
            "  }," +
            "  items: processedItems," +
            "  summary: {" +
            "    subtotal: total," +
            "    shipping: shippingCost," +
            "    grandTotal: total + shippingCost" +
            "  }," +
            "  status: if ($.couponCode) { 'processed_with_coupon' } else { 'processed' }" +
            "}";
        
        JsonNode input = mapper.readTree(inputJson);
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println("\n=== Final Result ===");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        
        System.out.println("\n=== Features Demonstrated ===");
        System.out.println("✓ If-else statements (age check, shipping logic)");
        System.out.println("✓ Switch statements (category discounts, membership multipliers)");
        System.out.println("✓ For-of loops (item processing, total calculation)");
        System.out.println("✓ Logging (INFO level messages)");
        System.out.println("✓ Error handling (validation check)");
        System.out.println("✓ Nested control flow (all combined)");
    }
}
