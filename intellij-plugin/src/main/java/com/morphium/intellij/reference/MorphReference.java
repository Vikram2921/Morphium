package com.morphium.intellij.reference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public class MorphReference extends PsiReferenceBase<PsiElement> {
    public MorphReference(@NotNull PsiElement element, TextRange rangeInElement) { super(element, rangeInElement); }
    @Nullable @Override public PsiElement resolve() { return null; }
    @NotNull @Override public Object[] getVariants() { return new Object[0]; }
}
