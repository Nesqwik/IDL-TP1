package particle;

import core.agents.Agent;
import core.misc.Environment;

/**
 * Agent particule qui inverse ses propres pas lors de collision
 */
public class InvertParticleAgent extends ParticleAgent {

    /**
     * Constructeur de l'agent particule
     * 
     * @param environment
     * @param posX
     * @param posY
     * @param pasX
     * @param pasY
     */
    public InvertParticleAgent(Environment environment, Integer posX, Integer posY, Integer pasX, Integer pasY) {
        super(environment, posX, posY, pasX, pasY);
    }

    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
        this.setPasX(-this.getPasX());
        this.setPasY(-this.getPasY());
    }
}
