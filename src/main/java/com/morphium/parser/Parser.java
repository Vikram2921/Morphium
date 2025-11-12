package com.morphium.parser;

import com.morphium.core.MorphiumException;
import com.morphium.parser.ast.*;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(Lexer lexer) {
        this.tokens = lexer.scanTokens();
    }

    public Expression parse() {
        return parseProgram();
    }

    private Expression parseProgram() {
        BlockExpr block = new BlockExpr();
        
        while (!isAtEnd()) {
            if (match(Token.Type.IMPORT)) {
                Expression importExpr = parseImportStatement();
                if (importExpr != null) {
                    block.addExpression(importExpr);
                }
            } else if (match(Token.Type.EXPORT)) {
                Expression exportExpr = parseExportStatement();
                if (exportExpr != null) {
                    block.addExpression(exportExpr);
                }
            } else if (match(Token.Type.FUNCTION)) {
                Expression funcExpr = parseFunctionDefinition();
                if (funcExpr != null) {
                    block.addExpression(funcExpr);
                }
            } else if (match(Token.Type.LET)) {
                Expression letExpr = parseLetDeclaration(block);
                if (letExpr != null) {
                    block.addExpression(letExpr);
                }
            } else if (match(Token.Type.GLOBAL)) {
                Expression globalExpr = parseGlobalDeclaration();
                if (globalExpr != null) {
                    block.addExpression(globalExpr);
                }
            } else {
                block.addExpression(parseExpression());
                if (!isAtEnd()) {
                    matchOptional(Token.Type.SEMICOLON);
                }
            }
        }
        
        List<Expression> exprs = block.getExpressions();
        if (exprs.isEmpty()) {
            return new LiteralExpr(null);
        }
        if (exprs.size() == 1) {
            return exprs.get(0);
        }
        return block;
    }

    private Expression parseLetDeclaration(BlockExpr parentBlock) {
        Token name = consume(Token.Type.IDENTIFIER, "Expected variable name");
        consume(Token.Type.EQ, "Expected '=' after variable name");
        Expression value = parseExpression();
        matchOptional(Token.Type.SEMICOLON);
        
        // Store let binding for later expressions in the block
        return new LetStatement(name.getLexeme(), value, new LiteralExpr(null));
    }

    private Expression parseGlobalDeclaration() {
        Token name = consume(Token.Type.IDENTIFIER, "Expected variable name");
        consume(Token.Type.EQ, "Expected '=' after variable name");
        Expression value = parseExpression();
        matchOptional(Token.Type.SEMICOLON);
        
        return new GlobalVarStatement(name.getLexeme(), value);
    }

    private Expression parseFunctionDefinition() {
        Token name = consume(Token.Type.IDENTIFIER, "Expected function name");
        consume(Token.Type.LPAREN, "Expected '(' after function name");
        
        List<String> parameters = new java.util.ArrayList<>();
        if (!check(Token.Type.RPAREN)) {
            do {
                Token param = consume(Token.Type.IDENTIFIER, "Expected parameter name");
                parameters.add(param.getLexeme());
            } while (match(Token.Type.COMMA));
        }
        
        consume(Token.Type.RPAREN, "Expected ')' after parameters");
        
        Expression body;
        if (match(Token.Type.LBRACE)) {
            body = parseFunctionBody();
            consume(Token.Type.RBRACE, "Expected '}' after function body");
        } else {
            consume(Token.Type.ARROW, "Expected '=>' or '{' after parameters");
            body = parseExpression();
        }
        
        matchOptional(Token.Type.SEMICOLON);
        
        return new FunctionDefExpr(name.getLexeme(), parameters, body);
    }

    private Expression parseFunctionBody() {
        BlockExpr block = new BlockExpr();
        
        while (!check(Token.Type.RBRACE) && !isAtEnd()) {
            if (match(Token.Type.RETURN)) {
                Expression returnExpr = parseExpression();
                matchOptional(Token.Type.SEMICOLON);
                block.addExpression(returnExpr);
                break;
            } else if (match(Token.Type.LET)) {
                Expression letExpr = parseLetDeclaration(block);
                if (letExpr != null) {
                    block.addExpression(letExpr);
                }
            } else {
                block.addExpression(parseExpression());
                matchOptional(Token.Type.SEMICOLON);
            }
        }
        
        List<Expression> exprs = block.getExpressions();
        if (exprs.isEmpty()) {
            return new LiteralExpr(null);
        }
        if (exprs.size() == 1) {
            return exprs.get(0);
        }
        return block;
    }

    private Expression parseImportStatement() {
        Token pathToken = consume(Token.Type.STRING, "Expected module path");
        String modulePath = (String) pathToken.getLiteral();
        
        String alias = null;
        List<String> specificImports = null;
        
        if (match(Token.Type.AS)) {
            Token aliasToken = consume(Token.Type.IDENTIFIER, "Expected alias name");
            alias = aliasToken.getLexeme();
        }
        
        matchOptional(Token.Type.SEMICOLON);
        
        return new ImportStatement(modulePath, alias, specificImports);
    }

    private Expression parseExportStatement() {
        Token name = consume(Token.Type.IDENTIFIER, "Expected export name");
        consume(Token.Type.EQ, "Expected '=' after export name");
        Expression value = parseExpression();
        matchOptional(Token.Type.SEMICOLON);
        
        return new ExportStatement(name.getLexeme(), value);
    }

    private Expression parseExpression() {
        return parseTernary();
    }

    private Expression parseTernary() {
        Expression expr = parseNullCoalesce();
        
        if (match(Token.Type.QUESTION)) {
            Expression thenExpr = parseExpression();
            consume(Token.Type.COLON, "Expected ':' in ternary expression");
            Expression elseExpr = parseExpression();
            return new TernaryExpr(expr, thenExpr, elseExpr);
        }
        
        return expr;
    }

    private Expression parseNullCoalesce() {
        Expression expr = parseLogicalOr();
        
        while (match(Token.Type.QUESTION_QUESTION)) {
            Expression right = parseLogicalOr();
            expr = new BinaryExpr(expr, BinaryExpr.Operator.NULL_COALESCE, right);
        }
        
        return expr;
    }

    private Expression parseLogicalOr() {
        Expression expr = parseLogicalAnd();
        
        while (match(Token.Type.PIPE_PIPE)) {
            Expression right = parseLogicalAnd();
            expr = new BinaryExpr(expr, BinaryExpr.Operator.OR, right);
        }
        
        return expr;
    }

    private Expression parseLogicalAnd() {
        Expression expr = parseEquality();
        
        while (match(Token.Type.AMP_AMP)) {
            Expression right = parseEquality();
            expr = new BinaryExpr(expr, BinaryExpr.Operator.AND, right);
        }
        
        return expr;
    }

    private Expression parseEquality() {
        Expression expr = parseComparison();
        
        while (true) {
            if (match(Token.Type.EQ_EQ_EQ)) {
                Expression right = parseComparison();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.EQ_STRICT, right);
            } else if (match(Token.Type.EQ_EQ)) {
                Expression right = parseComparison();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.EQ, right);
            } else if (match(Token.Type.BANG_EQ_EQ)) {
                Expression right = parseComparison();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.NE_STRICT, right);
            } else if (match(Token.Type.BANG_EQ)) {
                Expression right = parseComparison();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.NE, right);
            } else {
                break;
            }
        }
        
        return expr;
    }

    private Expression parseComparison() {
        Expression expr = parseAddition();
        
        while (true) {
            if (match(Token.Type.LT)) {
                Expression right = parseAddition();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.LT, right);
            } else if (match(Token.Type.LT_EQ)) {
                Expression right = parseAddition();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.LE, right);
            } else if (match(Token.Type.GT)) {
                Expression right = parseAddition();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.GT, right);
            } else if (match(Token.Type.GT_EQ)) {
                Expression right = parseAddition();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.GE, right);
            } else {
                break;
            }
        }
        
        return expr;
    }

    private Expression parseAddition() {
        Expression expr = parseMultiplication();
        
        while (true) {
            if (match(Token.Type.PLUS)) {
                Expression right = parseMultiplication();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.ADD, right);
            } else if (match(Token.Type.MINUS)) {
                Expression right = parseMultiplication();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.SUBTRACT, right);
            } else {
                break;
            }
        }
        
        return expr;
    }

    private Expression parseMultiplication() {
        Expression expr = parseUnary();
        
        while (true) {
            if (match(Token.Type.STAR)) {
                Expression right = parseUnary();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.MULTIPLY, right);
            } else if (match(Token.Type.SLASH)) {
                Expression right = parseUnary();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.DIVIDE, right);
            } else if (match(Token.Type.PERCENT)) {
                Expression right = parseUnary();
                expr = new BinaryExpr(expr, BinaryExpr.Operator.MODULO, right);
            } else {
                break;
            }
        }
        
        return expr;
    }

    private Expression parseUnary() {
        if (match(Token.Type.BANG)) {
            Expression expr = parseUnary();
            return new UnaryExpr(UnaryExpr.Operator.NOT, expr);
        }
        
        if (match(Token.Type.MINUS)) {
            Expression expr = parseUnary();
            return new UnaryExpr(UnaryExpr.Operator.MINUS, expr);
        }
        
        return parsePostfix();
    }

    private Expression parsePostfix() {
        Expression expr = parsePrimary();
        
        while (true) {
            if (match(Token.Type.DOT)) {
                Token property = consume(Token.Type.IDENTIFIER, "Expected property name after '.'");
                expr = new MemberAccessExpr(expr, new IdentifierExpr(property.getLexeme()), false, false);
            } else if (match(Token.Type.QUESTION_DOT)) {
                Token property = consume(Token.Type.IDENTIFIER, "Expected property name after '?.'");
                expr = new MemberAccessExpr(expr, new IdentifierExpr(property.getLexeme()), true, false);
            } else if (match(Token.Type.LBRACKET)) {
                Expression index = parseExpression();
                consume(Token.Type.RBRACKET, "Expected ']' after index");
                expr = new MemberAccessExpr(expr, index, false, true);
            } else if (match(Token.Type.LPAREN)) {
                expr = parseCallExpression(expr);
            } else {
                break;
            }
        }
        
        return expr;
    }

    private Expression parseCallExpression(Expression callee) {
        CallExpr call = new CallExpr(callee);
        
        if (!check(Token.Type.RPAREN)) {
            do {
                call.addArgument(parseExpression());
            } while (match(Token.Type.COMMA));
        }
        
        consume(Token.Type.RPAREN, "Expected ')' after arguments");
        return call;
    }

    private Expression parsePrimary() {
        if (match(Token.Type.TRUE)) {
            return new LiteralExpr(true);
        }
        if (match(Token.Type.FALSE)) {
            return new LiteralExpr(false);
        }
        if (match(Token.Type.NULL)) {
            return new LiteralExpr(null);
        }
        
        if (match(Token.Type.NUMBER)) {
            return new LiteralExpr(previous().getLiteral());
        }
        
        if (match(Token.Type.STRING)) {
            return new LiteralExpr(previous().getLiteral());
        }
        
        if (match(Token.Type.IDENTIFIER)) {
            return new IdentifierExpr(previous().getLexeme());
        }
        
        if (match(Token.Type.LPAREN)) {
            Expression expr = parseExpression();
            consume(Token.Type.RPAREN, "Expected ')' after expression");
            return expr;
        }
        
        if (match(Token.Type.LBRACKET)) {
            return parseArrayLiteral();
        }
        
        if (match(Token.Type.LBRACE)) {
            return parseObjectLiteral();
        }
        
        throw error(peek(), "Expected expression");
    }

    private Expression parseArrayLiteral() {
        ArrayExpr array = new ArrayExpr();
        
        if (!check(Token.Type.RBRACKET)) {
            do {
                array.addElement(parseExpression());
            } while (match(Token.Type.COMMA));
        }
        
        consume(Token.Type.RBRACKET, "Expected ']' after array elements");
        return array;
    }

    private Expression parseObjectLiteral() {
        ObjectExpr object = new ObjectExpr();
        
        if (!check(Token.Type.RBRACE)) {
            do {
                if (match(Token.Type.LBRACKET)) {
                    Expression keyExpr = parseExpression();
                    consume(Token.Type.RBRACKET, "Expected ']' after computed property");
                    consume(Token.Type.COLON, "Expected ':' after property key");
                    Expression valueExpr = parseExpression();
                    object.addComputedProperty(keyExpr, valueExpr);
                } else {
                    String key;
                    if (check(Token.Type.STRING)) {
                        advance();
                        key = (String) previous().getLiteral();
                    } else {
                        Token keyToken = consume(Token.Type.IDENTIFIER, "Expected property name");
                        key = keyToken.getLexeme();
                    }
                    consume(Token.Type.COLON, "Expected ':' after property key");
                    Expression value = parseExpression();
                    object.addProperty(key, value);
                }
            } while (match(Token.Type.COMMA));
        }
        
        consume(Token.Type.RBRACE, "Expected '}' after object properties");
        return object;
    }

    private boolean match(Token.Type... types) {
        for (Token.Type type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean matchOptional(Token.Type type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private Token consume(Token.Type type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private boolean check(Token.Type type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == Token.Type.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private MorphiumException error(Token token, String message) {
        return new MorphiumException(message, token.getSourcePath(), token.getLine(), token.getColumn());
    }
}
