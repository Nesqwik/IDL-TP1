package core.agents;


import java.awt.Color;

import core.misc.Environment;
import core.misc.Logger;

/**
 * Classe abstraite pour tous les agents
 */
public abstract class Agent {
    /**
     * forme carrée
     */
    public static int SQUARE = 1;
    /**
     * forme triangle
     */
    public static int TRIANGLE = 2;
    /**
     * forme ronde
     */
    public static int ROUND = 3;
    /**
     * identifiant de l'agent
     */
    public int id;
    /**
     * position X de l'agent
     */
    private int posX;
    /**
     * position Y de l'agent
     */
    private int posY;
    /**
     * couleur de l'agent
     */
    private Color color;
    /**
     * changement sur l'agent
     */
    private boolean hasChanged;
    /**
     * l'agent est sélectionné
     */
    private boolean isSelected = false;
    /**
     * l'agent est vivant
     */
    private boolean isAlive = true;
    /**
     * environnement de l'agent
     */
    protected Environment environment;

    /**
     * Constructeur de l'agent
     * 
     * @param environment environnement de l'agent
     * @param posX poisiton X de l'agent
     * @param posY position Y de l'agent
     */
    public Agent(Environment environment, int posX, int posY) {
        this.environment = environment;
        this.isAlive = true;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Initialise l'agent
     * 
     * @param posX position X
     * @param posY position Y
     */
    public void init(int posX, int posY) {
        this.isAlive = true;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Retourne la forme
     * 
     * @return la forme
     */
    public abstract int getShape();

    /**
     * Méthode decide de l'agent
     */
    public void decide() {
        hasChanged = false;
    }

    /**
     * Setter de hasChanger
     * 
     * @param hasChanged vrai si changement sinon faux
     */
    private void setChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    /**
     * Ajout d'un changement
     */
    protected void hasChanged() {
        this.hasChanged = true;
    }

    /**
     * Getter de la position X
     * 
     * @return position X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * getter de la position Y
     * 
     * @return position Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * setter de la position Y
     * 
     * @param posY position y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * setter de la position X
     * 
     * @param posX position X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * getter de la couleur
     * 
     * @return la couleur
     */
    public Color getColor() {
        if (isSelected) {
            return Color.BLUE;
        }
        if (color == null) {
            return Color.GRAY;
        }

        return color;
    }

    /**
     * setter de la couleur
     * 
     * @param color couleur
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * afficher des traces
     */
    protected void logAgent() {
        if (hasChanged) {
            Logger.log(this.toString());
        }
    }

    @Override
    public String toString() {
        return "Agent;" + posY + ";" + posY;
    }

    /**
     * getter de isSelected
     * 
     * @return vrai si l'agent est selectionne sinon faux
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * setter de isSelected
     * 
     * @param selected vrai si c'est selectionné sinon faux
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * getter de isAlive
     * 
     * @return vrai si l'agent est en vie sinon retourne faux
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * setter de isAlive
     * 
     * @param alive vrai si l'agent est en vie sinon faux
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * action a réaliser lors de la destruction de l'agent
     */
    public abstract void onDestroyed();
}
