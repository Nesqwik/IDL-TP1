package core.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import pacman.DefenderAgent;
import pacman.EnvironmentPacman;
import pacman.WinnerAgent;

public class Grid extends JPanel {

    private Environment environment;
    private SMA sma;

    private int zoomLevel = 0;
    private boolean showGrid = false;
    private boolean needRefresh = true;


    public Grid(Environment environment, SMA sma) {


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int gridPosX = mouseEvent.getX() / getZoomedBoxSize();
                int gridPosY = mouseEvent.getY() / getZoomedBoxSize();
                Agent a = environment.getAgent(gridPosX, gridPosY);
                if (a == null) return;
                a.setSelected(!a.isSelected());
            }
        });


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyChar() == '-') {
                    zoomLevel -= 1;
                    Grid.this.repaintView();
                }

                if (keyEvent.getKeyChar() == '+') {
                    zoomLevel += 1;
                    Grid.this.repaintView();
                }

                if (keyEvent.getKeyChar() == 'g') {
                    showGrid = !showGrid;
                    Grid.this.repaintView();
                }

                if (keyEvent.getKeyChar() == ' ') {
                    sma.setRunning(!sma.isRunning());
                }

                if (keyEvent.getKeyChar() == 'n') {
                    sma.update();
                }
                
                if (keyEvent.getKeyChar() == 'a') {
                	Config.setSpeedHunter(Config.getSpeedHunter()+1);
                }
                
                if (keyEvent.getKeyChar() == 'z') {
                	Config.setSpeedHunter(Config.getSpeedHunter()-1);
                }
                
                if (keyEvent.getKeyChar() == 'o') {
                	Config.setSpeedAvatar(Config.getSpeedAvatar()-1);
                }
                
                if (keyEvent.getKeyChar() == 'p') {
                	Config.setSpeedAvatar(Config.getSpeedAvatar()+1);
                }
                
                if (keyEvent.getKeyChar() == 'w') {
                	Config.setDelay(Config.getDelay()-1000);
                }
                
                if (keyEvent.getKeyChar() == 'x') {
                	Config.setDelay(Config.getDelay()+1000);
                }
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.showGrid = Config.isGrid();
        this.environment = environment;
        this.sma = sma;
    }


    private int getZoomedBoxSize() {
        return Config.getBoxSize() + zoomLevel;
    }


    public void repaintView() {
        this.needRefresh = false;
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = columns * getZoomedBoxSize();
        int height = rows * getZoomedBoxSize();

        return new Dimension(width, height);
    }


    private void printGrid(Graphics g, int width, int height, int wdOfRow, int htOfRow) {
        int rows = environment.getRows();
        int columns = environment.getCols();

        for (int k = 0; k < rows; k++)
            g.drawLine(0, k * htOfRow, width, k * htOfRow);

        for (int k = 0; k < columns; k++)
            g.drawLine(k * wdOfRow, 0, k * wdOfRow, height);
    }

    private void printAgents(Graphics g, Environment environment, int wdOfRow, int htOfRow) {
        for (Agent agent : environment.getAgents()) {

        	if (agent instanceof DefenderAgent) {
        		DefenderAgent defender = ((DefenderAgent)agent);
        		if (! defender.isActive()){
        			continue;
        		}
        	}
        	
        	if (agent instanceof WinnerAgent) {
        		WinnerAgent winner = ((WinnerAgent)agent);
        		if (! winner.isActive()){
        			continue;
        		}
        	}
        	
            int x = agent.getPosX();
            int y = agent.getPosY();
            Color color = agent.getColor();
            g.setColor(color);

            if (agent.getShape() == Agent.ROUND) {
                g.fillOval(x * wdOfRow, y * htOfRow, wdOfRow, htOfRow);
            } else if (agent.getShape() == Agent.SQUARE) {
                g.fillRect(x * wdOfRow, y * htOfRow, wdOfRow, htOfRow);
            } else if (agent.getShape() == Agent.TRIANGLE) {
                g.fillPolygon(
                        new int[]{x * wdOfRow + wdOfRow / 2, x * wdOfRow, x * wdOfRow + wdOfRow},
                        new int[]{y * htOfRow, y * htOfRow + htOfRow, y * htOfRow + htOfRow},
                        3
                );
            }
        }
    }

    private void printDijkstra(Graphics g) {
        int[][] dijkstra = ((EnvironmentPacman)environment).getDijkstraResult();
        for(int x = 0 ; x < dijkstra.length ; x++) {
            for(int y = 0 ; y < dijkstra[x].length ; y++) {
            	g.setColor(Color.WHITE);
                g.drawString(dijkstra[x][y] + "", x * getZoomedBoxSize(), (y + 1) * getZoomedBoxSize());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (needRefresh) {
            sma.runOnce();
            environment.actuallyAddAgents();
            environment.actuallyRemoveAgents();
            this.needRefresh = false;
        }

        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = columns * getZoomedBoxSize();
        int height = rows * getZoomedBoxSize();

        g.setColor(Config.blue);

        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);


        int wdOfRow = width / (columns);
        int htOfRow = height / (rows);

        if (this.showGrid) {
            printGrid(g, width, height, wdOfRow, htOfRow);
        }

        printAgents(g, environment, wdOfRow, htOfRow);
        printDijkstra(g);
    }

    public void setEnvironment(Environment env) {
        this.environment = env;
    }

    public void setRefresh(Boolean refresh) {
        this.needRefresh = refresh;
    }
}
