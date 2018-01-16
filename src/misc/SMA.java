package misc;

import agents.Agent;
import agents.ParticleAgent;
import view.View;

import java.util.Observable;

public class SMA extends Observable {

    private Environment environment;

    public static void main(String[] args) {
        Environment env = new Environment(5, 5, true);
        env.addAgent(new ParticleAgent(env, 0, 0, 1, 1));
        env.addAgent(new ParticleAgent(env, 1, 1, 1, 1));


        SMA sma = new SMA(env);


        View view = new View(env);

        sma.addObserver(view);
        sma.run();
    }

    public SMA(Environment env) {
        this.environment = env;
    }

    public void run() {
        while(true) {
            long startTime = System.currentTimeMillis();
            runOnce();
            setChanged();
            notifyObservers(environment);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
    }

    private void runOnce() {
        for(Agent agent : environment.getAgents()) {
            agent.decide();
        }
    }
}
