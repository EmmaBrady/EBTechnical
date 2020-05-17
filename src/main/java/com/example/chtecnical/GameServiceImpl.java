package com.example.chtecnical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService{

    Reader data = new FileReader(new File("src/main/resources/games.json"));
    Map<String, Integer> gameLikes = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    Game[] jsonObj = mapper.readValue(data, Game[].class);

    public GameServiceImpl() throws IOException {
    }

    @Override
    public String findHighestRatedGame() throws IOException {
        int sumOfLikes = 0;

        for (Game game : jsonObj) {
            List<Comment> gameComments = game.getComments();
            int actualGameLikes = game.getLikes();
            sumOfLikes = sumOfLikes + actualGameLikes;

            for (Comment comment : gameComments) {
                int userLikes = comment.getLike();
                sumOfLikes = sumOfLikes + userLikes;

            }
            gameLikes.put(game.getTitle() + " ",sumOfLikes);
            sumOfLikes = 0;
        }

        Map.Entry<String, Integer> highestRatedGame = null;
        for (Map.Entry<String, Integer> entry : gameLikes.entrySet())
        {
            if (highestRatedGame == null || entry.getValue().compareTo(highestRatedGame.getValue()) > 0)
            {
                highestRatedGame = entry;
            }
        }

        return highestRatedGame.getKey();
    }

    @Override
    public String findUserWithMostComments() throws FileNotFoundException {

        ArrayList<String> usersCommented = new ArrayList<>();

        for (Game game : jsonObj) {
            List<Comment> gameComments = game.getComments();

            for (Comment comment : gameComments) {
                String user = comment.getUser();
                usersCommented.add(user);
            }
        }

        Map.Entry<String, Long> mostCommentedUser
                = usersCommented.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        return mostCommentedUser.getKey();
    }

    @Override
    public Map<String, String> findAverageLikesPerGame() throws FileNotFoundException {
        int sumOfLikes = 0;
        int commentsPerGame = 0;

        Map<String, String> averageLikesPerGame = new HashMap<>();

        for (Game game : jsonObj) {
            List<Comment> gameComments = game.getComments();
            commentsPerGame = gameComments.size();
            int actualGameLikes = game.getLikes();
            sumOfLikes = sumOfLikes + actualGameLikes;

            for (Comment comment : gameComments) {
                int userLikes = comment.getLike();
                sumOfLikes = sumOfLikes + userLikes;

            }
            gameLikes.put(game.getTitle() + " ",sumOfLikes);
            sumOfLikes = 0;

            for(Map.Entry<String,Integer> entry : gameLikes.entrySet()) {
                int avgLikesPerGame = entry.getValue() / commentsPerGame;
                averageLikesPerGame.put(entry.getKey(), String.valueOf(avgLikesPerGame));
            }
        }
        return averageLikesPerGame;
    }

    @Override
    public String convertReportToJson(Map<String, Object> report) {

        ObjectMapper mapper = new ObjectMapper();
        String jsonArray = null;
        try {
            jsonArray = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public List<Map<String, String>> addLabelAvgLikesPerGame(Map<String, String> averageLikesPerGame) {

        return averageLikesPerGame.entrySet().stream()
                .map( entry ->
                        Map.ofEntries(
                                Map.entry("title", entry.getKey()),
                                Map.entry("average_likes", entry.getValue())
                        )).collect(Collectors.toList());
    }

    @Override
    public String findGameBasedOffId(Integer id) {
        for(Game game: jsonObj) {
            if(game.getId() == id) {
                String selectedGame = null;
                ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                try {
                    selectedGame = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return selectedGame;
            }
        }
        return null;
    }
}
