package view;

import agents.Agent;
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

        int width = columns * 3;
        int height = rows * 3;

        return new Dimension(width, height);
    }



    private void printGrid(Graphics g) {
        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = columns * 3;
        int height = rows * 3;

        int htOfRow = height / (rows);
        for (int k = 0; k < rows; k++)
            g.drawLine(0, k * htOfRow , width, k * htOfRow );

        int wdOfRow = width / (columns);
        for (int k = 0; k < columns; k++)
            g.drawLine(k*wdOfRow , 0, k*wdOfRow , height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = columns * 3;
        int height = rows * 3;

        int htOfRow = height / (rows);
        int wdOfRow = width / (columns);

        //printGrid(g);

        for(Agent agent : environment.getAgents()) {
            int x = agent.getPosX();
            int y = agent.getPosY();
            Color color = agent.getColor();
            g.setColor(color);
            g.fillOval(x * wdOfRow, y * htOfRow, wdOfRow, htOfRow);
        }
    }

    public void setEnvironment(Environment env) {
        this.environment = env;
    }
}
