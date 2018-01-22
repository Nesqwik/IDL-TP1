package view;

import agents.Agent;
import misc.Config;
import misc.Environment;
import misc.SMA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Grid extends JPanel {

    private Environment environment;
    private SMA sma;

    private float zoomLevel = 1f;
    private boolean showGrid = false;

    public Grid(Environment environment, SMA sma) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int gridPosX = mouseEvent.getX() / getZoomedBoxSize();
                int gridPosY = mouseEvent.getY() / getZoomedBoxSize();
                Agent a = environment.getAgent(gridPosX, gridPosY);
                if(a == null)return;
                a.setSelected(!a.isSelected());
                Grid.this.getParent().revalidate();
                Grid.this.getParent().repaint();
            }
        });


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyChar() == '-') {
                    zoomLevel -= 0.1;
                    Grid.this.getParent().revalidate();
                    Grid.this.getParent().repaint();
                }

                if(keyEvent.getKeyChar() == '+') {
                    zoomLevel += 0.1;
                    Grid.this.getParent().revalidate();
                    Grid.this.getParent().repaint();
                }

                if(keyEvent.getKeyChar() == 'g') {
                    showGrid = !showGrid;
                    Grid.this.getParent().revalidate();
                    Grid.this.getParent().repaint();
                }

                if(keyEvent.getKeyChar() == ' ') {
                    sma.setRunning(!sma.isRunning());
                }

                if(keyEvent.getKeyChar() == 'n') {
                    sma.runOnce();
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
        return (int) (Config.getBoxSize() * zoomLevel);
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
            int x = agent.getPosX();
            int y = agent.getPosY();
            Color color = agent.getColor();
            g.setColor(color);
            g.fillOval(x * wdOfRow, y * htOfRow, wdOfRow, htOfRow);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = columns * getZoomedBoxSize();
        int height = rows * getZoomedBoxSize();

        int wdOfRow = width / (columns);
        int htOfRow = height / (rows);

        if (this.showGrid) {
            printGrid(g, width, height, wdOfRow, htOfRow);
        }

        printAgents(g, environment, wdOfRow, htOfRow);
    }

    public void setEnvironment(Environment env) {
        this.environment = env;
    }
}
