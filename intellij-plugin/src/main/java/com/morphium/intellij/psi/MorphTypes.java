package com.morphium.intellij.psi;

import com.intellij.psi.tree.IElementType;

public interface MorphTypes {
    // Keywords
    IElementType FUNCTION = new MorphElementType("FUNCTION");
    IElementType LET = new MorphElementType("LET");
    IElementType GLOBAL = new MorphElementType("GLOBAL");
    IElementType IF = new MorphElementType("IF");
    IElementType ELSE = new MorphElementType("ELSE");
    IElementType FOR = new MorphElementType("FOR");
    IElementType IN = new MorphElementType("IN");
    IElementType WHILE = new MorphElementType("WHILE");
    IElementType RETURN = new MorphElementType("RETURN");
    IElementType BREAK = new MorphElementType("BREAK");
    IElementType CONTINUE = new MorphElementType("CONTINUE");
    IElementType SWITCH = new MorphElementType("SWITCH");
    IElementType CASE = new MorphElementType("CASE");
    IElementType DEFAULT = new MorphElementType("DEFAULT");
    IElementType IMPORT = new MorphElementType("IMPORT");
    IElementType AS = new MorphElementType("AS");
    IElementType EXPORT = new MorphElementType("EXPORT");
    IElementType TRUE = new MorphElementType("TRUE");
    IElementType FALSE = new MorphElementType("FALSE");
    IElementType NULL = new MorphElementType("NULL");

    // Operators
    IElementType PLUS = new MorphElementType("PLUS");
    IElementType MINUS = new MorphElementType("MINUS");
    IElementType MULTIPLY = new MorphElementType("MULTIPLY");
    IElementType DIVIDE = new MorphElementType("DIVIDE");
    IElementType MODULO = new MorphElementType("MODULO");
    IElementType EQ = new MorphElementType("EQ");
    IElementType EQEQ = new MorphElementType("EQEQ");
    IElementType NE = new MorphElementType("NE");
    IElementType LT = new MorphElementType("LT");
    IElementType LE = new MorphElementType("LE");
    IElementType GT = new MorphElementType("GT");
    IElementType GE = new MorphElementType("GE");
    IElementType AND = new MorphElementType("AND");
    IElementType OR = new MorphElementType("OR");
    IElementType NOT = new MorphElementType("NOT");
    IElementType QUESTION = new MorphElementType("QUESTION");
    IElementType COLON = new MorphElementType("COLON");
    IElementType NULL_COALESCE = new MorphElementType("NULL_COALESCE");

    // Delimiters
    IElementType LPAREN = new MorphElementType("LPAREN");
    IElementType RPAREN = new MorphElementType("RPAREN");
    IElementType LBRACE = new MorphElementType("LBRACE");
    IElementType RBRACE = new MorphElementType("RBRACE");
    IElementType LBRACKET = new MorphElementType("LBRACKET");
    IElementType RBRACKET = new MorphElementType("RBRACKET");
    IElementType SEMICOLON = new MorphElementType("SEMICOLON");
    IElementType COMMA = new MorphElementType("COMMA");
    IElementType DOT = new MorphElementType("DOT");
    IElementType RANGE = new MorphElementType("RANGE");

    // Literals
    IElementType STRING = new MorphElementType("STRING");
    IElementType NUMBER = new MorphElementType("NUMBER");
    IElementType IDENTIFIER = new MorphElementType("IDENTIFIER");
    IElementType COMMENT = new MorphElementType("COMMENT");
}
