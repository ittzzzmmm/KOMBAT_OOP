package com.example.demo.ast;

import com.example.demo.game.Context;

import java.util.List;

public record BlockStatement(List<Statement> statements) implements Statement{
    @Override
    public void eval(Context context){
        for(Statement s : statements){
            s.eval(context);
        }
    }
}
