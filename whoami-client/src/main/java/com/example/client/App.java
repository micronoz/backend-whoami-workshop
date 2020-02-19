package com.example.client;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public final class App {
    private static String id;
    private static final String base = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Console console = System.console();
    private static final Gson g = new Gson();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("######### Welcome to WhoAmI! #########");
        System.out.print("Please enter a user name: ");
        register(readLine());
        startGame();
    }

    private static void startGame() throws InterruptedException, IOException {
        GameState curState;
        boolean printOnce = false;

        while (true) {
            curState = checkStatus();
            if (curState.needsReady && !curState.gameInProgress) {
                System.out.println(curState.condition);
                System.out.print("Ready to play? (Y/N): ");
                String input = readLine();
                if (input.equals("Y")) {
                    System.out.println("Accepted game");
                    HttpResponse<String> response = sendReady();
                    if (response.statusCode() != 200)
                        System.out.println(response.body());
                    else
                        System.out.println("Game will start soon");
                } else if (input.equals("N")) {
                    System.exit(0);
                }

            } else if (curState.gameInProgress && curState.yourTurn) {
                if (!printOnce) {
                    System.out.println("The game has started!");
                    printOnce = true;
                }

                System.out.println("########### MESSAGE HISTORY ###########");
                for (Message m : curState.messages)
                    System.out.println(m.content);
                System.out.println("########### END MESSAGES ###########");

                if (curState.isAsking) {
                    askQuestion();
                } else {
                    giveAnswer();
                }

            }

            if (curState.gameInProgress) {
                System.out.println("########### MESSAGE HISTORY ###########");
                for (Message m : curState.messages)
                    System.out.println(m.content);
                System.out.println("########### END MESSAGES ###########");
            }

            Thread.sleep(5000);
        }
    }

    private static void askQuestion() throws IOException, InterruptedException {
        System.out.print("Ask your question: ");
        String input = readLine();
        if (!input.isBlank()) {
            sendMessage(input);
        } else {
            System.out.println("Please enter a valid question");
            askQuestion();
        }

    }

    private static void giveAnswer() throws IOException, InterruptedException {
        System.out.print("Give an answer (Y/N): ");
        String input = readLine();
        if (input.equals("Y") || input.equals("N"))
            sendMessage(input);
        else {
            System.out.println("Invalid answer format");
            giveAnswer();
        }
    }

    private static void sendMessage(String message) throws IOException, InterruptedException {
        HashMap<String, String> values = new HashMap<String, String>() {
            {
                put("id", id);
                put("message", message);
            }
        };

        client.send(buildRequest("/api/v1/game/send", values), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> sendReady() throws IOException, InterruptedException {
        HashMap<String, String> values = new HashMap<String, String>() {
            {
                put("id", id);
            }
        };

        HttpResponse<String> response = client.send(buildRequest("/api/v1/game/ready", values),
                HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private static GameState checkStatus() throws IOException, InterruptedException {
        HashMap<String, String> values = new HashMap<String, String>() {
            {
                put("id", id);
            }
        };

        HttpResponse<String> response = client.send(buildRequest("/api/v1/game/status", values),
                HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return g.fromJson(response.body(), GameState.class);
        } else {
            System.out.println("Waiting for admin to start game.");
            return new GameState();
        }

    }

    private static void register(String userName) throws IOException, InterruptedException {
        HashMap<String, String> values = new HashMap<String, String>() {
            {
                put("userName", userName);
            }
        };

        HttpResponse<String> response = client.send(buildRequest("/api/v1/user/register", values),
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Registration Successful!");
            id = response.body();
        } else {
            System.out.println("Looks like the user name is already in use.");
            System.out.print("Please enter another user name: ");
            register(readLine());
        }
    }

    private static String readLine() {
        return console.readLine();
    }

    private static HttpRequest buildRequest(String endpoint, HashMap<String, String> content)
            throws InterruptedException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(content);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(base + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).setHeader("Accept", "application/json")
                .setHeader("Content-type", "application/json").build();
        return request;
    }

}
