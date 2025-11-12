package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

public interface Expression {
    JsonElement evaluate(Context context);
}
