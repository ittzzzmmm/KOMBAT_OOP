package com.example.demo.ast;

import java.util.Map;

public interface Expression extends Node{
    int eval(Map<String, Integer> bindings);
}
