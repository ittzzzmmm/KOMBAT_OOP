package com.example.demo.ast;

import com.example.demo.game.Context;

public interface Statement extends Node{
    void eval(Context context);
}
