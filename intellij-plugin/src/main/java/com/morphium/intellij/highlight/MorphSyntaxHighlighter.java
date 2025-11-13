package com.morphium.intellij.highlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.morphium.intellij.lexer.MorphLexerAdapter;
import com.morphium.intellij.psi.MorphTypes;
import org.jetbrains.annotations.NotNull;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class MorphSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("MORPH_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING = createTextAttributesKey("MORPH_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("MORPH_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey COMMENT = createTextAttributesKey("MORPH_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey OPERATOR = createTextAttributesKey("MORPH_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("MORPH_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey FUNCTION_DECLARATION = createTextAttributesKey("MORPH_FUNCTION_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey FUNCTION_CALL = createTextAttributesKey("MORPH_FUNCTION_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey BRACES = createTextAttributesKey("MORPH_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("MORPH_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("MORPH_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);

    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull @Override public Lexer getHighlightingLexer() { return new MorphLexerAdapter(); }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(MorphTypes.FUNCTION) || tokenType.equals(MorphTypes.LET) || tokenType.equals(MorphTypes.GLOBAL) || tokenType.equals(MorphTypes.IF) ||
            tokenType.equals(MorphTypes.ELSE) || tokenType.equals(MorphTypes.FOR) || tokenType.equals(MorphTypes.WHILE) || tokenType.equals(MorphTypes.RETURN) ||
            tokenType.equals(MorphTypes.BREAK) || tokenType.equals(MorphTypes.CONTINUE) || tokenType.equals(MorphTypes.SWITCH) || tokenType.equals(MorphTypes.CASE) ||
            tokenType.equals(MorphTypes.DEFAULT) || tokenType.equals(MorphTypes.IMPORT) || tokenType.equals(MorphTypes.AS) || tokenType.equals(MorphTypes.EXPORT) ||
            tokenType.equals(MorphTypes.IN) || tokenType.equals(MorphTypes.TRUE) || tokenType.equals(MorphTypes.FALSE) || tokenType.equals(MorphTypes.NULL)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(MorphTypes.STRING)) { return STRING_KEYS;
        } else if (tokenType.equals(MorphTypes.NUMBER)) { return NUMBER_KEYS;
        } else if (tokenType.equals(MorphTypes.COMMENT)) { return COMMENT_KEYS;
        } else if (tokenType.equals(MorphTypes.LBRACE) || tokenType.equals(MorphTypes.RBRACE)) { return BRACES_KEYS;
        } else if (tokenType.equals(MorphTypes.LBRACKET) || tokenType.equals(MorphTypes.RBRACKET)) { return BRACKETS_KEYS;
        } else if (tokenType.equals(MorphTypes.LPAREN) || tokenType.equals(MorphTypes.RPAREN)) { return PARENTHESES_KEYS;
        } else if (tokenType.equals(MorphTypes.IDENTIFIER)) { return IDENTIFIER_KEYS;
        } else if (tokenType.equals(MorphTypes.PLUS) || tokenType.equals(MorphTypes.MINUS) || tokenType.equals(MorphTypes.MULTIPLY) || 
                   tokenType.equals(MorphTypes.DIVIDE) || tokenType.equals(MorphTypes.EQ) || tokenType.equals(MorphTypes.DOT)) { return OPERATOR_KEYS; }
        return EMPTY_KEYS;
    }
}
