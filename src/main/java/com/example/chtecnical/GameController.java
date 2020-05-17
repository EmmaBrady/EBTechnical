package com.example.chtecnical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> getGameInfo(@PathVariable(name = "id") Integer id) throws Exception {
        Reader data = new FileReader(new File("src/main/resources/games.json"));
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readValue(data, JsonNode.class);
        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode element = jsonNode.get(i);
            if (element.get("id").asInt() == id) {
                return new ResponseEntity<>(element, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/games/report", method = RequestMethod.GET)
    public Map<String,String> getReport() throws IOException {
        Map<String, String> report = new HashMap<>();

        String highestRankedGame = gameService.findHighestRatedGame();
        String userWithMostComments = gameService.findUserWithMostComments();

        report.put("highest_ranked_game",highestRankedGame);
        report.put("user_with_most_comments", userWithMostComments);
        report.put("averages_likes_per_game", " ");

        return report;
    }
}
