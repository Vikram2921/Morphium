package com.morphium.intellij.reference;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.morphium.intellij.psi.MorphTypes;
import org.jetbrains.annotations.NotNull;
public class MorphReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(MorphTypes.IDENTIFIER),
            new PsiReferenceProvider() {
                @NotNull @Override public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                    return PsiReference.EMPTY_ARRAY;
                }
            });
    }
}
