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
    protected int cptPos = 0;


    public SeaAgent(Environment env, int x, int y, int breedTime) {
        super(env, x, y);
        this.breedTime = breedTime;
        this.initialBreedTime = breedTime;
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
        
        Agent[][] moore = environment.getMoore(this);
        browseNeighbors(moore);
    }

    public void browseNeighbors(Agent[][] moore) {
        initCpt();
        
        for (int x = 0; x < moore.length; x++) {
            for (int y = 0; y < moore[x].length; y++) {
                insertInArray(moore[x][y], x, y);
            }
        }
    }

    protected void initCpt() {
        cptPos = 0;
    }
    
	protected void insertInArray(Agent agent, int x, int y) {
		if (agent == null) {
		    xPossiblePos[cptPos] = x - 1;
		    yPossiblePos[cptPos] = y - 1;
		    cptPos++;
		}
	}

	protected boolean moveIfCan() {
       if (cptPos != 0) {
            int randInt = SMA.getRandom().nextInt(cptPos);
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
