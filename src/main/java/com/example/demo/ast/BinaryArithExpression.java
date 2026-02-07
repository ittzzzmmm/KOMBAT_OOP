package com.example.demo.ast;

import java.util.Map;

import static java.lang.Math.pow;

public record BinaryArithExpression(Expression left, String operator, Expression right) implements Expression
{
    @Override
    public int eval(Map<String,Integer> binding){
        int lv = left.eval(binding);
        int rv = right.eval(binding);
        if (operator.equals("+")) return lv + rv;
        if (operator.equals("-")) return lv - rv;
        if (operator.equals("*")) return lv * rv;
        if (operator.equals("^")) return (int) pow(lv,rv);
        if (operator.equals("%")){
            if(rv==0)throw new ArithmeticException("Modula by zero");
            return lv % rv;
        }
        if (operator.equals("/")){
            if(rv==0)throw new ArithmeticException("Divided by zero");
            return lv / rv;
        }
        throw new RuntimeException("unknown operator: " + operator);
    }
}
