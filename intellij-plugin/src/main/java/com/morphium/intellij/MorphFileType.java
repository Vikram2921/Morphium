package com.morphium.intellij;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MorphFileType extends LanguageFileType {
    public static final MorphFileType INSTANCE = new MorphFileType();

    private MorphFileType() {
        super(MorphLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Morph File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Morphium DSL file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "morph";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
