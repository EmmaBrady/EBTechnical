package com.example.chtecnical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        String highestRatedGameValue = highestRatedGame.getKey();
        return highestRatedGameValue;
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
        String userWithMostComments = mostCommentedUser.getKey();
        return userWithMostComments;
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
    public String makeMePretty(String json) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement =  new JsonParser().parse(json);
        System.out.println(gson.toJson(jsonElement));
        String prettyJson = gson.toJson(jsonElement);

        return prettyJson;
    }

    @Override
    public Map<String, String> convertLikesToJsonArray(Map<String, String> averageLikesPerGame) throws JSONException {
        return null;
    }

//    @Override
//    public JSONArray convertLikesToJsonArray(Map<String, String> averageLikesPerGame) throws JSONException {
//        JSONArray myArray = new JSONArray();
//
//        for(Map.Entry<String,String> entry : averageLikesPerGame.entrySet()) {
//            myArray.put(entry.getKey());
//            myArray.put(entry.getValue());
//        }
//        return myArray;
//    }

//    @Override
//    public Map<String, String> convertLikesToJsonArray(Map<String, String> averageLikesPerGame) throws JSONException {
//        Map<String,String> averageLikeGame = new HashMap<>();
//        Map<String,String> averageLikes = new HashMap<>();
//
//        for(Map.Entry<String,String> entry : averageLikesPerGame.entrySet()) {
//            averageLikeGame.put("title", entry.getKey());
//            averageLikes.put("average_likes",entry.getValue());
//        }
//        return myArray;
//    }
}
