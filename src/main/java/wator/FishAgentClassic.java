package wator;

import core.misc.Environment;

/**
 * Agent poisson qui bouge et se reproduit s'il a boug�
 */
public class FishAgentClassic extends FishAgent {

    /**
     * Constructeur de l'agent poisson
     * 
     * @param env
     * @param posX
     * @param posY
     * @param breedTime
     */
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
