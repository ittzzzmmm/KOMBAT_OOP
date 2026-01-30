package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5174") // ให้ React เรียกได้
public class ClickController {

    private final ClickCounter counter = new ClickCounter();

    @PostMapping("/click")
    public int click() {
        counter.increment();
        return counter.getCount();   // ส่งเลขกลับไปให้ React
    }

    @GetMapping("/count")
    public int getCount() {
        return counter.getCount();   // เอาไว้โหลดค่าตอนเปิดหน้า
    }
}
