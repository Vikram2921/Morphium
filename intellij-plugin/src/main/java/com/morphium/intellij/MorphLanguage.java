package com.morphium.intellij;

import com.intellij.lang.Language;

public class MorphLanguage extends Language {
    public static final MorphLanguage INSTANCE = new MorphLanguage();

    private MorphLanguage() {
        super("Morph");
    }
}
