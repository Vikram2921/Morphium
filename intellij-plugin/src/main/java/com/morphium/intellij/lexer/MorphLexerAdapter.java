package com.morphium.intellij.lexer;

import com.intellij.lexer.FlexAdapter;

public class MorphLexerAdapter extends FlexAdapter {
    public MorphLexerAdapter() {
        super(new MorphLexer(null));
    }
}
