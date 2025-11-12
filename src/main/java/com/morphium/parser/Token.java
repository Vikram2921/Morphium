package com.morphium.parser;

public class Token {
    public enum Type {
        // Literals
        NUMBER, STRING, TRUE, FALSE, NULL,
        
        // Identifiers and keywords
        IDENTIFIER, LET, IF, FOR, OF, IMPORT, EXPORT, AS, FROM,
        
        // Operators
        PLUS, MINUS, STAR, SLASH, PERCENT,
        EQ, EQ_EQ, EQ_EQ_EQ, BANG_EQ, BANG_EQ_EQ,
        LT, LT_EQ, GT, GT_EQ,
        AMP_AMP, PIPE_PIPE, BANG,
        QUESTION, QUESTION_DOT, QUESTION_QUESTION, COLON,
        
        // Delimiters
        LPAREN, RPAREN, LBRACE, RBRACE, LBRACKET, RBRACKET,
        DOT, COMMA, SEMICOLON, ARROW,
        
        // Special
        EOF, NEWLINE
    }

    private final Type type;
    private final String lexeme;
    private final Object literal;
    private final int line;
    private final int column;
    private final String sourcePath;

    public Token(Type type, String lexeme, Object literal, int line, int column, String sourcePath) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
        this.column = column;
        this.sourcePath = sourcePath;
    }

    public Type getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public Object getLiteral() {
        return literal;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    @Override
    public String toString() {
        return String.format("Token{%s, '%s', %s, %d:%d}", type, lexeme, literal, line, column);
    }
}
