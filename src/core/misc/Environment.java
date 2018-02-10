package core.misc;

import java.util.LinkedList;
import java.util.List;

import core.agents.Agent;
import core.agents.FrontierAgent;

/**
 * Environnement des agents
 */
public class Environment {

	/**
	 * toricité
	 */
	protected boolean isToric;

    /**
     * liste de tous les agents
     */
    protected List<Agent> agents = new LinkedList<>();
    /**
     * liste des agents à ajouter
     */
    protected List<Agent> agentsToAdd = new LinkedList<>();
    /**
     * liste des agents à retirer
     */
    protected List<Agent> agentsToRemove = new LinkedList<>();

    /**
     * un agent frontière
     */
    protected FrontierAgent frontier = new FrontierAgent(this);

    /**
     * grille des agents
     */
    protected Agent[][] grid;
    /**
     * nombre de colonnes
     */
    protected int cols;
    /**
     * nombre de ligne
     */
    protected int rows;

    /**
     * Constructeur de l'environnement
     */
    public Environment() {

        this.cols = Config.getGridSizeX();
        this.rows = Config.getGridSizeY();

        grid = new Agent[cols][rows];

        this.isToric = Config.isTorus();
    }

    /**
     * ajouter un agent
     * 
     * @param agent
     */
    public void addAgent(Agent agent) {
        agentsToAdd.add(agent);

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

    /**
     * supprimer un agent
     * 
     * @param agent
     */
    public void removeAgent(Agent agent) {
        agentsToRemove.add(agent);
        agent.setAlive(false);

        grid[agent.getPosX()][agent.getPosY()] = null;
    }

    /**
     * actualise la liste des ajouts des agents
     */
    public void actuallyAddAgents() {
        agents.addAll(agentsToAdd);
        agentsToAdd.clear();
    }

    /**
     * actualise la liste des agents supprimés
     */
    public void actuallyRemoveAgents() {
        for (Agent a : agentsToRemove) {
            agents.remove(a);
            a.onDestroyed();
        }
        agentsToRemove.clear();
    }

    /**
     * Récupère un agent dans la grille
     * 
     * @param gridX position X
     * @param gridY position Y
     * @return l'agent
     */
    public Agent getAgent(int gridX, int gridY) {
        if (gridX >= grid.length) return null;
        if (gridY >= grid[0].length) return null;

        return grid[gridX][gridY];
    }

    /**
     * Récupère la liste de tous les agents
     * 
     * @return liste de tous les agents
     */
    public List<Agent> getAgents() {
        return agents;
    }

    /**
     * Récupère tous les voisins de Moore
     * 
     * @param agent l'agent cible
     * @return la liste des voisins
     */
    public Agent[][] getMoore(Agent agent) {
        if (isToric) {
            return getMooreToric(agent);
        } else {
            return getMooreClassic(agent);
        }
    }

    /**
     * Vérifie si la position est valide
     * 
     * @param pos position 
     * @param size taille maximum
     * @return vrai si la position est valide sinon faux
     */
    private boolean isValidPosition(int pos, int size) {
        return pos >= 0 && pos < size;
    }

    /**
     * récupère les voisins de moore sans la toricité
     * 
     * @param agent l'agent cible
     * @return les 8 voisins possibles
     */
    private Agent[][] getMooreClassic(Agent agent) {
        Agent[][] moore = new Agent[3][3]; // 8 voisins + agent

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                int gridX = agent.getPosX() + x;
                int gridY = agent.getPosY() + y;

                if (isValidPosition(gridX, cols) && isValidPosition(gridY, rows)) {
                    moore[x + 1][y + 1] = grid[gridX][gridY];
                } else {
                    moore[x + 1][y + 1] = frontier;
                }
            }
        }

        return moore;
    }

    /**
     * Récupère la liste des voisins de moore dans un environement torique
     * 
     * @param agent l'agent cible
     * @return les voisins
     */
    private Agent[][] getMooreToric(Agent agent) {
        Agent[][] moore = new Agent[3][3]; // 8 voisins + agent

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                int gridX = getNewPosToric(agent.getPosX(), x, cols);
                int gridY = getNewPosToric(agent.getPosY(), y, rows);
                moore[x + 1][y + 1] = grid[gridX][gridY];
            }
        }

        return moore;
    }

    /**
     * Getter cols
     * 
     * @return cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * Getter rows
     * 
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Change la position d'un agent
     * 
     * @param agent l'agent cible
     * @param x le pas x
     * @param y le pas y
     */
    public void moveAgent(Agent agent, int x, int y) {
        grid[agent.getPosX()][agent.getPosY()] = null;

        // Normaliser les directions selon la taille
        x = x % cols;
        y = y % rows;

        if (this.isToric) {
            this.moveAgentToric(agent, x, y);
        } else {
            this.moveAgentClassic(agent, x, y);
        }
    }
    
    /**
     * Change la position d'un agent
     * 
     * @param agent cible
     * @param posX position x
     * @param posY position y
     */
    public void moveAgentWithNewPos(Agent agent, int posX, int posY) {
    	grid[agent.getPosX()][agent.getPosY()] = null;
    	agent.setPosX(posX);
        agent.setPosY(posY);
        grid[posX][posY] = agent;
    }

    /**
     * Change la position d'un agent sans environnement torique
     * 
     * @param agent cible
     * @param x pas x
     * @param y pas y
     */
    private void moveAgentClassic(Agent agent, int x, int y) {
        agent.setPosX(agent.getPosX() + x);
        agent.setPosY(agent.getPosY() + y);
        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

    /**
     * Créée la position torique
     * 
     * @param pos la position
     * @param dir la direction
     * @param size la taille maximale
     * @return la position torique
     */
    private int getNewPosToric(int pos, int dir, int size) {
        return (size + pos + dir) % size;
    }

    /**
     * Getter de la position torique x
     * 
     * @param posX position x
     * @return la position torique de x
     */
    public int getToricPosX(int posX) {
        return (cols + posX) % cols;
    }

    /**
     * Getter de la position torique y
     * 
     * @param posY position y
     * @return la position torique de y
     */
    public int getToricPosY(int posY) {
        return (rows + posY) % rows;
    }

    /**
     * Change la position de l'agent dans un environnement torique
     * 
     * @param agent cible
     * @param x pas x
     * @param y pas y 
     */
    private void moveAgentToric(Agent agent, int x, int y) {
        agent.setPosX(getNewPosToric(agent.getPosX(), x, cols));
        agent.setPosY(getNewPosToric(agent.getPosY(), y, rows));

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

	/**
	 * getter de isToric
	 * 
	 * @return isToric
	 */
	public boolean isToric() {
		return isToric;
	}

	/**
	 * setter de isToric
	 * 
	 * @param isToric
	 */
	public void setToric(boolean isToric) {
		this.isToric = isToric;
	}
    
    
    
}
