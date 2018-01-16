package misc;

import agents.Agent;
import agents.FrontierAgent;

import java.util.LinkedList;
import java.util.List;

public class Environment {

    private boolean isToric;

    private List<Agent> agents = new LinkedList<>();
    private FrontierAgent frontier = new FrontierAgent(this);

    private Agent[][] grid;
    private int cols;
    private int rows;

    public Environment(int cols, int rows, boolean isToric) {
        grid = new Agent[cols][rows];

        this.cols = cols;
        this.rows = rows;
        this.isToric = isToric;
    }

    public void addAgent(Agent agent) {
        agents.add(agent);

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Agent[][] getMoore(Agent agent) {
        if(isToric) {
            return getMooreToric(agent);
        } else {
            return getMooreClassic(agent);
        }
    }

    private Agent[][] getMooreClassic(Agent agent) {
        Agent[][] moore = new Agent[3][3]; // 8 voisins + agent

        // TODO

        return moore;
    }

    private Agent[][] getMooreToric(Agent agent) {
        Agent[][] moore = new Agent[3][3]; // 8 voisins + agent

        for(int x = -1 ; x < 2 ; x++) {
            for(int y = -1 ; y < 2 ; y++) {
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

        if(this.isToric) {
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
        if(pos + dir > size - 1) {
            pos = (pos + dir) % size;
        } else if(pos + dir < 0) {
            pos = size + pos + dir;
        } else {
            pos = pos + dir;
        }
        return pos;
    }

    private void moveAgentToric(Agent agent, int x, int y) {
        agent.setPosX(getNewPosToric(agent.getPosX(), x, cols));
        agent.setPosY(getNewPosToric(agent.getPosY(), y, rows));

        grid[agent.getPosX()][agent.getPosY()] = agent;
    }

}
