package com.example.chtecnical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;

@RestController
public class GameController {

    @RequestMapping("/helloWorld")
    private String helloWorld() {
        return "Hello world!";
    }

    @RequestMapping("/jsonTest")
    public String getGameFile() throws IOException {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:games.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String content = new String(Files.readAllBytes(file.toPath()));

        return content;
    }

    @RequestMapping("/games")
    public GameDTO JsonMapper() throws IOException {

        InputStream inJson = GameDTO.class.getResourceAsStream("/games.json");
        GameDTO gameData = new ObjectMapper().readValue(inJson, GameDTO.class);

        return gameData;

    }

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> getGameInfo(@PathVariable(name = "id") Integer id) throws Exception {
        Reader reader = new FileReader(new File("games.json"));
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readValue(reader, JsonNode.class);
        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode element = jsonNode.get(i);
            if (element.get("id").asInt() == id) {
                return new ResponseEntity<>(element, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
