package com.example.demo.ast;

import com.example.demo.game.Context;

public class OpponentExpression implements Expression {
    @Override
    public int eval(Context context) {
        return context.scan("OPPONENT");
    }
}
