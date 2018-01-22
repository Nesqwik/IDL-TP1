package view;

import misc.Config;
import misc.Environment;
import misc.SMA;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private JFrame frame;
    private Grid grid;
    private SMA sma;

    public View() {
        Config.load();
        Environment env = new Environment(Config.getGridSizeX(), Config.getGridSizeY(), Config.isTorus());
        this.sma = new SMA(env);
        sma.populate(env, Config.getNbParticles());

        frame = new JFrame();
        grid = new Grid(env, sma);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(grid), BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(Config.getCanvasSizeX(), Config.getCanvasSizeY()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        sma.addObserver(this);
        sma.run();
    }

    @Override
    public void update(Observable observable, Object obj) {

        if (sma.getTickNumber() % Config.getRefresh() == 0) {
            Environment env = (Environment) obj;
            grid.setEnvironment(env);
            frame.invalidate();
            frame.repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
