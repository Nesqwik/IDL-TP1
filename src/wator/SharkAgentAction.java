package wator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

public class SharkAgentAction extends SharkAgent {


    public SharkAgentAction(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();

        boolean hasEaten = eatIfCan();
        if (!hasEaten) {
            boolean hasReproduct = reproductIfCan();
            if (!hasReproduct) {
                moveIfCan();
            }
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
