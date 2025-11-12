package com.morphium.parser;

import com.morphium.core.MorphiumException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private static final Map<String, Token.Type> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("let", Token.Type.LET);
        KEYWORDS.put("if", Token.Type.IF);
        KEYWORDS.put("for", Token.Type.FOR);
        KEYWORDS.put("of", Token.Type.OF);
        KEYWORDS.put("import", Token.Type.IMPORT);
        KEYWORDS.put("export", Token.Type.EXPORT);
        KEYWORDS.put("as", Token.Type.AS);
        KEYWORDS.put("from", Token.Type.FROM);
        KEYWORDS.put("function", Token.Type.FUNCTION);
        KEYWORDS.put("return", Token.Type.RETURN);
        KEYWORDS.put("global", Token.Type.GLOBAL);
        KEYWORDS.put("true", Token.Type.TRUE);
        KEYWORDS.put("false", Token.Type.FALSE);
        KEYWORDS.put("null", Token.Type.NULL);
    }

    private final String source;
    private final String sourcePath;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int column = 1;

    public Lexer(String source, String sourcePath) {
        this.source = source;
        this.sourcePath = sourcePath;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(Token.Type.EOF, "", null, line, column, sourcePath));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(Token.Type.LPAREN); break;
            case ')': addToken(Token.Type.RPAREN); break;
            case '{': addToken(Token.Type.LBRACE); break;
            case '}': addToken(Token.Type.RBRACE); break;
            case '[': addToken(Token.Type.LBRACKET); break;
            case ']': addToken(Token.Type.RBRACKET); break;
            case ',': addToken(Token.Type.COMMA); break;
            case ':': addToken(Token.Type.COLON); break;
            case ';': addToken(Token.Type.SEMICOLON); break;
            case '+': addToken(Token.Type.PLUS); break;
            case '-': addToken(Token.Type.MINUS); break;
            case '*': addToken(Token.Type.STAR); break;
            case '%': addToken(Token.Type.PERCENT); break;
            case '!':
                if (match('=')) {
                    addToken(match('=') ? Token.Type.BANG_EQ_EQ : Token.Type.BANG_EQ);
                } else {
                    addToken(Token.Type.BANG);
                }
                break;
            case '=':
                if (match('=')) {
                    addToken(match('=') ? Token.Type.EQ_EQ_EQ : Token.Type.EQ_EQ);
                } else if (match('>')) {
                    addToken(Token.Type.ARROW);
                } else {
                    addToken(Token.Type.EQ);
                }
                break;
            case '<':
                addToken(match('=') ? Token.Type.LT_EQ : Token.Type.LT);
                break;
            case '>':
                addToken(match('=') ? Token.Type.GT_EQ : Token.Type.GT);
                break;
            case '&':
                if (match('&')) addToken(Token.Type.AMP_AMP);
                else throw error("Unexpected character: &");
                break;
            case '|':
                if (match('|')) addToken(Token.Type.PIPE_PIPE);
                else throw error("Unexpected character: |");
                break;
            case '?':
                if (match('?')) {
                    addToken(Token.Type.QUESTION_QUESTION);
                } else if (match('.')) {
                    addToken(Token.Type.QUESTION_DOT);
                } else {
                    addToken(Token.Type.QUESTION);
                }
                break;
            case '.':
                addToken(Token.Type.DOT);
                break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else if (match('*')) {
                    blockComment();
                } else {
                    addToken(Token.Type.SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                column = 1;
                break;
            case '"':
            case '\'':
                string(c);
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    throw error("Unexpected character: " + c);
                }
                break;
        }
    }

    private void blockComment() {
        while (!isAtEnd()) {
            if (peek() == '*' && peekNext() == '/') {
                advance(); // consume *
                advance(); // consume /
                break;
            }
            if (peek() == '\n') {
                line++;
                column = 1;
            }
            advance();
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        Token.Type type = KEYWORDS.getOrDefault(text, Token.Type.IDENTIFIER);
        
        Object literal = null;
        if (type == Token.Type.TRUE) literal = true;
        else if (type == Token.Type.FALSE) literal = false;
        
        addToken(type, literal);
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }

        String text = source.substring(start, current);
        addToken(Token.Type.NUMBER, Double.parseDouble(text));
    }

    private void string(char quote) {
        StringBuilder value = new StringBuilder();
        
        while (peek() != quote && !isAtEnd()) {
            if (peek() == '\n') {
                line++;
                column = 1;
            }
            if (peek() == '\\') {
                advance();
                if (!isAtEnd()) {
                    char escaped = advance();
                    switch (escaped) {
                        case 'n': value.append('\n'); break;
                        case 't': value.append('\t'); break;
                        case 'r': value.append('\r'); break;
                        case '\\': value.append('\\'); break;
                        case '"': value.append('"'); break;
                        case '\'': value.append('\''); break;
                        default: value.append(escaped); break;
                    }
                }
            } else {
                value.append(advance());
            }
        }

        if (isAtEnd()) {
            throw error("Unterminated string");
        }

        advance(); // closing quote

        addToken(Token.Type.STRING, value.toString());
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        column++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '$';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private char advance() {
        column++;
        return source.charAt(current++);
    }

    private void addToken(Token.Type type) {
        addToken(type, null);
    }

    private void addToken(Token.Type type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line, column - text.length(), sourcePath));
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private MorphiumException error(String message) {
        return new MorphiumException(message, sourcePath, line, column);
    }
}
