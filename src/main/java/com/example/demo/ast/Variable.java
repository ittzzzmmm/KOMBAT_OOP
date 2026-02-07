package com.example.demo.ast;

import java.util.Map;

public record Variable(String varName) implements Expression {
    @Override
    public int eval(Map<String,Integer> binding){
        if(binding.containsKey(varName)){
            return binding.get(varName);
        }
        else{
            throw new RuntimeException("Undefined variable : "+ varName);
        }
    }
}
