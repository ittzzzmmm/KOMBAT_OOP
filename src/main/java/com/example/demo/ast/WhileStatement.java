package com.example.demo.ast;

import com.example.demo.game.Context;

public record WhileStatement(Expression condition, Statement bodyStatement)implements Statement {
    private static final int MAX_LOOP = 100;
    @Override
    public void eval(Context context){
        for(int i=0;condition.eval(context) > 0 && i < MAX_LOOP;i++){
            bodyStatement.eval(context);
        }
    }
}
