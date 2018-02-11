package expWator.sma;

import java.awt.Point;
import java.util.List;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.Grid;
import core.view.View;
import expWator.SymbolAgent;
import expWator.WatorExpFactory;

/**
 * SMA de l'exp�rience de pierre-papier-ciseaux
 */
public class ExpWatorSMA extends SMA {

    /**
     * Constructeur de la sma
     * 
     * @param env
     */
    public ExpWatorSMA(Environment env) {
        super(env);
    }

    /**
     * Lancer l'exp�rience pierre-papier-ciseaux
     * 
     * @param args
     */
    public static void main(String[] args) {
        Config.load();
        
        Environment environment = new Environment();
        ExpWatorSMA SMA = new ExpWatorSMA(environment);
        Grid grid = new Grid(environment, SMA);

        new View(SMA, grid);
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
