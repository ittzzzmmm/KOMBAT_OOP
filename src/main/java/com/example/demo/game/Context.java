package com.example.demo.game;

import com.example.demo.ast.Direction;

import java.util.HashMap;
import java.util.Map;

public record Context(GameState gameState,Minion minion,Map<String,Integer> variables) {
    public Context(GameState gameState,Minion minion){
            this(gameState,minion,new HashMap<>());
    }

    public int scan(String type){
        return 0;
    }

    public int scan(String type, Direction dir){
        return 0;
    }
}
