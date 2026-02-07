package com.example.demo.ast;

import com.example.demo.game.Context;

import java.util.Map;

import static java.lang.Math.pow;

public record BinaryArithExpression(Expression left, String operator, Expression right) implements Expression
{
    @Override
    public int eval(Context context){
        int lv = left.eval(context);
        int rv = right.eval(context);
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
