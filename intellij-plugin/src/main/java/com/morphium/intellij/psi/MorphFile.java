package com.morphium.intellij.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.morphium.intellij.MorphFileType;
import com.morphium.intellij.MorphLanguage;
import org.jetbrains.annotations.NotNull;

public class MorphFile extends PsiFileBase {
    public MorphFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, MorphLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return MorphFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Morph File";
    }
}
