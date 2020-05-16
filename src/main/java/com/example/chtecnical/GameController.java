package com.example.chtecnical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Arrays;

@RestController
public class GameController {

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> getGameInfo(@PathVariable(name = "id") Integer id) throws Exception {
        Reader reader = new FileReader(new File("src/main/resources/games.json"));
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


    @RequestMapping("/games")
    public GameDTO[] JsonMapper() throws IOException {

        Reader data = new FileReader(new File("src/main/resources/games.json"));

        ObjectMapper mapper = new ObjectMapper();
        GameDTO[] jsonObj = mapper.readValue(data, GameDTO[].class);

        for (GameDTO itr : jsonObj) {
            System.out.println("Val of id is: " + itr.getId());
            System.out.println("Val of title is: " + itr.getTitle());
            System.out.println("Val of description is: " + itr.getDescription());
            System.out.println("Val of by is: " + itr.getBy());
            System.out.println("Val of platform is: " + Arrays.toString(itr.getPlatform()));
            System.out.println("Val of age rating is: " + itr.getAge_rating());
            System.out.println("Val of likes is: " + itr.getLikes());
            System.out.println("Val of comments is: " + itr.getComments() + "\n");

        }
         return jsonObj;
    }

    @RequestMapping(value = "/games/report", method = RequestMethod.GET)
    public String getReport(){

        return "test";
    }

}
