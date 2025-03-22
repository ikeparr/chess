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


}
