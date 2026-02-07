package com.example.demo.ast;

import com.example.demo.game.Context;

public record MoveCommand(Direction dir) implements Command{

    @Override
    public void eval(Context context) {
        context.gameState().move(context.minion(),dir);
    }
}
