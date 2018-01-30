package expWator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;

public class SymbolAgent extends Agent {
    public static int PAPER = 1;
    public static int ROCK = 2;
    public static int SCISOR = 3;

    private int symbol;
    private SymbolAgent[] possibleEatable = {null, null, null, null, null, null, null, null};


    public SymbolAgent(Environment env, int x, int y, int symbol) {
        super(env, x, y);

        this.symbol = symbol;
    }

    @Override
    public void decide() {
        Agent[][] moore = environment.getMoore(this);
        eatIfCan(moore);
    }


    protected boolean eatIfCan(Agent[][] moore) {
        int cpt = getPossibleFishesNumber(moore);

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            this.eat(possibleEatable[randInt]);
            return true;
        }

        return false;
    }

    private boolean canEat(SymbolAgent symbolAgent) {
        return this.getSymbol() == PAPER && symbolAgent.getSymbol() == ROCK || this.getSymbol() == ROCK && symbolAgent.getSymbol() == SCISOR || this.getSymbol() == SCISOR && symbolAgent.getSymbol() == PAPER;
    }

    public void eat(SymbolAgent symbolAgent) {
        symbolAgent.setSymbol(this.getSymbol());
    }

    private int getPossibleFishesNumber(Agent[][] moore) {
        int cpt = 0;
        for (int x = 0; x < moore.length; x++) {
            for (int y = 0; y < moore[x].length; y++) {
                if (moore[x][y] instanceof SymbolAgent) {
                    SymbolAgent symbolAgent = (SymbolAgent) moore[x][y];
                    if (this.canEat(symbolAgent)) {
                        possibleEatable[cpt] = symbolAgent;
                        cpt++;
                    }
                }
            }
        }
        return cpt;
    }

    @Override
    public int getShape() {
        return Agent.SQUARE;
    }

    @Override
    public Color getColor() {
        if(this.symbol == SCISOR) {
            return Color.RED;
        }
        if(this.symbol == PAPER) {
            return Color.BLUE;
        }
        if(this.symbol == ROCK) {
            return Color.GREEN;
        }

        return Color.BLACK;
    }

    @Override
    public void onDestroyed() {

    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
