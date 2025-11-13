package com.morphium.intellij.parser;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
public class MorphPsiElement extends ASTWrapperPsiElement {
    public MorphPsiElement(@NotNull ASTNode node) { super(node); }
}
