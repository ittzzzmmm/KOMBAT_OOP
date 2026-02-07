package com.example.demo.ast;

import java.util.Map;

public record IntLit(int val) implements Expression{
    @Override
    public int eval(Map<String,Integer> binding){
        return val;
    }
}
