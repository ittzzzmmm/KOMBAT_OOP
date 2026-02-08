package com.example.demo.ast;

import com.example.demo.game.Context;

import java.util.Map;

public record IntLit(int val) implements Expression{
    @Override
    public int eval(Context context){
        return val;
    }
}
