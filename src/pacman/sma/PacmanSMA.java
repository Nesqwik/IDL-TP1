package pacman.sma;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.View;
import pacman.AvatarAgent;
import pacman.HunterAgent;
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
        AvatarAgent avatarAgent = new AvatarAgent(environment, c.x, c.y, Config.getSpeedAvatar());
        view.getGrid().addKeyListener(avatarAgent);
        environment.addAgent(avatarAgent);

        c = availableCoord.get(1);
        HunterAgent hunter = new HunterAgent(environment, c.x, c.y, Config.getSpeedHunter());
        environment.addAgent(hunter);

        for(int i = 2 ; i < availableCoord.size() / 5 ; i++) {
            c = availableCoord.get(i);
            environment.addAgent(new WallAgent(environment, c.x, c.y));
        }

        this.environment.actuallyAddAgents();
    }
}
