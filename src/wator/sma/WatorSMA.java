package wator.sma;

import java.awt.Point;
import java.util.List;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.Grid;
import core.view.View;
import wator.WatorFactory;

public class WatorSMA extends SMA {

    public WatorSMA(Environment env) {
        super(env);
    }

    public static void main(String[] args) {
        Config.load();

        Environment environment = new Environment();
        WatorSMA SMA = new WatorSMA(environment);
        Grid grid = new Grid(environment, SMA);

        new View(SMA, grid);
    }

    @Override
    protected void addAgents(List<Point> availableCoord) {
        int nbFish = Config.getNbFish();
        int nbShark = Config.getNbShark();

        for (int i = 0; i < nbFish + nbShark; i++) {
            Point c = availableCoord.get(i);

            if(i < nbFish) {
                this.environment.addAgent(WatorFactory.newFish(environment, c.x, c.y));
            } else {
                this.environment.addAgent(WatorFactory.newShark(environment, c.x, c.y));
            }
        }

        this.environment.actuallyAddAgents();
    }
}
