# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Server Design Sequence Diagram Link

Default: https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QqACYnE5Gt0y0MxWMYFXHlNa6l6020C3VgdiyDTtke5UACyD4dJHpTUbqYDk8ZTACiUG8ab0tshaIxkxXxQ7ScKG4wvZ3AGY96XDyyJ73BeV7QOUwAILqHBhPIC5Pu2a7vt2n6VAArLu3T-n0R6qEBZ59Je17lLCHBqDqxAEIkMAQAAZjAlDXpi8HoBwpheL4-gBNA7DkjAAAyEDREkARpBkWTIcwsrUCW1R1E0rQGOoCRoCOUyiqMKxfC8bwfH08Grmy+YlgeAzluOmlPNpSyaTAALnAWUlOvKMAIIJACSaCwgJ9geai6KxNiCaGC6YZumSFIGrSZmjCaRLhhaHIwNyvIGoKNHeMMMDqWIrrSm+RRqigCpKiquXmvlQXlGVrKQmAPi3CgtqpdophBRqoUsuUnrekGUVjjFoZxW6CXlNGMCxsGLWtXKwXJvZlzeTy2a5pgRmIQVJSlCZ2UWTOQbzs2Ez6S+60fmAvYDkOmHbdF9x6TW+2Nodem2QhnZnV+TgAIx-iZOF4VWhFgVlwRBtASAAF6Ncd0mnRJvboVd+4Acep6A6BaYkWRUAUcp1F0Qx0BMa9LFsd4fiBF4KDoPxgm+MwInpJkmBnWyq69tI558ee9Tns0LSKaoyndLOB1oG9c1AiWotPeLa1szN5SufYNS+gGj0LoFM3tUNnVGCg3CZOrday7FTLDZGiXSAbFKGBNgrCjAMsLtNhWdmt5TearYDLQgebzet7OlnsMCwgoADSCj0cM2BgGEsQ4w4Wuw+98OVJdv3Pin+Ufdu32-SjuFoyBREg7O4NQ8sr0GTnacVIjBfYYBxcERjxGkao5Gx3jtH0RjxMHGYnBkxxgSQrafHQjAADi46sozYksxJCuw72088-z9jjiLGvoBLhQe07u9ywHK9OUV-HQrPR7G3OpuOs6BIdaSMAgKkKAgA2E1i7fYtm2aEZCiWngO-T+40gxpWgEfE2LtBrmzyo5cEMBFTKjgQAzslUYC1XqkkKgEEkC2mdnvB+KgCjVTJOOSesRYT-3ipbcokRgCEOVDPccgoeTshIbNA+AdPZXznr7f2UtA4nS2n0Leow3LSDPF9Ps34txPFEpkA0FYjpPB0AgUADYVHmTUVMCRKAABy44jpZ2OLXHI5104YWRuIueZ4Kj9AMVImRciFFTCUfqW6u0+gaK0TojSei7GjGMaMUxMBGhmNfOuOuO4frXULgDEuwMfCgz9BXaG1cTqp0sQjGxWF-otyBpjDuXdKJJF7oTKAA8h6sU8OTTi2B6rYG4PAXUmRWGjBSEzcSliz4yVqA0Te280l3wXCOAxoSUBRJBDw4RpQiEqV6Po8cUybJ2WEWfcE3VMjXxQL-WWsxJnjmTufHW8CX5vw-l-Y+BzNZoLoUAxKcBQENnAXGbQMAaJQMWaYB5FtEHORQaVZ+EZAUX2wbcGAeDkCEOPq7M5IVdaXPaSgPZsJjkDWqiNcaNpOkoEFJAThbV8oex2Wi8cgjVqnwqqIrCzjpHlFkfIrJ2cYm5OsUjel44XFMrcay8x7LNwVB3Fyv6zdgKt1LreLBKAHyxBhoKpCHKRX5wSU3VGkrinlFSeXHGldFXROVcKhu6rClarbqHUpONu5UUqf3eCtSR4UwCJYA2rlkgwAAFIQA4XswIfjP5Lz6Y5AZlJ5ItAMTvGB6ARwtOAG6qAcAICuSgEcnl0gZmGV4TAKNoyxZxs0Ym5Nqb02SMzdSzZ4LygACtfVoHRdAsZ6BZjxuLSm6AZaUBSNOY-GQoKuqvO-rLO56BaEWyeeUaeFJ3mTXkF8jKTaxZ-OxbSwqEIKQ4NZPbFqJKn7IvdDAOtPJ0XZVbUWygJbO25ozeOvKk6+TYC0B0vZDswgMpgMkDIqQsq3RgBw7AEAhblJgG2jsIAO1QC4e7HNx6G2UrULmDQ8s12bWDhLIVKF+z5J6FmixwrRWNBaLY81+FtVCiYbK+VYBDVwxVXExupH0al11WDfVmS8OYasaq38ZqJVkctRBKCMFgBwQFUarsKrTUkf48x4GWNO42pA-axijqnX1NHgELwCauxelgMAbALTCDxCogvZmrNQ1pgqJzbmvN+bGH3mceZr9uB4BoZW1MWznIgFc3CXtiZ90XMPT5vT7nV0Ppszzc8v6BQKBqP8+97Iqpcyi026QcWEvlQiyl3mN7RjpfiySyWqYuq+apShyzxVUE1y4+US6GHjUoURqWDVRcxhPGKUYGIZTlJ9EHn80mQA
Presentation: https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QqACYnE5Gt0y0MxWMYFXHlNa6l6020C3VgdiyDTtke5UACyD4dJHpTUbqYDk8ZTACiUG8ab0tshaIxkxXxQ7ScKG4wvZ3AGY96XDyyJ73BeV7QOUwAILqHBhPIC5Pu2a7vt2n6VAArLu3T-n0R6qEBZ59Je17lLCHBqDqxAEIkMAQAAZjAlDXpi8HoBwpheL4-gBNA7DkjAAAyEDREkARpBkWTIcwsrUCW1R1E0rQGOoCRoCOUyiqMKxfC8bwfH08Grmy+YlgeAzluOmlPNpSyaTAALnAWUlOvKMAIIJACSaCwgJ9geai6KxNiCaGC6YZumSFIGrSZmjCaRLhhaHIwNyvIGoKNHeMMMDqWIrrSm+RRqigCpKiquXmvlQXlGVrKQmAPi3CgtqpdophBRqoUsuUnrekGUVjjFoZxW6CXlNGMCxsGLWtXKwXJvZlzeTy2a5pgRmIQVJSlCZ2UWTOQbzs2Ez6S+60fmAvYDkOmHbdF9x6TW+2Nodem2QhnZnV+TgAIx-iZOF4VWhFgVlwRBtASAAF6Ncd0mnRJvboVd+4Acep6A6BaYkWRUAUcp1F0Qx0BMa9LFsd4fiBF4KDoPxgm+MwInpJkmBnWyq69tI558ee9Tns0LSKaoyndLOB1oG9c1AiWotPeLa1szN5SufYNS+gGj0LoFM3tUNnVGCg3CZOrday7FTLDZGiXSAbFKGBNgrCjAMsLtNhWdmt5TearYDLQgebzet7OlnsMCwgoADSCj0cM2BgGEsQ4w4Wuw+98OVJdv3Pin+Ufdu32-SjuFoyBREg7O4NQ8sr0GTnacVIjBfYYBxcERjxGkao5Gx3jtH0RjxMHGYnBkxxgSQrafHQjAADi46sozYksxJCuw72088-z9jjiLGvoBLhQe07u9ywHK9OUV-HQrPR7G3OpuOs6BIdaSMAgKkKAgA2E1i7fYtm2aEZCiWngO-T+40gxpWgEfE2LtBrmzyo5cEMBFTKjgQAzslUYC1XqkkKgEEkC2mdnvB+KgCjVTJOOSesRYT-3ipbcokRgCEOVDPccgoeTshIbNA+AdPZXznr7f2UtA4nS2n0Leow3LSDPF9Ps34txPFEpkA0FYjpPB0AgUADYVHmTUVMCRKAABy44jpZ2OLXHI5104YWRuIueZ4Kj9AMVImRciFFTCUfqW6u0+gaK0TojSei7GjGMaMUxMBGhmNfOuOuO4frXULgDEuwMfCgz9BXaG1cTqp0sQjGxWF-otyBpjDuXdKJJF7oTKAA8h6sU8OTTi2B6rYG4PAXUmRWGjBSEzcSliz4yVqA0Te280l3wXCOAxoSUBRJBDw4RpQiEqV6Po8cUybJ2WEWfcE3VMjXxQL-WWsxJnjmTufHW8CX5vw-l-Y+BzNZoLoUAxKcBQENnAXGbQMAaJQMWaYB5FtEHORQaVZ+EZAUX2wbcGAeDkCEOPq7M5IVdaXPaSgPZsJjkDWqiNcaNpOkoEFJAThbV8oex2Wi8cgjVqnwqqIrCzjpHlFkfIrJ2cYm5OsUjel44XFMrcay8x7LNwVB3Fyv6zdgKt1LreLBKAHyxBhoKpCHKRX5wSU3VGkrinlFSeXHGldFXROVcKhu6rClarbqHUpONu5UUqf3eCtSR4UwCJYA2rlkgwAAFIQA4XswIfjP5Lz6Y5AZlJ5ItAMTvGB6ARwtOAG6qAcAICuSgEcnl0gZmGV4TAKNoyxZxs0Ym5Nqb02SMzdSzZ4LygACtfVoHRdAsZ6BZjxuLSm6AZaUBSNOY-GQoKuqvO-rLO56BaEWyeeUaeFJ3mTXkF8jKTaxZ-OxbSwqEIKQ4NZPbFqJKn7IvdDAOtPJ0XZVbUWygJbO25ozeOvKk6+TYC0B0vZDswgMpgMkDIqQsq3RgBw7AEAhblJgG2jsIAO1QC4e7HNx6G2UrULmDQ8s12bWDhLIVKF+z5J6FmixwrRWNBaLY81+FtVCiYbK+VYBDVwxVXExupH0al11WDfVmS8OYasaq38ZqJVkctRBKCMFgBwQFUarsKrTUkf48x4GWNO42pA-axijqnX1NHgELwCauxelgMAbALTCDxCogvZmrNQ1pgqJzbmvN+bGH3mceZr9uB4BoZW1MWznIgFc3CXtiZ90XMPT5vT7nV0Ppszzc8v6BQKBqP8+97Iqpcyi026QcWEvlQiyl3mN7RjpfiySyWqYuq+apShyzxVUE1y4+US6GHjUoURqWDVRcxhPGKUYGIZTlJ9EHn80mQA
