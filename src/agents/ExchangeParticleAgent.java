package agents;

import misc.Environment;

import java.awt.*;

public class ExchangeParticleAgent extends ParticleAgent {


    public ExchangeParticleAgent(Environment environment, Integer posX, Integer posY, Integer pasX, Integer pasY) {
        super(environment, posX, posY, pasX, pasY);
    }

    @Override
    protected void onCollide(ParticleAgent otherAgent, Agent[][] moore) {
        super.onCollide(otherAgent, moore);
        this.sendPas(otherAgent);
    }

    private void sendPas(ParticleAgent other) {
        int lastPasX = other.getPasX();
        int lastPasY = other.getPasY();

        other.setPasX(this.getPasX());
        other.setPasY(this.getPasY());

        this.setPasX(lastPasX);
        this.setPasY(lastPasY);
    }
}
