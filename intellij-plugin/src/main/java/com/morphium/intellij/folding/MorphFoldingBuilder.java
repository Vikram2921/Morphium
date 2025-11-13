package com.morphium.intellij.folding;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.*;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public class MorphFoldingBuilder extends FoldingBuilderEx {
    @NotNull @Override public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        return FoldingDescriptor.EMPTY;
    }
    @Nullable @Override public String getPlaceholderText(@NotNull ASTNode node) { return "{...}"; }
    @Override public boolean isCollapsedByDefault(@NotNull ASTNode node) { return false; }
}
