package wator;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;

public class SharkAgent extends SeaAgent {


    protected int feedTime;
    private int initialFeedTime;

    private FishAgent[] possibleFish = {null, null, null, null, null, null, null, null};
    private int nbFish = 0;

    public SharkAgent(Environment env, int x, int y, int breedTime, int feedTime) {
        super(env, x, y, breedTime);
        this.feedTime = feedTime;
        this.initialFeedTime = feedTime;
        this.setColor(Config.pink);
    }

    public void init(int x, int y, int breedTime, int feedTime) {
        super.init(x, y, breedTime);
        this.feedTime = feedTime;
        this.initialFeedTime = feedTime;
        this.setColor(Config.pink);
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

    protected void killFish(FishAgent fish) {
        fish.kill();
        feedTime = initialFeedTime;
    }


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
