package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ClickControllerWS {
    private final ClickCounter counter = new ClickCounter();

    @MessageMapping("/click")
    @SendTo("/topic/count")
    public int click() {
        counter.increment();
        return counter.getCount();
    }
    @MessageMapping("/current")
    @SendTo("/topic/count")
    public int current() {
        return counter.getCount();
    }
}
