package com.example.chtecnical;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @RequestMapping("/helloWorld")
    private String helloWorld() {
        return "Hello world!";
    }

}
