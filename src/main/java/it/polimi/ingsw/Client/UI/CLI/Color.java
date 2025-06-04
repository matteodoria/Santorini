package it.polimi.ingsw.Client.UI.CLI;

public enum Color {
    ANSI_RED("\u001B[31;1m"),
    ANSI_GREEN("\u001B[32;1m"),
    ANSI_YELLOW("\u001B[33;1m"),
    ANSI_BLUE("\u001B[36;1m"),
    ANSI_PURPLE("\u001B[35;1m"),
    ANSI_GREY("\u001B[61m"),
    ANSI_WHITE_BACKGROUND("\u001B[047m"),
    ANSI_BLUE_BOLD_BRIGHT("\033[1;94m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    Color(String escape) { this.escape = escape; }

    public String escape() { return escape; }
}
