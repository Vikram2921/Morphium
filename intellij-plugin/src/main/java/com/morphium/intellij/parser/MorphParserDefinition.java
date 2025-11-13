package com.morphium.intellij.parser;
import com.intellij.lang.*;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.*;
import com.morphium.intellij.MorphLanguage;
import com.morphium.intellij.lexer.MorphLexerAdapter;
import com.morphium.intellij.psi.MorphFile;
import com.morphium.intellij.psi.MorphTypes;
import org.jetbrains.annotations.NotNull;
public class MorphParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(com.intellij.psi.TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(MorphTypes.COMMENT);
    public static final TokenSet STRINGS = TokenSet.create(MorphTypes.STRING);
    public static final IFileElementType FILE = new IFileElementType(MorphLanguage.INSTANCE);
    @NotNull @Override public Lexer createLexer(Project project) { return new MorphLexerAdapter(); }
    @Override public @NotNull PsiParser createParser(Project project) { return new MorphParser(); }
    @Override public @NotNull IFileElementType getFileNodeType() { return FILE; }
    @NotNull @Override public TokenSet getWhitespaceTokens() { return WHITE_SPACES; }
    @NotNull @Override public TokenSet getCommentTokens() { return COMMENTS; }
    @NotNull @Override public TokenSet getStringLiteralElements() { return STRINGS; }
    @NotNull @Override public PsiElement createElement(ASTNode node) { return new MorphPsiElement(node); }
    @Override public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) { return new MorphFile(viewProvider); }
}
