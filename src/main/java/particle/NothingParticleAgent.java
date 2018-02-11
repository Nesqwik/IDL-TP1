package particle;

import core.agents.Agent;
import core.misc.Environment;

/**
 * Agent particule qui ne fait rien lors d'une collision avec un autre agent
 */
public class NothingParticleAgent extends ParticleAgent {

    /**
     * Constructeur de l'agent
     * 
     * @param environment
     * @param posX
     * @param posY
     * @param pasX
     * @param pasY
     */
    public NothingParticleAgent(Environment environment, Integer posX, Integer posY, Integer pasX, Integer pasY) {
        super(environment, posX, posY, pasX, pasY);
    }

    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
    }
}
