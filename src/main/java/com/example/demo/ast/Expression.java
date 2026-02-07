package com.example.demo.ast;

import com.example.demo.game.Context;

public interface Expression extends Node{
    int eval(Context context);
}
