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
        return new Dimension(600, 600);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int rows = environment.getRows();
        int columns = environment.getCols();

        int width = getSize().width;
        int height = getSize().height;

        int htOfRow = height / (rows);
        for (int k = 0; k < rows; k++)
            g.drawLine(0, k * htOfRow , width, k * htOfRow );

        int wdOfRow = width / (columns);
        for (int k = 0; k < columns; k++)
            g.drawLine(k*wdOfRow , 0, k*wdOfRow , height);

        for(Agent agent : environment.getAgents()) {
            int x = agent.getPosX();
            int y = agent.getPosY();
            g.fillOval(x * wdOfRow, y * htOfRow, wdOfRow, htOfRow);
        }
    }

    public void setEnvironment(Environment env) {
        this.environment = env;
    }
}
