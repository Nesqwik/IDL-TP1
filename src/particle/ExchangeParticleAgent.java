package particle;

import core.agents.Agent;
import core.misc.Environment;

/**
 * Particule qui échange les pas lors de collision
 */
public class ExchangeParticleAgent extends ParticleAgent {


    /**
     * Constructeur de l'agent qui échange les pas
     * 
     * @param environment
     * @param posX
     * @param posY
     * @param pasX
     * @param pasY
     */
    public ExchangeParticleAgent(Environment environment, Integer posX, Integer posY, Integer pasX, Integer pasY) {
        super(environment, posX, posY, pasX, pasY);
    }
    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
        this.sendPas(otherAgent);
    }

    /**
     * Envoie les pas à l'autre agents
     * 
     * @param other
     */
    private void sendPas(ParticleAgent other) {
        int lastPasX = other.getPasX();
        int lastPasY = other.getPasY();

        other.setPasX(this.getPasX());
        other.setPasY(this.getPasY());

        this.setPasX(lastPasX);
        this.setPasY(lastPasY);
    }
}
