package core.misc;

/**
 * Afficher la trace
 */
public class Logger {

    /**
     * Affiche la trace que si le paramètre trace est à vrai 
     * 
     * @param value le message à afficher
     */
    public static void log(String value) {
        if (Config.isTrace()) {
            System.out.println(value);
        }
    }
}
