package wator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

public abstract class SeaAgent extends Agent {
    protected int breedTime;
    protected int initialBreedTime;

    protected int lastX;
    protected int lastY;

    int xPossiblePos[] = {0, 0, 0, 0, 0, 0, 0, 0};
    int yPossiblePos[] = {0, 0, 0, 0, 0, 0, 0, 0};

    public SeaAgent(Environment env, int x, int y, int breedTime) {
        super(env, x, y);

        this.breedTime = breedTime;
        this.initialBreedTime = breedTime;
    }

    protected void updateBreed() {
        this.breedTime -= 1;
        if (breedTime == 0) {
            reproduct();
            breedTime = initialBreedTime;
        }
    }

    @Override
    public void decide() {
        super.decide();
        this.lastX = this.getPosX();
        this.lastY = this.getPosY();
    }

    protected boolean moveIfCan(Agent[][] moore) {
        int cpt = 0;
        for (int x = 0; x < moore.length; x++) {
            for (int y = 0; y < moore[x].length; y++) {
                if (moore[x][y] == null) {
                    System.out.println(x + ":" + y);
                    xPossiblePos[cpt] = x - 1;
                    yPossiblePos[cpt] = y - 1;
                    cpt++;
                }
            }
        }

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            environment.moveAgent(this, xPossiblePos[randInt], yPossiblePos[randInt]);
            System.out.println("move");
            return true;
        }

        System.out.println("do not move");
        return false;
    }

    protected abstract void reproduct();
}
