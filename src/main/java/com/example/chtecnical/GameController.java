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
    public String getReport() throws IOException {
        Map<String, String> report = new HashMap<>();

        String highestRankedGame = gameService.findHighestRatedGame();
        String userWithMostComments = gameService.findUserWithMostComments();
        Map<String,String> averageLikesPerGame = gameService.findAverageLikesPerGame();

        report.put("highest_rated_game",highestRankedGame);
        report.put("user_with_most_comments", userWithMostComments);
        report.putAll(averageLikesPerGame);

        String jsonReport = gameService.convertReportToJson(report);

        return jsonReport;
    }
}

//TODO: Step 1 - Get averages per game - total likes / comments
//TODO: Step 2 - Convert report map into JSON
//TODO: Step 3 - Profit???