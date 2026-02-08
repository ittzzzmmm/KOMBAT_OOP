package com.example.demo.ast;

public enum Direction {
    UP,
    DOWN,
    UPLEFT,
    UPRIGHT,
    DOWNLEFT,
    DOWNRIGHT;
    public static Direction fromString(String s){
        return switch (s.toLowerCase()){
            case "up" -> UP;
            case "down" -> DOWN;
            case "upleft" -> UPLEFT;
            case "upright" -> UPRIGHT;
            case "downleft" -> DOWNLEFT;
            case "downright" -> DOWNRIGHT;
            default -> throw new IllegalArgumentException("Invalid direction: " + s);
        };
    }
}
