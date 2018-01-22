package misc;

import agents.Agent;
import agents.ParticleAgent;
import view.View;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class SMA extends Observable {

    private Environment environment;
    private Random random;
    private List<Point> availableCoord = new ArrayList<>();
    private int tickNumber = 1;

    public static void main(String[] args) {
        new View();
    }

    public SMA(Environment env) {
        this.environment = env;
        this.random = new Random(Config.getSeed());
    }

    public void populate(Environment env, int nbAgent) {
        long startTime = System.currentTimeMillis();
        for (int x = 0; x < env.getCols(); x++) {
            for (int y = 0; y < env.getRows(); y++) {
                availableCoord.add(new Point(x, y));
            }
        }
        Collections.shuffle(availableCoord, this.random);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println(elapsedTime);


        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbAgent; i++) {
            ParticleAgent agent = createParticleAgent(environment, availableCoord.get(i));
            env.addAgent(agent);
        }
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println(elapsedTime);
    }

    private ParticleAgent createParticleAgent(Environment env, Point coord) {
        int pasX = random.nextInt(3) - 1;
        int pasY = random.nextInt(3) - 1;

        try {
            return (ParticleAgent) Class.forName(Config.getParticleType()).getConstructor(Environment.class, Integer.class, Integer.class, Integer.class, Integer.class).newInstance(env, coord.x, coord.y, pasX, pasY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {

        int nbTicks = Config.getNbTicks();
        while (nbTicks == 0 || tickNumber <= nbTicks) {
            long startTime = System.currentTimeMillis();
            runOnce();
            setChanged();
            notifyObservers(environment);
            Logger.log("Tick;" + tickNumber);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            try {
                Thread.sleep(Math.max(0, Config.getDelay() - elapsedTime));
                tickNumber++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void runOnce() {
        switch (Config.getScheduling()) {
            case "equitable":
                runOnceFairRandom();
                break;
            case "sequentiel":
                runOnceSequencial();
                break;

            case "aleatoire":
                runOnceRandom();
                break;

            default:
                System.out.println("Mode de scheduling incorrect. Le mode sequentiel a été choisi.");
                runOnceSequencial();
        }
    }

    private void runOnceFairRandom() {
        List<Agent> agents = environment.getAgents();
        Collections.shuffle(agents, this.random);
        for (Agent agent : agents) {
            agent.decide();
        }
    }

    private void runOnceRandom() {
        int size = environment.getAgents().size();
        for (int i = 0; i < size; i++) {
            environment.getAgents().get(random.nextInt(size)).decide();
        }
    }

    private void runOnceSequencial() {
        for (Agent agent : environment.getAgents()) {
            agent.decide();
        }
    }

    public int getTickNumber() {
        return tickNumber;
    }
}
