package core.misc;

import core.agents.Agent;
import core.agents.FrontierAgent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Environment {

    private boolean isToric;

    private List<Agent> agents = new LinkedList<>();
    private List<Agent> agentsToAdd = new LinkedList<>();

    private FrontierAgent frontier = new FrontierAgent(this);

    private Agent[][] grid;
    private int cols;
    private int rows;

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

    public void actuallyAddAgents() {
        agents.addAll(agentsToAdd);
        agentsToAdd.clear();
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

    private void moveAgentClassic(Agent agent, int x, int y) {
        agent.setPosX(agent.getPosX() + x);
        agent.setPosY(agent.getPosY() + y);
        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

    private int getNewPosToric(int pos, int dir, int size) {
        return (size + pos + dir) % size;
    }

    private void moveAgentToric(Agent agent, int x, int y) {
        agent.setPosX(getNewPosToric(agent.getPosX(), x, cols));
        agent.setPosY(getNewPosToric(agent.getPosY(), y, rows));

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }
}