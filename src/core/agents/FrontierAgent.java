package core.agents;

import core.misc.Environment;

/**
 * Agent frontière qui permet de délimiter le périmètre
 */
public class FrontierAgent extends Agent {

    /**
     * Constructeur de l'agent frontière
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
