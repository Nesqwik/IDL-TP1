package wator;

import core.misc.Environment;

/**
 * Agent poisson qui réalise une seule action a chaque tour : reproduire ou bouger
 */
public class FishAgentAction extends FishAgent {

    /**
     * Constructeur de l'agent poisson
     * 
     * @param env
     * @param posX
     * @param posY
     * @param breedTime
     */
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
