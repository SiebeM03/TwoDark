package woareXengine.util;

public class Logger {
    // Color codes for ANSI terminal
    public static final String RESET = "\033[0m";  // Reset color
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";


    /** Log a message to the console */
    public static void log(String message) {
        System.out.println(message);
    }

    /** Log a message to the console with a color */
    public static void log(String color, String message) {
        System.out.println(color + message + RESET);
    }

    /** Log an error message in red */
    public static void error(String message) {
        log(RED, "[ERROR]: " + message);
    }

    /** Log a warning message in yellow */
    public static void warn(String message) {
        log(YELLOW, "[WARNING]: " + message);
    }

    /** Log an info message in blue */
    public static void info(String message) {
        log(BLUE, "[INFO]: " + message);
    }

    /** Log a success message in green */
    public static void success(String message) {
        log(GREEN, "[SUCCESS]: " + message);
    }

    /** Log a debug message in purple */
    public static void debug(String message) {
        log(PURPLE, "[DEBUG]: " + message);
    }
}