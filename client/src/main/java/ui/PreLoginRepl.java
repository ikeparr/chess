package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class PreLoginRepl {

    private final PreLoginClient preLoginClient;
    private final int serverURL;

    public PreLoginRepl(int serverUrl) {
        preLoginClient = new PreLoginClient(serverUrl);
        this.serverURL = serverUrl;
    }

    public void run() {
        System.out.println("Welcome to CS240 Chess. Login or register to start.");
        System.out.print(preLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = preLoginClient.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
                //             IF AUTHTOKEN != NULL THEN IN POSTLOGIN REPL
                if (preLoginClient.authToken != null) {
                    PostLoginRepl postLoginRepl = new PostLoginRepl(serverURL, preLoginClient.authToken, preLoginClient.usernameLoggedIn);
                    postLoginRepl.run();
                    break;
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

//    public void notify(Notification notification) {
//        System.out.println(SET_TEXT_COLOR_RED + notification.message());
//        printPrompt();
//    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }
}
