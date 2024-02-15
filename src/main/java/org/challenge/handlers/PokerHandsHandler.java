package org.challenge.handlers;

import org.challenge.Main;
import org.challenge.exceptions.DuplicateCardException;
import org.challenge.exceptions.UnmatchedCardValueException;
import org.challenge.models.Hand;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class PokerHandsHandler {
    private static final String DEFAULT_FILE_NAME = "challenge-samples.txt";

    public void handle(String[] args) throws Exception {
        String file = handleArguments(args);
        InputStream inputStream = getInputStream(file);
        processInputStream(inputStream);
    }

    /**
     * Handles arguments from the entrypoint of the application
     *
     * @param args - The arguments from the application entrypoint
     * @return String || null - If arguments are supplied
     */
    private String handleArguments(String[] args) throws IllegalArgumentException {
        if(args == null || args.length == 0) {
            return null;
        }

        if (args.length == 1) {
            return args[0];
        }

        throw new IllegalArgumentException("Too many arguments have been supplied");
    }

    private InputStream getInputStream(String customFilePath) throws IOException, IllegalArgumentException {
        if(null != customFilePath) {
            Path path = Paths.get(customFilePath);
            if(!Files.exists(path)) {
                throw new FileNotFoundException("File not found: " + customFilePath);
            }

            if(Files.size(path) == 0) {
                throw new IllegalArgumentException("File is empty");
            }

            return new FileInputStream(customFilePath);
        } else {
            ClassLoader cl = Main.class.getClassLoader();
            return cl.getResourceAsStream(DEFAULT_FILE_NAME);
        }
    }

    private void processInputStream(InputStream inputStream) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            AtomicInteger lineCount = new AtomicInteger();
            br.lines().forEach(line -> {
                lineCount.getAndIncrement();

                try {
                    processPokerHandLine(line);
                } catch (IllegalArgumentException | UnmatchedCardValueException | DuplicateCardException e) {
                    System.out.println("Line #" + lineCount + " (" + line + ") failed with exception: " + e);
                }
            });
        }
    }

    private void processPokerHandLine(String line) throws IllegalArgumentException, UnmatchedCardValueException, DuplicateCardException {
        if(null != line && !line.isEmpty()) {
            Hand hand = new Hand(line.split(" "));
            System.out.println(line + " => " + hand.determineHandRank());
        }
    }
}
