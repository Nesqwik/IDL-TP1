package pacman.sma;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.View;
import pacman.AvatarAgent;
import pacman.WallAgent;
import wator.WatorFactory;

import java.awt.*;
import java.util.List;

public class PacmanSMA extends SMA {

    public PacmanSMA(Environment env) {
        super(env);
    }

    public static void main(String[] args) {
        Config.load();

        new View(new PacmanSMA(new Environment()));
    }

    @Override
    protected void addAgents(List<Point> availableCoord) {
        Point c = availableCoord.get(0);
        AvatarAgent avatarAgent = new AvatarAgent(environment, c.x, c.y);
        view.getGrid().addKeyListener(avatarAgent);
        environment.addAgent(avatarAgent);

        for(int i = 1 ; i < availableCoord.size() / 5 ; i++) {
            c = availableCoord.get(i);
            System.out.println(c.x + ":" + c.y);
            environment.addAgent(new WallAgent(environment, c.x, c.y));
        }

        this.environment.actuallyAddAgents();
    }
}
