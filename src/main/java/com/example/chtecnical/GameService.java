package com.example.chtecnical;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface GameService {
    public abstract String findHighestRatedGame() throws IOException;
    public abstract String findUserWithMostComments() throws FileNotFoundException;
    public abstract Map<String, String> findAverageLikesPerGame() throws FileNotFoundException;
    public abstract String convertReportToJson(Map<String, String> report) throws JsonProcessingException;
    public abstract String makeMePretty(String json);
    public abstract JSONArray convertLikesToJsonArray(Map<String, String> averageLikesPerGame) throws JSONException;
}
