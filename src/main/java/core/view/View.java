package core.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.misc.Config;
import core.misc.SMA;

/**
 * Vue
 */
public class View implements Observer {

    /**
     * getter frame
     * 
     * @return frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * setter frame
     * 
     * @param frame
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * frame
     */
    private JFrame frame;

    /**
     * getter de la grille
     * 
     * @return la grille
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * setter grid
     * 
     * @param grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * grille
     */
    private Grid grid;
    /**
     * sma
     */
    private SMA sma;

    /**
     * Constructeur de la vue
     * 
     * @param sma 
     * @param grid
     */
    public View(SMA sma, Grid grid) {
        this.sma = sma;

        frame = new JFrame();
        this.grid = grid;

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
        this.grid.setRefresh(((Boolean) obj).booleanValue());
        if (sma.getTickNumber() % Config.getRefresh() == 0) {
            frame.invalidate();
            frame.repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
