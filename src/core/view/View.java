package core.view;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    private JFrame frame;

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    private Grid grid;
    private SMA sma;

    public View(SMA sma) {
        this.sma = sma;

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

        sma.populate(this);

        sma.addObserver(this);
        sma.run();
    }

    @Override
    public void update(Observable observable, Object obj) {
        grid.setRefresh(((Boolean) obj).booleanValue());
        if (sma.getTickNumber() % Config.getRefresh() == 0) {
            frame.invalidate();
            frame.repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
