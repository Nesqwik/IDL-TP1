package core.misc;

public class Logger {

    public static void log(String value) {
        if (Config.isTrace()) {
            System.out.println(value);
        }
    }
}
