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
    public String getGameBasedOffId(@PathVariable(name = "id") Integer id) throws Exception {

        return gameService.findGameBasedOffId(id);
    }

    @RequestMapping(value = "/games/report", method = RequestMethod.GET)
    public String getReport() throws IOException {

        Map<String, Object> report = new HashMap<>();

        String highestRankedGame = gameService.findHighestRatedGame();
        String userWithMostComments = gameService.findUserWithMostComments();
        Map<String, String> averageLikesPerGame = gameService.findAverageLikesPerGame();
        List<Map<String,String>> labelAvgLikesPerGame = gameService.addLabelAvgLikesPerGame(averageLikesPerGame);

        report.put("highest_rated_game",highestRankedGame);
        report.put("user_with_most_comments", userWithMostComments);
        report.put("average_likes_per_game",labelAvgLikesPerGame);

        String jsonReport = gameService.convertReportToJson(report);

        return jsonReport;
    }
}
