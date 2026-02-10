package com.example.demo.parser;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.demo.ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Parser {

    private final StatementTokenizer tk;

    private static final Set<String> RESERVED_WORDS = Set.of(
            "up","upleft","upright","down","downleft","downright",
            "if","then","else","while",
            "ally","opponent","nearby",
            "move","shoot","done"
    );

    private static final Set<String> ENTITY_VARIABLES = Set.of(
            "hp","owner","context","pos"
    );

    public Parser(StatementTokenizer tk) {
        this.tk = tk;
    }

    public Statement parseStrategy() throws SyntaxError{
        List<Statement> statements = new ArrayList<>();
        while(tk.hasNextToken()){
            statements.add(parseStatement());
        }
        return new BlockStatement(statements);
    }

    private Statement parseStatement() throws SyntaxError{

        if (tk.peek("{")) return parseBlock();
        if (tk.peek("if")) return parseIf();
        if (tk.peek("while")) return parseWhile();

        return parseCommand();
    }

    private Statement parseBlock() throws SyntaxError{
        tk.consume("{");

        List<Statement> statements = new ArrayList<>();

        while(tk.hasNextToken() && !tk.peek("}")){
            statements.add(parseStatement());
        }
        if (!tk.peek("}"))
            throw new SyntaxError(" '}' expected");
        tk.consume("}");
        return new BlockStatement(statements);

    }

    private Statement parseIf() throws SyntaxError{
        tk.consume("if");
        tk.consume("(");
        Expression condition = parseExpression();
        tk.consume(")");

        tk.consume("then");
        Statement thenStatement = parseStatement();

        tk.consume("else");
        Statement elseStatement = parseStatement();

        return new IfStatement(condition,thenStatement,elseStatement);
    }

    private Statement parseWhile() throws SyntaxError{
        tk.consume("while");
        tk.consume("(");
        Expression condition = parseExpression();
        tk.consume(")");
        Statement bodyStatement = parseStatement();
        return new WhileStatement(condition,bodyStatement);
    }

    private Statement parseCommand() throws SyntaxError{

        if (tk.peek("done") || tk.peek("move") || tk.peek("shoot")){
            return parseAction();
        }

        return parseAssignment();
    }

    private Statement parseAction() throws SyntaxError{

        if (tk.peek("done")) {
            tk.consume();
            return new DoneCommand();
        }else if (tk.peek("move")) {
            tk.consume();
            return parseMove();
        }else{
            tk.consume();
            return parseAttack();
        }
    }
    private Statement parseMove(){
        Direction direction = parseDirection();
        return new MoveCommand(direction);
    }

    private Direction parseDirection() throws SyntaxError {
        String token = tk.consume();
        try {
            return Direction.valueOf(token.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new SyntaxError("Invalid direction: " + token);
        }
    }

    private Statement parseAttack() throws SyntaxError{
        Direction direction = parseDirection();
        Expression expression = parseExpression();
        return new AttackCommand(direction,expression);
    }

    private Statement parseAssignment() throws SyntaxError{
        String varName = tk.consume();
        validateIdentifier(varName);
        tk.consume("=");
        Expression expression = parseExpression();
        return new AssignmentCommand(varName,expression);
    }

    private Expression parseExpression() throws SyntaxError{
        Expression current = parseTerm();
        while(tk.peek("+")||tk.peek("-")){
            if(tk.peek().equals("+")){
                tk.consume();
                current = new BinaryArithExpression(current,"+",parseTerm());
            }else{
                tk.consume();
                current = new BinaryArithExpression(current,"-",parseTerm());
            }
        }
        return current;
    }

    private Expression parseTerm() throws SyntaxError{
        Expression current = parseFactor();
        while(tk.peek("*")||tk.peek("/")||tk.peek("%")){
            if(tk.peek().equals("*")){
                tk.consume();
                current = new BinaryArithExpression(current,"*",parseFactor());
            }else if(tk.peek().equals("/")){
                tk.consume();
                current = new BinaryArithExpression(current,"/",parseFactor());
            }else{
                tk.consume();
                current = new BinaryArithExpression(current,"%",parseFactor());
            }
        }
        return current;
    }

    private Expression parseFactor() throws SyntaxError{
        Expression base = parsePower();
        if(tk.peek("^")){
            tk.consume();
            Expression exponent = parseFactor();
            return new BinaryArithExpression(base,"^",exponent);
        }
        return base;
    }

    private Expression parsePower() throws SyntaxError{
        String token =  tk.peek();
        if(Character.isDigit(token.charAt(0))){
            tk.consume();
            return new IntLit(Integer.parseInt(token));
        }else if(token.equals("(")){
            tk.consume("(");
            Expression expression = parseExpression();
            tk.consume(")");
            return expression;
        } else if(token.equals("ally")||token.equals("opponent")||token.equals("nearby")){
            return parseInfo();
        }else{
            tk.consume();
            return new Variable(token);
        }
    }

    private Expression parseInfo() throws SyntaxError{
        String token = tk.peek();
        if(token.equals("ally")){
            tk.consume();
            return new AllyExpression();
        }else if(token.equals("opponent")){
            tk.consume();
            return new OpponentExpression();
        }else{
            tk.consume();
            Direction dir = parseDirection();
            return new NearbyExpression(dir);
        }
    }


    private void validateIdentifier(String varName) throws SyntaxError{
        if(RESERVED_WORDS.contains(varName)){
            throw new SyntaxError("Word is reserved : "+varName);
        }
        if(ENTITY_VARIABLES.contains(varName)){
            throw new SyntaxError("Word is entity variable : "+varName);
        }
        if (!Character.isAlphabetic(varName.charAt(0))) {
            throw new SyntaxError("Invalid identifier: " + varName);
        }
    }
}
