package com.morphium.intellij;
import com.intellij.lang.*;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.morphium.intellij.psi.MorphTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public class MorphBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
        new BracePair(MorphTypes.LPAREN, MorphTypes.RPAREN, false),
        new BracePair(MorphTypes.LBRACE, MorphTypes.RBRACE, true),
        new BracePair(MorphTypes.LBRACKET, MorphTypes.RBRACKET, false)
    };
    @Override public BracePair[] getPairs() { return PAIRS; }
    @Override public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) { return true; }
    @Override public int getCodeConstructStart(PsiFile file, int openingBraceOffset) { return openingBraceOffset; }
}
