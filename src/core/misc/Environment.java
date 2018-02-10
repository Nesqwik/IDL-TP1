package core.misc;

import java.util.LinkedList;
import java.util.List;

import core.agents.Agent;
import core.agents.FrontierAgent;

public class Environment {

	protected boolean isToric;

    protected List<Agent> agents = new LinkedList<>();
    protected List<Agent> agentsToAdd = new LinkedList<>();
    protected List<Agent> agentsToRemove = new LinkedList<>();

    protected FrontierAgent frontier = new FrontierAgent(this);

    protected Agent[][] grid;
    protected int cols;
    protected int rows;

    public Environment() {

        this.cols = Config.getGridSizeX();
        this.rows = Config.getGridSizeY();

        grid = new Agent[cols][rows];

        this.isToric = Config.isTorus();
    }

    public void addAgent(Agent agent) {
        agentsToAdd.add(agent);

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

    public void removeAgent(Agent agent) {
        agentsToRemove.add(agent);
        agent.setAlive(false);

        grid[agent.getPosX()][agent.getPosY()] = null;
    }

    public void actuallyAddAgents() {
        agents.addAll(agentsToAdd);
        agentsToAdd.clear();
    }

    public void actuallyRemoveAgents() {
        for (Agent a : agentsToRemove) {
            agents.remove(a);
            a.onDestroyed();
        }
        agentsToRemove.clear();
    }

    public Agent getAgent(int gridX, int gridY) {
        if (gridX >= grid.length) return null;
        if (gridY >= grid[0].length) return null;

        return grid[gridX][gridY];
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Agent[][] getMoore(Agent agent) {
        if (isToric) {
            return getMooreToric(agent);
        } else {
            return getMooreClassic(agent);
        }
    }

    /*public Agent[][] getVonNeumann(Agent agent) {
        if (isToric) {
            return getVonNeumannToric(agent);
        } else {
            return getVonNeumannClassic(agent);
        }
    }

    private Agent[][] getVonNeumannClassic(Agent agent) {

    }

    private Agent[][] getVonNeumannToric(Agent agent) {
    }*/

    private boolean isValidPosition(int pos, int size) {
        return pos >= 0 && pos < size;
    }

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

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

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
    
    public void moveAgentWithNewPos(Agent agent, int posX, int posY) {
    	grid[agent.getPosX()][agent.getPosY()] = null;
    	agent.setPosX(posX);
        agent.setPosY(posY);
        grid[posX][posY] = agent;
    }

    private void moveAgentClassic(Agent agent, int x, int y) {
        agent.setPosX(agent.getPosX() + x);
        agent.setPosY(agent.getPosY() + y);
        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

    private int getNewPosToric(int pos, int dir, int size) {
        return (size + pos + dir) % size;
    }

    public int getToricPosX(int posX) {
        return (cols + posX) % cols;
    }

    public int getToricPosY(int posY) {
        return (rows + posY) % rows;
    }

    private void moveAgentToric(Agent agent, int x, int y) {
        agent.setPosX(getNewPosToric(agent.getPosX(), x, cols));
        agent.setPosY(getNewPosToric(agent.getPosY(), y, rows));

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

	public boolean isToric() {
		return isToric;
	}

	public void setToric(boolean isToric) {
		this.isToric = isToric;
	}
    
    
    
}
