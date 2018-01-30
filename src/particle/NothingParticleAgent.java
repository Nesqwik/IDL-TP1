package particle;

import core.agents.Agent;
import core.misc.Environment;

public class NothingParticleAgent extends ParticleAgent {

    public NothingParticleAgent(Environment environment, Integer posX, Integer posY, Integer pasX, Integer pasY) {
        super(environment, posX, posY, pasX, pasY);
    }

    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
    }
}
