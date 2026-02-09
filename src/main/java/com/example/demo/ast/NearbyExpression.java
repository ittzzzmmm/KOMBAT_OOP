package com.example.demo.ast;

import com.example.demo.game.Context;

public record NearbyExpression(Direction dir) implements Expression{
    @Override
    public int eval(Context context){
        return context.scan("NEARBY",dir);
    }
}
