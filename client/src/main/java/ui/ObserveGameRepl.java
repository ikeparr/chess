package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ObserveGameRepl {

    private final ObserveGameClient observeGameClient;
    private final int serverUrl;

    public ObserveGameRepl(int serverUrl, String authtoken) {
        observeGameClient = new ObserveGameClient(serverUrl, authtoken);
        this.serverUrl = serverUrl;
    }

    public void run() {
        System.out.println("\nvalid inputs:");
        System.out.print(observeGameClient.help());
        observeGameClient.printBoard();

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = observeGameClient.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
                if (result.equals("Observer left the game\n")) {
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
