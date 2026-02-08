package com.example.demo.parser;

public interface Tokenizer {
    boolean hasNextToken();
    String peek();
    String consume();
}
