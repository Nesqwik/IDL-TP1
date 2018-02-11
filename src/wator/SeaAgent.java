package wator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;

/**
 * Agent mer
 */
public abstract class SeaAgent extends Agent {
    /**
     * temps de reproduction
     */
    protected int breedTime;
    /**
     * temps initial de reproduction
     */
    protected int initialBreedTime;
    /**
     * age de l'agent
     */
    protected int age;
    /**
     * age maximal 
     */
    public final int MAX_AGE = 20;

    /**
     * dernier x
     */
    protected int lastX;
    /**
     * dernier y
     */
    protected int lastY;

    /**
     * x position possible
     */
    private int xPossiblePos[] = {0, 0, 0, 0, 0, 0, 0, 0};
    /**
     * y position possible
     */
    private int yPossiblePos[] = {0, 0, 0, 0, 0, 0, 0, 0};
    /**
     * compteur position possible
     */
    protected int cptPos = 0;


    /**
     * Constructeur de l'agent 
     * 
     * @param env
     * @param x
     * @param y
     * @param breedTime
     */
    public SeaAgent(Environment env, int x, int y, int breedTime) {
        super(env, x, y);
        this.breedTime = breedTime;
        this.initialBreedTime = breedTime;
    }

    /**
     * initialisation de l'agent
     * 
     * @param x
     * @param y
     * @param breedTime
     */
    public void init(int x, int y, int breedTime) {
        super.init(x, y);
        this.breedTime = breedTime;
        this.initialBreedTime = breedTime;
        this.age = 0;
    }

    /**
     * mise a jour du temps de reproduction
     */
    protected void updateBreed() {
        this.breedTime -= 1;
    }

    @Override
    public void decide() {
        super.decide();
        this.lastX = this.getPosX();
        this.lastY = this.getPosY();
        this.updateBreed();
        this.age = Math.min(this.age + 1, MAX_AGE);

        Agent[][] moore = environment.getMoore(this);
        browseNeighbors(moore);
    }

    /**
     * Parcourir les voisins
     * 
     * @param moore voisins
     */
    public void browseNeighbors(Agent[][] moore) {
        initCpt();
        
        for (int x = 0; x < moore.length; x++) {
            for (int y = 0; y < moore[x].length; y++) {
                insertInArray(moore[x][y], x, y);
            }
        }
    }

    /**
     * initialize le compteur de position
     */
    protected void initCpt() {
        cptPos = 0;
    }
    
	/**
	 * inserer la nouvelle position dans le tableau des positions possibles
	 * 
	 * @param agent
	 * @param x
	 * @param y
	 */
	protected void insertInArray(Agent agent, int x, int y) {
		if (agent == null) {
		    xPossiblePos[cptPos] = x - 1;
		    yPossiblePos[cptPos] = y - 1;
		    cptPos++;
		}
	}

	/**
	 * Bouge l'agent s'il peut
	 * 
	 * @return vrai s'il a boug�
	 */
	protected boolean moveIfCan() {
       if (cptPos != 0) {
            int randInt = SMA.getRandom().nextInt(cptPos);
            environment.moveAgent(this, xPossiblePos[randInt], yPossiblePos[randInt]);
            return true;
        }

        return false;
    }

    /**
     * V�rifie s'il peut se reproduire
     * 
     * @return vrai s'il peut se reproduire
     */
    public boolean canReproduct() {
        return breedTime <= 0;
    }

    /**
     * Reproduit l'agent
     * 
     * @param x
     * @param y
     */
    protected void reproduct(int x, int y) {
        breedTime = initialBreedTime;
        newBorn(environment.getToricPosX(x), environment.getToricPosY(y));
    }

    /**
     * Cr�e un nouvel agent
     * 
     * @param x
     * @param y
     */
    public abstract void newBorn(int x, int y);


    /**
     * Reproduit s'il peut
     * 
     * @return vrai s'il s'est reproduit
     */
    protected boolean reproductIfCan() {
        if (!this.canReproduct()) {
            return false;
        }

        if (cptPos != 0) {
            int randInt = SMA.getRandom().nextInt(cptPos);
            this.reproduct(this.getPosX() + xPossiblePos[randInt], this.getPosY() + yPossiblePos[randInt]);
            return true;
        }

        return false;
    }
}
