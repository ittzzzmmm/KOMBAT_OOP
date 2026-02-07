package com.example.demo.ast;

import com.example.demo.game.Context;

public record WhileStatement(Expression condition, Statement bodyStatement)implements Statement {
    @Override
    public void eval(Context context){
        if(condition.eval(context.variables()) > 0){
            bodyStatement.eval(context);
        }
    }
}
