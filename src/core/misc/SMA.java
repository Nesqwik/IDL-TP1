package core.misc;

import core.agents.Agent;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class SMA extends Observable {

    protected Environment environment;
    protected static Random random;
    private List<Point> availableCoord = new ArrayList<>();
    private int tickNumber = 1;
    private boolean isRunning = true;

    public SMA(Environment env) {
        this.environment = env;
        random = new Random(Config.getSeed());
    }

    public void populate() {
        for (int x = 0; x < this.environment.getCols(); x++) {
            for (int y = 0; y < this.environment.getRows(); y++) {
                availableCoord.add(new Point(x, y));
            }
        }
        Collections.shuffle(availableCoord, random);
        this.addAgents(availableCoord);
        // Shuffle agents to equilibrate the speak turn
        Collections.shuffle(environment.getAgents());
    }

    protected abstract void addAgents(List<Point> availableCoord);

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        setChanged();
        notifyObservers(true);
    }

    public void run() {

        int nbTicks = Config.getNbTicks();
        long beginTime = System.currentTimeMillis();
        while (nbTicks == 0 || tickNumber <= nbTicks) {
            if (this.isRunning()) {
                update();
            } else {
                sleep(20);
            }
        }

        long elapsedTime = System.currentTimeMillis() - beginTime;
        System.out.println("temps passé : " + elapsedTime);
        System.out.println("moyenne par tick : " + (float) elapsedTime / (float) nbTicks);
    }

    public void runOnce() {
        long startTime = System.currentTimeMillis();
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

        Logger.log("Tick;" + tickNumber);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        long delay = 0;
        if (this.isRunning) delay = Config.getDelay() - elapsedTime;
        tickNumber++;
        sleep(Math.max(0, delay));
    }

    private void runOnceFairRandom() {
        List<Agent> agents = environment.getAgents();
        Collections.shuffle(agents, random);
        for (Agent agent : agents) {
            if(agent.isAlive()) {
                agent.decide();
            }
        }
    }

    private void runOnceRandom() {
        int size = environment.getAgents().size();
        for (int i = 0; i < size; i++) {
            Agent agent = environment.getAgents().get(random.nextInt(size));
            if(agent.isAlive()) {
                agent.decide();
            }
        }
    }

    private void runOnceSequencial() {
        for (Agent agent : environment.getAgents()) {
            if(agent.isAlive()) {
                agent.decide();
            }
        }
    }

    public int getTickNumber() {
        return tickNumber;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public static Random getRandom() {
        return random;
    }
}
