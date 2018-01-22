package view;

import agents.Agent;
import misc.Config;
import misc.Environment;

import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    private Environment environment;

    public Grid(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Dimension getPreferredSize() {
        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = columns * Config.getBoxSize();
        int height = rows * Config.getBoxSize();

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

        int width = columns * Config.getBoxSize();
        int height = rows * Config.getBoxSize();

        int wdOfRow = width / (columns);
        int htOfRow = height / (rows);

        if (Config.isGrid()) {
            printGrid(g, width, height, wdOfRow, htOfRow);
        }

        printAgents(g, environment, wdOfRow, htOfRow);
    }

    public void setEnvironment(Environment env) {
        this.environment = env;
    }
}
