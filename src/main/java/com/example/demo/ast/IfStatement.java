package com.example.demo.ast;

import com.example.demo.game.Context;

public record IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) implements Statement{
    @Override
    public void eval(Context context){
        if(condition.eval(context) > 0){ // greater than 0
            thenStatement.eval(context);
        }else{ // less than equal 0
            elseStatement.eval(context);
        }
    }
}
