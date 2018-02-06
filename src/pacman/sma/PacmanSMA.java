package pacman.sma;

import java.awt.Point;
import java.util.List;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.View;
import pacman.AvatarAgent;
import pacman.DefenderAgent;
import pacman.HunterAgent;
import pacman.WallAgent;
import pacman.WinnerAgent;

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
        WinnerAgent winner = new WinnerAgent(environment, c.x, c.y);
        environment.addAgent(winner);
        
        c = availableCoord.get(1);
        AvatarAgent avatarAgent = new AvatarAgent(environment, c.x, c.y, winner);
        view.getGrid().addKeyListener(avatarAgent);
        environment.addAgent(avatarAgent);

        c = availableCoord.get(2);
        HunterAgent hunter = new HunterAgent(environment, c.x, c.y);
        environment.addAgent(hunter);

        for(int i = 3 ; i < availableCoord.size() / 5 ; i++) {
            c = availableCoord.get(i);
            environment.addAgent(new WallAgent(environment, c.x, c.y));
        }
        
        
        c = availableCoord.get(availableCoord.size()/5 + 1);
        environment.addAgent(new DefenderAgent(environment, c.x, c.y, availableCoord.subList(availableCoord.size()/5, availableCoord.size())));

        this.environment.actuallyAddAgents();
    }
}
