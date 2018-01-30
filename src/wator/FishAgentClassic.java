package wator;

import core.misc.Environment;

public class FishAgentClassic extends FishAgent {

    public FishAgentClassic(Environment env, Integer posX, Integer posY, Integer breedTime) {
        super(env, posX, posY, breedTime);
    }

    @Override
    public void decide() {
        super.decide();

        boolean hasMoved = moveIfCan();
        if (hasMoved && this.canReproduct()) {
            this.reproduct(this.lastX, this.lastY);
        }
    }
}
