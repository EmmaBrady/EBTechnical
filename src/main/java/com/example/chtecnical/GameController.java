package com.example.chtecnical;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class GameController {

    @RequestMapping("/helloWorld")
    private String helloWorld() {
        return "Hello world!";
    }

    @RequestMapping("/games")
    private String getGameFile() throws IOException {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:games.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));

        return content;
    }
}
