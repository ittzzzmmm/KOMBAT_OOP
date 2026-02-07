package com.example.demo.ast;

import com.example.demo.game.Context;

public record AssignmentStatement(String variable, Expression expression) implements Command{

    @Override
    public void eval(Context context){
        int val = expression.eval(context.variables());
        context.variables().put(variable,val);
    }
}
