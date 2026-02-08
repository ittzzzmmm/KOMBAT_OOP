package com.example.demo.ast;

import com.example.demo.game.Context;

import java.util.Map;

public record Variable(String varName) implements Expression {
    @Override
    public int eval(Context context){
        Integer value = context.variables().get(varName);
        if(value==null){
            throw new RuntimeException("Undefined variable : "+varName);
        }
        return value;

    }
}
