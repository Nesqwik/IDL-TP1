package particle;

import core.agents.Agent;
import core.misc.Environment;

public class NothingParticleAgent extends ParticleAgent {

    public NothingParticleAgent(Environment environment) {
        super(environment);
    }

    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
    }
}
