package com.example.demo.ast;

import com.example.demo.game.Context;

public record WhileStatement(Expression condition, Statement trueStatement)implements Statement {
    @Override
    public void eval(Context context){
        if(condition.eval(context.variables()) > 0){
            trueStatement.eval(context);
        }
    }
}
