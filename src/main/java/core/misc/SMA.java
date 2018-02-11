package core.misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import core.agents.Agent;
import core.view.View;

/**
 * SMA abstrait
 */
public abstract class SMA extends Observable {

    /**
     * environnement
     */
    protected Environment environment;
    /**
     * random
     */
    protected static Random random;
    /**
     * points disponibles
     */
    private List<Point> availableCoord = new ArrayList<>();
    /**
     * nombre de tick
     */
    private int tickNumber = 1;
    /**
     * temps d'attente
     */
    private int sleepTime = 0;
    /**
     * est en cours
     */
    private boolean isRunning = true;

    /**
     * vue
     */
    protected View view;

    /**
     * Constructeur de la SMA
     * 
     * @param env l'environnement des agents
     */
    public SMA(Environment env) {
        this.environment = env;
        random = new Random(Config.getSeed());
    }

    /**
     * int�grer des agents dans l'environnement
     * 
     * @param view la vue de l'environnement
     */
    public void populate(View view) {
        this.view = view;

        for (int x = 0; x < this.environment.getCols(); x++) {
            for (int y = 0; y < this.environment.getRows(); y++) {
                availableCoord.add(new Point(x, y));
            }
        }
        Collections.shuffle(availableCoord, random);
        this.addAgents(availableCoord);
        // Shuffle agents to equilibrate the speak turn
        Collections.shuffle(environment.getAgents(), random);
    }

    /**
     * Ajouter des agents dans l'environnement
     * 
     * @param availableCoord
     */
    protected abstract void addAgents(List<Point> availableCoord);

    /**
     * sleep
     * 
     * @param ms
     */
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * mise � jour
     */
    public void update() {
        setChanged();
        notifyObservers(true);
    }

    /**
     * traitement
     */
    public void run() {

        int nbTicks = Config.getNbTicks();

        long elapsedTime;
        long lastTime;
        while (nbTicks == 0 || tickNumber <= nbTicks) {
            lastTime = System.currentTimeMillis();
            if (this.isRunning() && sleepTime <= 0) {
                update();
                sleepTime = Config.getDelay();
            } else {
                sleep(20);
            }
            elapsedTime = System.currentTimeMillis() - lastTime;
            sleepTime -= elapsedTime;
        }
    }

    /**
     * un seul tour
     */
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
        Logger.log("Time;" + elapsedTime);
    }

    /**
     * tour de parole �quilibr�
     */
    private void runOnceFairRandom() {
        List<Agent> agents = environment.getAgents();
        Collections.shuffle(agents, random);
        for (Agent agent : agents) {
            if(agent.isAlive()) {
                agent.decide();
            }
        }
    }

    /**
     * tour de parole al�atoire
     */
    private void runOnceRandom() {
        int size = environment.getAgents().size();
        for (int i = 0; i < size; i++) {
            Agent agent = environment.getAgents().get(random.nextInt(size));
            if(agent.isAlive()) {
                agent.decide();
            }
        }
    }

    /**
     * tour de parole s�quentiel
     */
    private void runOnceSequencial() {
        for (Agent agent : environment.getAgents()) {
            if(agent.isAlive()) {
                agent.decide();
            }
        }
    }

    /**
     * Getter tickNumber
     * 
     * @return tickNumber
     */
    public int getTickNumber() {
        return tickNumber;
    }

    /**
     * Getter isRunning
     * 
     * @return isRunning
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * setter running
     * 
     * @param running
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * getter environment
     * 
     * @return environment
     */
    public Environment getEnvironment() {
        return this.environment;
    }

    /**
     * getter random
     * 
     * @return random
     */
    public static Random getRandom() {
        return random;
    }
}
