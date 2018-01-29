package particle;

import core.agents.Agent;
import core.misc.Environment;

public class InvertParticleAgent extends ParticleAgent {

    public InvertParticleAgent(Environment environment) {
        super(environment);
    }

    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
        this.setPasX(-this.getPasX());
        this.setPasY(-this.getPasY());
    }
}
