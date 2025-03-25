package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GameplayRepl {

    private final GameplayClient gameplayClient;
    private final int serverUrl;

    public GameplayRepl(int serverUrl, String authtoken, String color) {
        gameplayClient = new GameplayClient(serverUrl, authtoken, color);
        this.serverUrl = serverUrl;
    }


    public void run() {
        System.out.println("\nvalid inputs:");
        System.out.print(gameplayClient.help());
        gameplayClient.printBoard();

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = gameplayClient.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
                if (result.equals("User left the game\n")) {
                    break;
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }

}

