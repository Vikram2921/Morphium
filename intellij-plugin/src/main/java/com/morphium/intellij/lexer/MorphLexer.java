package com.morphium.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.morphium.intellij.psi.MorphTypes;
import com.intellij.psi.TokenType;

import java.io.Reader;

public class MorphLexer implements FlexLexer {
    
    private Reader reader;
    private CharSequence buffer;
    private int bufferIndex;
    private int bufferEnd;
    private int tokenStart;
    private int tokenEnd;
    private IElementType tokenType;
    
    public MorphLexer(Reader reader) {
        this.reader = reader;
    }
    
    @Override
    public void yybegin(int state) {
    }
    
    @Override
    public int yystate() {
        return 0;
    }
    
    @Override
    public void reset(CharSequence buffer, int start, int end, int initialState) {
        this.buffer = buffer;
        this.bufferIndex = start;
        this.bufferEnd = end;
        this.tokenStart = start;
    }
    
    @Override
    public IElementType advance() {
        if (bufferIndex >= bufferEnd) {
            return null;
        }
        
        tokenStart = bufferIndex;
        char ch = buffer.charAt(bufferIndex);
        
        // Skip whitespace
        if (Character.isWhitespace(ch)) {
            while (bufferIndex < bufferEnd && Character.isWhitespace(buffer.charAt(bufferIndex))) {
                bufferIndex++;
            }
            tokenEnd = bufferIndex;
            return TokenType.WHITE_SPACE;
        }
        
        // Line comment
        if (ch == '/' && bufferIndex + 1 < bufferEnd && buffer.charAt(bufferIndex + 1) == '/') {
            bufferIndex += 2;
            while (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) != '\n') {
                bufferIndex++;
            }
            tokenEnd = bufferIndex;
            return MorphTypes.COMMENT;
        }
        
        // Block comment
        if (ch == '/' && bufferIndex + 1 < bufferEnd && buffer.charAt(bufferIndex + 1) == '*') {
            bufferIndex += 2;
            while (bufferIndex + 1 < bufferEnd) {
                if (buffer.charAt(bufferIndex) == '*' && buffer.charAt(bufferIndex + 1) == '/') {
                    bufferIndex += 2;
                    break;
                }
                bufferIndex++;
            }
            tokenEnd = bufferIndex;
            return MorphTypes.COMMENT;
        }
        
        // String
        if (ch == '"') {
            bufferIndex++;
            while (bufferIndex < bufferEnd) {
                char c = buffer.charAt(bufferIndex);
                if (c == '"') {
                    bufferIndex++;
                    break;
                }
                if (c == '\\' && bufferIndex + 1 < bufferEnd) {
                    bufferIndex++;
                }
                bufferIndex++;
            }
            tokenEnd = bufferIndex;
            return MorphTypes.STRING;
        }
        
        // Number
        if (Character.isDigit(ch)) {
            while (bufferIndex < bufferEnd && (Character.isDigit(buffer.charAt(bufferIndex)) || buffer.charAt(bufferIndex) == '.')) {
                bufferIndex++;
            }
            tokenEnd = bufferIndex;
            return MorphTypes.NUMBER;
        }
        
        // Identifier or keyword
        if (Character.isLetter(ch) || ch == '_') {
            while (bufferIndex < bufferEnd && (Character.isLetterOrDigit(buffer.charAt(bufferIndex)) || buffer.charAt(bufferIndex) == '_')) {
                bufferIndex++;
            }
            tokenEnd = bufferIndex;
            String text = buffer.subSequence(tokenStart, tokenEnd).toString();
            
            switch (text) {
                case "function": return MorphTypes.FUNCTION;
                case "let": return MorphTypes.LET;
                case "global": return MorphTypes.GLOBAL;
                case "if": return MorphTypes.IF;
                case "else": return MorphTypes.ELSE;
                case "for": return MorphTypes.FOR;
                case "in": return MorphTypes.IN;
                case "while": return MorphTypes.WHILE;
                case "return": return MorphTypes.RETURN;
                case "break": return MorphTypes.BREAK;
                case "continue": return MorphTypes.CONTINUE;
                case "switch": return MorphTypes.SWITCH;
                case "case": return MorphTypes.CASE;
                case "default": return MorphTypes.DEFAULT;
                case "import": return MorphTypes.IMPORT;
                case "as": return MorphTypes.AS;
                case "export": return MorphTypes.EXPORT;
                case "true": return MorphTypes.TRUE;
                case "false": return MorphTypes.FALSE;
                case "null": return MorphTypes.NULL;
                default: return MorphTypes.IDENTIFIER;
            }
        }
        
        // Operators and punctuation
        tokenEnd = bufferIndex + 1;
        bufferIndex++;
        
        switch (ch) {
            case '(': return MorphTypes.LPAREN;
            case ')': return MorphTypes.RPAREN;
            case '{': return MorphTypes.LBRACE;
            case '}': return MorphTypes.RBRACE;
            case '[': return MorphTypes.LBRACKET;
            case ']': return MorphTypes.RBRACKET;
            case ';': return MorphTypes.SEMICOLON;
            case ',': return MorphTypes.COMMA;
            case '.': return MorphTypes.DOT;
            case ':': return MorphTypes.COLON;
            case '+': return MorphTypes.PLUS;
            case '-': return MorphTypes.MINUS;
            case '*': return MorphTypes.MULTIPLY;
            case '/': return MorphTypes.DIVIDE;
            case '%': return MorphTypes.MODULO;
            case '=':
                if (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) == '=') {
                    bufferIndex++;
                    tokenEnd = bufferIndex;
                    return MorphTypes.EQEQ;
                }
                return MorphTypes.EQ;
            case '!':
                if (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) == '=') {
                    bufferIndex++;
                    tokenEnd = bufferIndex;
                    return MorphTypes.NE;
                }
                return MorphTypes.NOT;
            case '<':
                if (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) == '=') {
                    bufferIndex++;
                    tokenEnd = bufferIndex;
                    return MorphTypes.LE;
                }
                return MorphTypes.LT;
            case '>':
                if (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) == '=') {
                    bufferIndex++;
                    tokenEnd = bufferIndex;
                    return MorphTypes.GE;
                }
                return MorphTypes.GT;
            case '&':
                if (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) == '&') {
                    bufferIndex++;
                    tokenEnd = bufferIndex;
                    return MorphTypes.AND;
                }
                break;
            case '|':
                if (bufferIndex < bufferEnd && buffer.charAt(bufferIndex) == '|') {
                    bufferIndex++;
                    tokenEnd = bufferIndex;
                    return MorphTypes.OR;
                }
                break;
        }
        
        return TokenType.BAD_CHARACTER;
    }
    
    @Override
    public int getTokenStart() {
        return tokenStart;
    }
    
    @Override
    public int getTokenEnd() {
        return tokenEnd;
    }
    
    public CharSequence getBufferSequence() {
        return buffer;
    }
    
    public int getBufferEnd() {
        return bufferEnd;
    }
}
