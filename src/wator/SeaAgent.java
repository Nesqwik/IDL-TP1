package wator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

public abstract class SeaAgent extends Agent {
    protected int breedTime;
    protected int initialBreedTime;

    protected int lastX;
    protected int lastY;

    private int xPossiblePos[] = {0, 0, 0, 0, 0, 0, 0, 0};
    private int yPossiblePos[] = {0, 0, 0, 0, 0, 0, 0, 0};


    public SeaAgent(Environment env) {
        super(env);
    }

    public void init(int x, int y, int breedTime) {
        super.init(x, y);
        this.breedTime = breedTime;
        this.initialBreedTime = breedTime;
    }

    protected void updateBreed() {
        this.breedTime -= 1;
    }

    @Override
    public void decide() {
        super.decide();
        this.lastX = this.getPosX();
        this.lastY = this.getPosY();
        this.updateBreed();
    }

    private int getXAndYPossibleCount(Agent[][] moore) {
        int cpt = 0;
        for (int x = 0; x < moore.length; x++) {
            for (int y = 0; y < moore[x].length; y++) {
                if (moore[x][y] == null) {
                    xPossiblePos[cpt] = x - 1;
                    yPossiblePos[cpt] = y - 1;
                    cpt++;
                }
            }
        }
        return cpt;
    }

    protected boolean moveIfCan(Agent[][] moore) {
        int cpt = getXAndYPossibleCount(moore);

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            environment.moveAgent(this, xPossiblePos[randInt], yPossiblePos[randInt]);
            return true;
        }

        return false;
    }

    public boolean canReproduct() {
        return breedTime <= 0;
    }

    protected void reproduct(int x, int y) {
        breedTime = initialBreedTime;
        newBorn(environment.getToricPosX(x), environment.getToricPosY(y));
    }

    public abstract void newBorn(int x, int y);


    protected boolean reproductIfCan(Agent[][] moore) {
        if (!this.canReproduct()) {
            return false;
        }

        int cpt = getXAndYPossibleCount(moore);

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            this.reproduct(this.getPosX() + xPossiblePos[randInt], this.getPosY() + yPossiblePos[randInt]);
            return true;
        }

        return false;
    }
}
