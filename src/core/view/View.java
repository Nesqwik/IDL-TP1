package core.view;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private JFrame frame;
    private Grid grid;
    private SMA sma;

    public View(SMA sma) {
        this.sma = sma;
        sma.populate();

        frame = new JFrame();
        grid = new Grid(sma.getEnvironment(), sma);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(grid), BorderLayout.CENTER);

        JPanel tuto = new JPanel();
        tuto.add(new JLabel("SPACE : pause/play -- N : next step -- G : show/hide grid -- Click : select agent -- +/- : zoom/unzoom"));
        frame.add(tuto, BorderLayout.NORTH);

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
            frame.invalidate();
            frame.repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
