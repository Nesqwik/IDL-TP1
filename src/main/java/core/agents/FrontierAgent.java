package core.agents;

import core.misc.Environment;

/**
 * Agent fronti�re qui permet de d�limiter le p�rim�tre
 */
public class FrontierAgent extends Agent {

    /**
     * Constructeur de l'agent fronti�re
     * 
     * @param environment l'environnement
     */
    public FrontierAgent(Environment environment) {
        super(environment, -1, -1);
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    @Override
    public void decide() {
    }

    @Override
    public void onDestroyed() {
    }
}
