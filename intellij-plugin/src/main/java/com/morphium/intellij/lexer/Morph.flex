package com.morphium.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.morphium.intellij.psi.MorphTypes;
import com.intellij.psi.TokenType;

%%

%class MorphLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

WHITE_SPACE=[\ \n\t\r\f]+
LINE_COMMENT="//"[^\r\n]*
BLOCK_COMMENT="/*" [^*] ~"*/" | "/*" "*"+ "/"

IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
NUMBER=[0-9]+(\.[0-9]+)?
STRING=\"([^\\\"]|\\.)*\"

%%

<YYINITIAL> {
    {WHITE_SPACE}           { return TokenType.WHITE_SPACE; }
    {LINE_COMMENT}          { return MorphTypes.COMMENT; }
    {BLOCK_COMMENT}         { return MorphTypes.COMMENT; }

    // Keywords
    "function"              { return MorphTypes.FUNCTION; }
    "let"                   { return MorphTypes.LET; }
    "global"                { return MorphTypes.GLOBAL; }
    "if"                    { return MorphTypes.IF; }
    "else"                  { return MorphTypes.ELSE; }
    "for"                   { return MorphTypes.FOR; }
    "in"                    { return MorphTypes.IN; }
    "while"                 { return MorphTypes.WHILE; }
    "return"                { return MorphTypes.RETURN; }
    "break"                 { return MorphTypes.BREAK; }
    "continue"              { return MorphTypes.CONTINUE; }
    "switch"                { return MorphTypes.SWITCH; }
    "case"                  { return MorphTypes.CASE; }
    "default"               { return MorphTypes.DEFAULT; }
    "import"                { return MorphTypes.IMPORT; }
    "as"                    { return MorphTypes.AS; }
    "export"                { return MorphTypes.EXPORT; }
    "true"                  { return MorphTypes.TRUE; }
    "false"                 { return MorphTypes.FALSE; }
    "null"                  { return MorphTypes.NULL; }

    // Operators
    "+"                     { return MorphTypes.PLUS; }
    "-"                     { return MorphTypes.MINUS; }
    "*"                     { return MorphTypes.MULTIPLY; }
    "/"                     { return MorphTypes.DIVIDE; }
    "%"                     { return MorphTypes.MODULO; }
    "="                     { return MorphTypes.EQ; }
    "=="                    { return MorphTypes.EQEQ; }
    "!="                    { return MorphTypes.NE; }
    "<"                     { return MorphTypes.LT; }
    "<="                    { return MorphTypes.LE; }
    ">"                     { return MorphTypes.GT; }
    ">="                    { return MorphTypes.GE; }
    "&&"                    { return MorphTypes.AND; }
    "||"                    { return MorphTypes.OR; }
    "!"                     { return MorphTypes.NOT; }
    "?"                     { return MorphTypes.QUESTION; }
    ":"                     { return MorphTypes.COLON; }
    "??"                    { return MorphTypes.NULL_COALESCE; }

    // Delimiters
    "("                     { return MorphTypes.LPAREN; }
    ")"                     { return MorphTypes.RPAREN; }
    "{"                     { return MorphTypes.LBRACE; }
    "}"                     { return MorphTypes.RBRACE; }
    "["                     { return MorphTypes.LBRACKET; }
    "]"                     { return MorphTypes.RBRACKET; }
    ";"                     { return MorphTypes.SEMICOLON; }
    ","                     { return MorphTypes.COMMA; }
    "."                     { return MorphTypes.DOT; }
    ".."                    { return MorphTypes.RANGE; }

    {STRING}                { return MorphTypes.STRING; }
    {NUMBER}                { return MorphTypes.NUMBER; }
    {IDENTIFIER}            { return MorphTypes.IDENTIFIER; }
}

[^]                         { return TokenType.BAD_CHARACTER; }
