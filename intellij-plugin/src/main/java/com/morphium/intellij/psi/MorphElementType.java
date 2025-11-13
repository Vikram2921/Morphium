package com.morphium.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.morphium.intellij.MorphLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MorphElementType extends IElementType {
    public MorphElementType(@NotNull @NonNls String debugName) {
        super(debugName, MorphLanguage.INSTANCE);
    }
}
