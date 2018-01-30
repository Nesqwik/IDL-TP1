package expWator;

import core.agents.Agent;
import core.misc.Environment;

public class PaperAgent extends Agent {
    public PaperAgent(Environment env, int x, int y) {
        super(env, x, y);
    }

    @Override
    public int getShape() {
        return 0;
    }

    @Override
    public void onDestroyed() {

    }
}
