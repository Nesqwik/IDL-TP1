package wator;

import core.misc.Config;
import core.misc.Environment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WatorFactory {

    static private Queue<FishAgent> diedFishes = new LinkedList<>();
    static private Queue<SharkAgent> diedShark = new LinkedList<>();

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
