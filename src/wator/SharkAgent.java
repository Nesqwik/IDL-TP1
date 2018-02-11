package wator;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;

/**
 * Agent requin
 */
public class SharkAgent extends SeaAgent {


    /**
     * temps de manger
     */
    protected int feedTime;
    /**
     * initial temps de manger
     */
    private int initialFeedTime;

    /**
     * poisson possible � manger
     */
    private FishAgent[] possibleFish = {null, null, null, null, null, null, null, null};
    /**
     * nombre de poisson
     */
    private int nbFish = 0;

    /**
     * Constructeur du requin
     * 
     * @param env
     * @param x
     * @param y
     * @param breedTime
     * @param feedTime
     */
    public SharkAgent(Environment env, int x, int y, int breedTime, int feedTime) {
        super(env, x, y, breedTime);
        this.feedTime = feedTime;
        this.initialFeedTime = feedTime;
        this.setColor(Config.pink);
    }

    /**
     * initialise le requin
     * 
     * @param x
     * @param y
     * @param breedTime
     * @param feedTime
     */
    public void init(int x, int y, int breedTime, int feedTime) {
        super.init(x, y, breedTime);
        this.feedTime = feedTime;
        this.initialFeedTime = feedTime;
        this.setColor(Config.pink);
    }

    @Override
    public Color getColor() {
        return super.getGradientColor(this.age, this.MAX_AGE, Config.red, Config.pink);
    }

    @Override
    public int getShape() {
        return Agent.TRIANGLE;
    }

    @Override
    public void newBorn(int x, int y) {
        //Logger.log("sharkAgent;born");
        environment.addAgent(WatorFactory.newShark(environment, x, y));
    }

    /**
     * mort du requin
     */
    protected void die() {
        //Logger.log("sharkAgent;die");
        environment.removeAgent(this);
        //WatorFactory.addDiedShark(this);
    }


    @Override
    public void decide() {
        super.decide();
        this.setColor(Config.red);
        feedTime--;
    }

    @Override
    public void onDestroyed() {
        WatorFactory.addDiedShark(this);
    }

    /**
     * tue le poisson
     * 
     * @param fish
     */
    protected void killFish(FishAgent fish) {
        fish.kill();
        feedTime = initialFeedTime;
    }


    /**
     * mange s'il peut
     * 
     * @return
     */
    protected boolean eatIfCan() {
        
        if (nbFish != 0) {
            int randInt = SMA.getRandom().nextInt(nbFish);
            this.killFish(possibleFish[randInt]);
            return true;
        }

        return false;
    }
    
    @Override
    protected void initCpt() {
    	super.initCpt();
    	nbFish=0;
    }
    
    @Override
    protected void insertInArray(Agent agent, int x, int y) {
    	super.insertInArray(agent, x, y);

    	if (agent instanceof FishAgent) {
            possibleFish[nbFish] = (FishAgent) agent;
            nbFish++;
        }
    }

    /**
     * mange et bouge s'il peut
     * 
     * @return vrai s'il a boug�
     */
    protected boolean eatAndMoveIfCan() {
       
        if (nbFish != 0) {
            int randInt = SMA.getRandom().nextInt(nbFish);
            int x = possibleFish[randInt].getPosX();
            int y = possibleFish[randInt].getPosY();
            this.killFish(possibleFish[randInt]);
            environment.moveAgent(this, x - this.getPosX(), y - this.getPosY());
            return true;
        }

        return false;
    }

}
