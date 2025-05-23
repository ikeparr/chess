package ui;


import java.util.Scanner;
import static ui.EscapeSequences.*;

public class PostLoginRepl {

    private final PostLoginClient postLoginClient;
    private final int serverUrl;

    public PostLoginRepl(int serverUrl, String authToken, String usernameLoggedIn) {
        postLoginClient = new PostLoginClient(serverUrl, authToken, usernameLoggedIn);
        this.serverUrl = serverUrl;
    }

    public void run() {
        System.out.println("\nvalid inputs:");
        System.out.print(postLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = postLoginClient.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
                if (result.equals("User logged out.\n")) {
                    PreLoginRepl preLoginRepl = new PreLoginRepl(serverUrl);
                    preLoginRepl.run();
                    break;
                }
                if (result.equals("User joined game.\n")) {
                    GameplayRepl gameplayRepl = new GameplayRepl(
                            serverUrl,
                            postLoginClient.authToken,
                            postLoginClient.playerColor,
                            postLoginClient.gameIDJoined);

                    gameplayRepl.run();
                }
                if (result.equals("User observing game.\n")) {
                    ObserveGameRepl observeGameRepl = new ObserveGameRepl(serverUrl, postLoginClient.authToken);
                    observeGameRepl.run();
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
