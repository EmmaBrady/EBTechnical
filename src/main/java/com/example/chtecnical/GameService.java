package com.example.chtecnical;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GameService {
    public abstract String findGameBasedOffId(Integer id);
    public abstract String findHighestRatedGame() throws IOException;
    public abstract String findUserWithMostComments() throws FileNotFoundException;
    public abstract Map<String, String> findAverageLikesPerGame() throws FileNotFoundException;
    public abstract String convertReportToJson(Map<String, Object> report) throws JsonProcessingException;
    public abstract List<Map<String,String>> addLabelAvgLikesPerGame(Map<String, String> averageLikesPerGame);
}
