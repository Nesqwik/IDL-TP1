package core.misc;

/**
 * Afficher la trace
 */
public class Logger {

    /**
     * Affiche la trace que si le param�tre trace est � vrai 
     * 
     * @param value le message � afficher
     */
    public static void log(String value) {
        if (Config.isTrace()) {
            System.out.println(value);
        }
    }
}
