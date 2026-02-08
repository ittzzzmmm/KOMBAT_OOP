package com.example.demo.parser;

import java.util.NoSuchElementException;

public class StatementTokenizer implements Tokenizer{
    private final String src;
    private String next;
    private int pos;

    public StatementTokenizer(String src) {
        this.src = src;
        pos=0;
        computeNext();
    }


    @Override
    public boolean hasNextToken() {
        return next!=null;
    }

    @Override
    public String peek() {
        checkNextToken();
        return next;
    }

    @Override
    public String consume() {
        checkNextToken();
        String result = next;
        computeNext();
        return result;
    }

    public int getSrcLength(){
        return src.length();
    }

    public void checkNextToken(){
        if(!hasNextToken())throw new NoSuchElementException("no token left");
    }

    private void computeNext(){
        int len = getSrcLength();
        StringBuilder s = new StringBuilder();
        while(pos < len && Character.isWhitespace(src.charAt(pos))) pos++; // skipping space and \n

        if(pos==len){
            next=null;
            return;
        }

        char c = src.charAt(pos);

        if(Character.isDigit(c) || Character.isAlphabetic(c))// group up words, numbers
        {
            s.append(c);
            for(pos++;pos<len
                    &&( Character.isDigit(src.charAt(pos))
                    || Character.isAlphabetic(src.charAt(pos)));pos++)
            {
                s.append(src.charAt(pos));

            }
        }
        else if(isOperator(c)) // detect operators
        {
            s.append(c);
            pos++;
        }
        else if(isParentheses(c)) // detect parentheses
        {
            s.append(c);
            pos++;
        }

        next=s.toString();

    }

    private boolean isOperator(char c){
        return "+-*/%^=".indexOf(c) >= 0;
    }

    private boolean isParentheses(char c) {
        return "{}()".indexOf(c) >= 0;
    }

    public boolean peek(String s){
        if(!hasNextToken())return false;
        return peek().equals(s);
    }

    public void consume(String s) throws SyntaxError{
        if(peek(s)) consume();
        else throw new SyntaxError("'"+ s +"' Expected");
    }
}
