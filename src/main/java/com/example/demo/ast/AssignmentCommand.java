package com.example.demo.ast;

import com.example.demo.game.Context;

public record AssignmentCommand(String variable, Expression expression) implements Command{

    @Override
    public void eval(Context context){
        int val = expression.eval(context);
        context.variables().put(variable,val);
    }
}
