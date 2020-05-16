package com.example.chtecnical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RestController
public class GameController {

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

    @RequestMapping("/games")
    public Game[] JsonMapper() throws IOException {

        Reader data = new FileReader(new File("src/main/resources/games.json"));

        ObjectMapper mapper = new ObjectMapper();
        Game[] jsonObj = mapper.readValue(data, Game[].class);

        for (Game itr : jsonObj) {
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
    public Map<String,String> getReport() throws IOException {
        Reader data = new FileReader(new File("src/main/resources/games.json"));

        Map<String, String> report = new HashMap<>();
        ArrayList<String> usersCommented = new ArrayList<>();
        Map<String, Integer> gameLikes = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        Game[] jsonObj = mapper.readValue(data, Game[].class);

        int sumOfLikes = 0;

        for (Game game : jsonObj) {
            List<Comment> gameComments = game.getComments();

            int actualGameLikes = game.getLikes();
            sumOfLikes = sumOfLikes + actualGameLikes;

            for (Comment comment : gameComments) {
                String user = comment.getUser();
                usersCommented.add(user);

                int userLikes = comment.getLike();
                sumOfLikes = sumOfLikes + userLikes;

            }
            gameLikes.put(game.getTitle() + " ",sumOfLikes);
            sumOfLikes = 0;
        }

        Map.Entry<String, Long> mostCommentedUser
                = usersCommented.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        String userWithMostComments = mostCommentedUser.getKey();


        Map.Entry<String, Integer> highestRatedGame = null;
        for (Map.Entry<String, Integer> entry : gameLikes.entrySet())
        {
            if (highestRatedGame == null || entry.getValue().compareTo(highestRatedGame.getValue()) > 0)
            {
                highestRatedGame = entry;
            }
        }

        report.put("user_with_most_comments", userWithMostComments);
        report.put("highest_rated_game", highestRatedGame.getKey());

        return report;
    }
}
