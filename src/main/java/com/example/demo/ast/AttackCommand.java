package com.example.demo.ast;

import com.example.demo.game.Context;

import java.util.Map;

public record AttackCommand(Direction dir, Expression power ) implements Command
{
    @Override
    public void eval(Context context){
        int damage = power.eval(context.variables());
        context.gameState().shoot(context.minion(), dir, damage);
    }
}
