package core.misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import core.agents.Agent;
import core.agents.FrontierAgent;
import pacman.WallAgent;

public class Environment {

    private boolean isToric;

    protected List<Agent> agents = new LinkedList<>();
    private List<Agent> agentsToAdd = new LinkedList<>();
    protected List<Agent> agentsToRemove = new LinkedList<>();
    protected boolean startGame;
    protected boolean endGame;
    protected boolean pacmanInvinsible;

    private FrontierAgent frontier = new FrontierAgent(this);

    private int[][] dijkstraResult;
    private Agent[][] grid;
    private int cols;
    private int rows;

    public Environment() {

        this.cols = Config.getGridSizeX();
        this.rows = Config.getGridSizeY();
        this.startGame = false;
        this.endGame = false;

        grid = new Agent[cols][rows];
        dijkstraResult = new int[cols][rows];

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

    public void dijkstra(Agent agent) {
        int gridX = agent.getPosX();
        int gridY = agent.getPosY();

        for (int x = 0; x < dijkstraResult.length; x++) {
            for (int y = 0; y < dijkstraResult[x].length; y++) {
                dijkstraResult[x][y] = -2;
            }
        }


        dijkstraRecursive(Arrays.asList(new Point(gridX, gridY)), 0);
    }

    private boolean markValue(int x, int y, int value) {
        if (!(grid[x][y] instanceof WallAgent)) {
            dijkstraResult[x][y] = value;
            return true;
        } else {
            dijkstraResult[x][y] = -1;
            return false;
        }
    }

    public void dijkstraRecursive(List<Point> voisins, int value) {
        List<Point> vVoisins = new ArrayList<>();
        for (Point p : voisins) {
            if(!markValue(p.x, p.y, value) && value != 0) {
                continue;
            }

            if (p.x > 0 && dijkstraResult[p.x - 1][p.y] == -2) {
                vVoisins.add(new Point(p.x - 1, p.y));
            }

            if (p.x < dijkstraResult.length - 1 && dijkstraResult[p.x + 1][p.y] == -2) {
                vVoisins.add(new Point(p.x + 1, p.y));
            }

            if (p.y > 0 && dijkstraResult[p.x][p.y - 1] == -2) {
                vVoisins.add(new Point(p.x, p.y - 1));
            }

            if (p.y < dijkstraResult[p.x].length - 1 && dijkstraResult[p.x][p.y + 1] == -2) {
                vVoisins.add(new Point(p.x, p.y + 1));
            }
        }

        if(vVoisins.size() != 0) {
            dijkstraRecursive(vVoisins, value + 1);
        }
    }

    public int[][] getDijkstraResult() {
        return dijkstraResult;
    }

    public int getDijkstraPos(int x, int y) {
        if (x < 0 || y < 0 || x > dijkstraResult.length - 1 || y > dijkstraResult[0].length - 1) {
            return -1;
        }
        return dijkstraResult[x][y];
    }
    
    public void startGame() {
    	this.startGame = true;
    }
    
    public void endGame() {
    	this.endGame = true;
    }
    
    public boolean isStartedGame() {
    	return this.startGame;
    }
    
    public boolean isEndedGame() {
    	return this.endGame;
    }
 
    public void setPacmanInvinsible(boolean invinsible) {
    	this.pacmanInvinsible = invinsible;
    }
    
    public boolean isPacmanInvinsible(){
    	return this.pacmanInvinsible;
    }
    
}
