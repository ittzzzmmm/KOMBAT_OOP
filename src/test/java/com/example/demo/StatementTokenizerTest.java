package com.example.demo;

import com.example.demo.parser.StatementTokenizer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class StatementTokenizerTest {
    @Test
    void testA(){

        StatementTokenizer tkz = new StatementTokenizer("Budget = 10");
        List<String> tokens = new ArrayList<>();
        while(tkz.hasNextToken()){
            tokens.add(tkz.peek());
            tkz.consume();
        }
        assertEquals(List.of("Budget","=","10"),tokens);
    }
    @Test
    void testB(){
        StatementTokenizer tkz = new StatementTokenizer("if (opponentLoc % 10 - 5) then move downleft");
        List<String> tokens = new ArrayList<>();
        while(tkz.hasNextToken()){
            tokens.add(tkz.peek());
            tkz.consume();
        }
        assertEquals(List.of("if","(","opponentLoc","%","10","-","5",")","then","move","downleft"),tokens);
    }
    @Test
    void testC(){
        StatementTokenizer tkz = new StatementTokenizer("t = t + 1");
        List<String> tokens = new ArrayList<>();
        while(tkz.hasNextToken()){
            tokens.add(tkz.peek());
            tkz.consume();
        }
        assertEquals(List.of("t","=","t","+","1"),tokens);
    }
    @Test
    void testD(){
        StatementTokenizer tkz = new StatementTokenizer("");
        List<String> tokens = new ArrayList<>();
        while(tkz.hasNextToken()){
            tokens.add(tkz.peek());
            tkz.consume();
        }
        assertEquals(List.of(),tokens);
    }
    @Test
    void testE(){
        StatementTokenizer tkz = new StatementTokenizer("else if (opponentLoc % 10 - 3) then move downright");
        List<String> tokens = new ArrayList<>();
        while(tkz.hasNextToken()){
            tokens.add(tkz.peek());
            tkz.consume();
        }
        assertEquals(List.of("else","if","(","opponentLoc","%","10","-","3",")","then","move","downright"),tokens);
    }
}
