package misc;

import agents.Agent;
import agents.ParticleAgent;
import view.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class SMA extends Observable {

    private Environment environment;
    private Random random;
    private ArrayList<Point> availableCoord = new ArrayList<>();

    public static void main(String[] args) {
        Environment env = new Environment(1000, 1000, true);

        SMA sma = new SMA(env);

        sma.populate(env, (int) (200000));


        View view = new View(env);

        sma.addObserver(view);
        sma.run();
    }

    private void populate(Environment env, int nbAgent) {
        for(int x = 0 ; x < env.getCols() ; x++) {
            for(int y = 0 ; y < env.getRows() ; y++) {
                availableCoord.add(new Point(x, y));
            }
        }

        for(int i = 0 ; i < nbAgent ; i++) {
            ParticleAgent agent = createParticleAgent(env);
            env.addAgent(agent);
        }

    }

    private ParticleAgent createParticleAgent(Environment env) {
        Point coord = availableCoord.remove(random.nextInt(availableCoord.size()));

        int pasX = random.nextInt(3) - 1;
        int pasY = random.nextInt(3) - 1;
        return new ParticleAgent(env, coord.x, coord.y, pasX, pasY);
    }

    public SMA(Environment env) {
        this.environment = env;
        this.random = new Random();
    }

    public void run() {
        while(true) {
            long startTime = System.currentTimeMillis();
            runOnce();
            setChanged();
            notifyObservers(environment);
            long endTime = System.currentTimeMillis();

            System.out.println(endTime - startTime);
            try {
                Thread.sleep(0l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void runOnce() {
        for(Agent agent : environment.getAgents()) {
            agent.decide();
        }
    }
}
