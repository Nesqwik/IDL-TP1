package wator;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.Logger;

public class FishAgentClassic extends FishAgent {

    public FishAgentClassic(Environment env, Integer posX, Integer posY, Integer breedTime) {
        super(env, posX, posY, breedTime);
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);

        boolean hasMoved = moveIfCan(moore);
        if (hasMoved && this.canReproduct()) {
            this.reproduct(this.lastX, this.lastY);
        }
    }
}
