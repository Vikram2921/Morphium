package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

public interface Expression {
    JsonNode evaluate(Context context);
}
