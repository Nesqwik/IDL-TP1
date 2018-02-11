package expWator;

import core.agents.Agent;
import core.misc.Environment;
import core.misc.SMA;

import java.awt.*;

/**
 * Acteur pierre-papier-ciseau distingu� par une constante
 */
public class SymbolAgent extends Agent {
    /**
     * papier
     */
    public static int PAPER = 1;
    /**
     * pierre
     */
    public static int ROCK = 2;
    /**
     * ciseaux
     */
    public static int SCISOR = 3;

    /**
     * symbole
     */
    private int symbol;
    /**
     * voisins qui peuvent �tre mang�s
     */
    private SymbolAgent[] possibleEatable = {null, null, null, null, null, null, null, null};


    /**
     * Constructeur de l'agent
     * 
     * @param env
     * @param x
     * @param y
     * @param symbol
     */
    public SymbolAgent(Environment env, int x, int y, int symbol) {
        super(env, x, y);

        this.symbol = symbol;
    }

    @Override
    public void decide() {
        Agent[][] moore = environment.getMoore(this);
        eatIfCan(moore);
    }


    /**
     * Extension de la couleur
     * 
     * @param moore les voisins de moore
     * @return vrai s'il a mang�
     */
    protected boolean eatIfCan(Agent[][] moore) {
        int cpt = getPossibleFishesNumber(moore);

        if (cpt != 0) {
            int randInt = SMA.getRandom().nextInt(cpt);
            this.eat(possibleEatable[randInt]);
            return true;
        }

        return false;
    }

    /**
     * V�rifie si le symbole peut manger 
     * 
     * @param symbolAgent cible
     * @return vrai si le symbole peut manger
     */
    private boolean canEat(SymbolAgent symbolAgent) {
        return this.getSymbol() == PAPER && symbolAgent.getSymbol() == ROCK || this.getSymbol() == ROCK && symbolAgent.getSymbol() == SCISOR || this.getSymbol() == SCISOR && symbolAgent.getSymbol() == PAPER;
    }

    /**
     * Mange (extension du symbole)
     * 
     * @param symbolAgent
     */
    public void eat(SymbolAgent symbolAgent) {
        symbolAgent.setSymbol(this.getSymbol());
    }

    /**
     * r�cup�re le nombre de voisins possible � manger
     * 
     * @param moore les voisins
     * @return le nombre de voisins que l'agent peut manger
     */
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

    /**
     * getter du symbole
     * 
     * @return symbol
     */
    public int getSymbol() {
        return symbol;
    }

    /**
     * setter du symbole
     * 
     * @param symbol
     */
    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
