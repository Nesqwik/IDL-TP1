package wator;

import core.misc.Environment;

public class FishAgentAction extends FishAgent {

    public FishAgentAction(Environment env, Integer posX, Integer posY, Integer breedTime) {
        super(env, posX, posY, breedTime);
    }


    @Override
    public void decide() {
        super.decide();

        boolean hasReproduct = this.reproductIfCan();
        if (!hasReproduct) {
            moveIfCan();
        }
    }
}
