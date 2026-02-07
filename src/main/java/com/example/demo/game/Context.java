package com.example.demo.game;

import java.util.HashMap;
import java.util.Map;

public record Context(GameState gameState,Minion minion,Map<String,Integer> variables) {
    public Context(GameState gameState,Minion minion){
            this(gameState,minion,new HashMap<>());
    }
}
