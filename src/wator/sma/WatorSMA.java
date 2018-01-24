package wator.sma;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.View;
import wator.WatorFactory;

import java.awt.*;
import java.util.List;

public class WatorSMA extends SMA {

    public WatorSMA(Environment env) {
        super(env);
    }

    public static void main(String[] args) {
        Config.load();

        new View(new WatorSMA(new Environment()));
    }

    @Override
    protected void addAgents(List<Point> availableCoord) {
        int nbFish = Config.getNbFish();

        for (int i = 0; i < nbFish; i++) {
            Point c = availableCoord.get(i);
            this.environment.addAgent(WatorFactory.newFish(environment, c.x, c.y));
        }
    }
}
