package wator;

import core.misc.Config;
import core.misc.Environment;

import java.util.ArrayList;
import java.util.List;

public class WatorFactory {

    static private List<FishAgent> diedFishes = new ArrayList<>();
    static private List<SharkAgent> diedShark = new ArrayList<>();

    public static FishAgent newFish(Environment env, int x, int y) {
        /*FishAgent fish;
        if (diedFishes.isEmpty()) {
            fish = createNewFish(env);
        } else {
            fish = diedFishes.remove(0);
        }

        fish.init(x, y, Config.getFishBreedTime());*/
        return createNewFish(env, x, y);
    }

    public static SharkAgent newShark(Environment env, int x, int y) {
        /*SharkAgent shark;
        if (diedShark.isEmpty()) {
            shark = createNewShark(env);
        } else {
            shark = diedShark.remove(0);
        }

        shark.init(x, y, Config.getSharkBreedTime(), Config.getSharkFeedTime());*/
        return createNewShark(env, x, y);
    }

    private static FishAgent createNewFish(Environment env, int x, int y) {
        try {
            return (FishAgent) Class.forName(Config.getFishType()).getConstructor(Environment.class, Integer.class, Integer.class, Integer.class).newInstance(env, x, y, Config.getFishBreedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SharkAgent createNewShark(Environment env, int x, int y) {
        try {
            return (SharkAgent) Class.forName(Config.getSharkType()).getConstructor(Environment.class, Integer.class, Integer.class, Integer.class, Integer.class).newInstance(env, x, y, Config.getSharkBreedTime(), Config.getSharkFeedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addDiedFish(FishAgent fish) {
        diedFishes.add(fish);
    }

    public static void addDiedShark(SharkAgent shark) {
        diedShark.add(shark);
    }
}
