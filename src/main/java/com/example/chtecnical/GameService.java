package com.example.chtecnical;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GameService {
    public abstract String findHighestRatedGame() throws IOException;
    public abstract String findUserWithMostComments() throws FileNotFoundException;
}
