package pacman.sma;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.View;
import pacman.AvatarAgent;
import pacman.DefenderAgent;
import pacman.HunterAgent;
import pacman.Labyrinthe;
import pacman.Labyrinthe.Element;
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
    	Point c;
    	int cpt_next=0;
    	if (Config.isGenerateLabyrinthe()) {
    		generateLabyrinthe(availableCoord);
    		cpt_next = 3;
    	} else {
			for (int i = 3; i < availableCoord.size() / 5; i++) {
				c = availableCoord.get(i);
				environment.addAgent(new WallAgent(environment, c.x, c.y));
			}
			cpt_next = availableCoord.size() / 5;
    	}
    	
    	c = availableCoord.get(0);
        WinnerAgent winner = new WinnerAgent(environment, c.x, c.y);
        environment.addAgent(winner);
        
        c = availableCoord.get(1);
        AvatarAgent avatarAgent = new AvatarAgent(environment, c.x, c.y, winner);
        view.getGrid().addKeyListener(avatarAgent);
        environment.addAgent(avatarAgent);

        c = availableCoord.get(2);
        HunterAgent hunter = new HunterAgent(environment, c.x, c.y);
        environment.addAgent(hunter);

        c = availableCoord.get(cpt_next);
        environment.addAgent(new DefenderAgent(environment, c.x, c.y, availableCoord.subList(cpt_next + 1, availableCoord.size())));

        this.environment.actuallyAddAgents();
    }

	private void generateLabyrinthe(List<Point> availableCoord) {
		Labyrinthe labyrinthe = new Labyrinthe(environment.getCols(), environment.getRows(), SMA.getRandom());
        labyrinthe.genererLabyFusion();
        Element[][] laby = labyrinthe.getLabyrinthe();
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < labyrinthe.getLabyY(); y++) {
        	for (int x = 0; x < labyrinthe.getLabyX(); x++) {
        		Element elmt = laby[x][y];
        		if (elmt.E) {
        			if ((x * 2)+1 < environment.getCols() && (y * 2) < environment.getRows()) {
        				environment.addAgent(new WallAgent(environment, (x * 2)+1, y * 2));
        				points.add(new Point((x*2)+1, y*2));
        				if ((y * 2) + 1 < environment.getRows()) {
        					environment.addAgent(new WallAgent(environment, (x * 2) + 1, (y * 2) + 1));
        					points.add(new Point((x*2)+1, (y*2)+1));
        				}
        				if ((y * 2) - 1 >= 0) {
        					environment.addAgent(new WallAgent(environment, (x * 2) + 1, (y * 2) - 1));
        					points.add(new Point((x*2)+1, (y*2)-1));
        				}
        			}
        		}
        		if (elmt.S) {
        			if ((y * 2)+1 < environment.getRows() && (x * 2) < environment.getCols()) {
	        			environment.addAgent(new WallAgent(environment, (x * 2), (y * 2)+1));
	        			points.add(new Point((x*2), (y*2)+1));
	        			if ((x * 2) + 1 < environment.getCols()) {
	        				environment.addAgent(new WallAgent(environment, (x * 2) + 1, (y * 2) + 1));
	        				points.add(new Point((x*2) + 1, (y*2)+1));
	        			}
	        			if ((x*2) - 1 >= 0) {
	        				environment.addAgent(new WallAgent(environment, (x*2)-1, (y*2)+1));
	        				points.add(new Point((x*2)-1, (y*2)+1));
	        			}
        			}
        		}
        	}
        }
        
        availableCoord.removeAll(points);
	}
}
