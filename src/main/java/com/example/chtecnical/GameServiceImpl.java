package com.example.chtecnical;

import com.fasterxml.jackson.databind.ObjectMapper;
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
}