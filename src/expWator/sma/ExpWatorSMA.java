package expWator.sma;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.View;
import expWator.SymbolAgent;
import expWator.WatorExpFactory;
import wator.WatorFactory;

import java.awt.*;
import java.util.List;

public class ExpWatorSMA extends SMA {

    public ExpWatorSMA(Environment env) {
        super(env);
    }

    public static void main(String[] args) {
        Config.load();

        new View(new ExpWatorSMA(new Environment()));
    }

    @Override
    protected void addAgents(List<Point> availableCoord) {

        for (int i = 0; i < availableCoord.size(); i++) {
            Point c = availableCoord.get(i);
            if(i < availableCoord.size() / 3) {
                this.environment.addAgent(WatorExpFactory.newSymbol(environment, c.x, c.y, SymbolAgent.PAPER));
            } else if(i < (availableCoord.size() / 3) * 2){
                this.environment.addAgent(WatorExpFactory.newSymbol(environment, c.x, c.y, SymbolAgent.ROCK));
            } else {
                this.environment.addAgent(WatorExpFactory.newSymbol(environment, c.x, c.y, SymbolAgent.SCISOR));
            }
        }

        this.environment.actuallyAddAgents();
    }
}
