package wator;

import java.util.LinkedList;
import java.util.Queue;

import core.misc.Config;
import core.misc.Environment;

/**
 * Factory des poissons et requins
 */
public class WatorFactory {

    /**
     * file des poissons morts
     */
    static private Queue<FishAgent> diedFishes = new LinkedList<>();
    /**
     * file des requins morts
     */
    static private Queue<SharkAgent> diedShark = new LinkedList<>();

    /**
     * Création ou récupération d'un poisson
     * 
     * @param env
     * @param x
     * @param y
     * @return le nouveau poisson
     */
    public static FishAgent newFish(Environment env, int x, int y) {
        FishAgent fish;
        if (diedFishes.isEmpty()) {
            fish = createNewFish(env, x, y);
        } else {
            fish = diedFishes.poll();
        }

        fish.init(x, y, Config.getFishBreedTime());
        return fish;
    }

    /**
     * Récupération ou création d'un nouveau requin
     * 
     * @param env
     * @param x
     * @param y
     * @return le requin
     */
    public static SharkAgent newShark(Environment env, int x, int y) {
        SharkAgent shark;
        if (diedShark.isEmpty()) {
            shark = createNewShark(env, x, y);
        } else {
            shark = diedShark.poll();
        }

        shark.init(x, y, Config.getSharkBreedTime(), Config.getSharkFeedTime());
        return shark;
    }

    /**
     * Création d'un nouveau poisson
     * 
     * @param env
     * @param x
     * @param y
     * @return le nouveau poisson
     */
    private static FishAgent createNewFish(Environment env, int x, int y) {
        try {
            return (FishAgent) Class.forName(Config.getFishType()).getConstructor(Environment.class, Integer.class, Integer.class, Integer.class).newInstance(env, x, y, Config.getFishBreedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Création d'un nouveau requin
     * 
     * @param env
     * @param x
     * @param y
     * @return le nouveau requin
     */
    private static SharkAgent createNewShark(Environment env, int x, int y) {
        try {
            return (SharkAgent) Class.forName(Config.getSharkType()).getConstructor(Environment.class, Integer.class, Integer.class, Integer.class, Integer.class).newInstance(env, x, y, Config.getSharkBreedTime(), Config.getSharkFeedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ajout d'un poisson mort
     * 
     * @param fish
     */
    public static void addDiedFish(FishAgent fish) {
        diedFishes.add(fish);
    }

    /**
     * Ajout d'un requin mort
     * 
     * @param shark
     */
    public static void addDiedShark(SharkAgent shark) {
        diedShark.add(shark);
    }
}
