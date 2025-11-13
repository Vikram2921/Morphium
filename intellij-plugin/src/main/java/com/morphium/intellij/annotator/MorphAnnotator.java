package com.morphium.intellij.annotator;
import com.intellij.lang.annotation.*;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
public class MorphAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        // Basic error checking can be added here
    }
}
