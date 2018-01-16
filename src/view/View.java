package view;

import misc.Environment;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private JFrame frame;
    private Grid grid;

    public View(Environment env) {
        frame = new JFrame();
        grid = new Grid(env);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(grid), BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object obj) {
        Environment env = (Environment) obj;
        grid.setEnvironment(env);
        grid.invalidate();
        grid.repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
