package com.example.demo.ast;

import com.example.demo.game.Context;

public record AttackCommand(Direction dir, Expression power ) implements Command
{
    @Override
    public void eval(Context context){
        int damage = power.eval(context);
        context.gameState().shoot(context.minion(), dir, damage);
    }
}
