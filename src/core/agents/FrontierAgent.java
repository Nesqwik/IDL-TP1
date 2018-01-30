package core.agents;

import core.misc.Environment;

public class FrontierAgent extends Agent {


    public FrontierAgent(Environment environment) {
        super(environment, -1, -1);
        //super.init(-1, -1);
    }

    @Override
    public int getShape() {
        return Agent.ROUND;
    }

    @Override
    public void decide() {}
}
