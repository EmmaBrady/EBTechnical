package com.example.chtecnical;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface GameService {
    public abstract String findHighestRatedGame() throws IOException;
    public abstract String findUserWithMostComments() throws FileNotFoundException;
    public abstract Map<String, String> findAverageLikesPerGame() throws FileNotFoundException;
    public abstract String convertReportToJson(Map<String, String> report);
    public abstract String makeMePretty(String json);
}
