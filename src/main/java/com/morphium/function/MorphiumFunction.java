package com.morphium.function;

import com.fasterxml.jackson.databind.JsonNode;

public interface MorphiumFunction {
    
    String getName();
    
    int getMinParams();
    
    int getMaxParams();
    
    JsonNode call(JsonNode root, JsonNode[] params);
}
