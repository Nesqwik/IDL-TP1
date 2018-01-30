package wator;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.Logger;
import core.misc.SMA;

public class SharkAgent extends SeaAgent {


    protected int feedTime;
    private int initialFeedTime;

    private FishAgent[] possibleFish = {null, null, null, null, null, null, null, null};

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

    protected void killFish(FishAgent fish) {
        fish.kill();
        feedTime = initialFeedTime;
    }


    protected boolean eatIfCan(Agent[][] moore) {
        int cpt = getPossibleFishesNumber(moore);

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            this.killFish(possibleFish[randInt]);
            return true;
        }

        return false;
    }


    private int getPossibleFishesNumber(Agent[][] moore) {
        int cpt = 0;
        for (int x = 0; x < moore.length; x++) {
            for (int y = 0; y < moore[x].length; y++) {
                if (moore[x][y] instanceof FishAgent) {
                    possibleFish[cpt] = (FishAgent) moore[x][y];
                    cpt++;
                }
            }
        }
        return cpt;
    }

    protected boolean eatAndMoveIfCan(Agent[][] moore) {
        int cpt = getPossibleFishesNumber(moore);

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            int x = possibleFish[randInt].getPosX();
            int y = possibleFish[randInt].getPosY();
            this.killFish(possibleFish[randInt]);
            environment.moveAgent(this, x - this.getPosX(), y - this.getPosY());
            return true;
        }

        return false;
    }

}
