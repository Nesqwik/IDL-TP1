package wator;

import core.misc.Environment;

public class WatorFactory {

    public static FishAgent newFish(Environment env, int x, int y) {
        return new FishAgent(env, x, y, 2);
    }
}
