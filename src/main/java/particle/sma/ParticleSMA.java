package particle.sma;

import java.awt.Point;
import java.util.List;

import core.misc.Config;
import core.misc.Environment;
import core.misc.SMA;
import core.view.Grid;
import core.view.View;
import particle.ParticleAgent;


/**
 * SMA Particule
 */
public class ParticleSMA extends SMA {

    /**
     * Constructeur de la particule SMA
     * 
     * @param env
     */
    public ParticleSMA(Environment env) {
        super(env);
    }

    /**
     * Lancer les particules
     * 
     * @param args
     */
    public static void main(String[] args) {
        Config.load();

        Environment environment = new Environment();
        ParticleSMA SMA = new ParticleSMA(environment);
        Grid grid = new Grid(environment, SMA);

        new View(SMA, grid);
    }

    @Override
    protected void addAgents(List<Point> availableCoord) {
        for (int i = 0; i < Config.getNbParticles(); i++) {
            ParticleAgent agent = createParticleAgent(this.environment, availableCoord.get(i));
            this.environment.addAgent(agent);
        }
    }

    /**
     * Cr�e un agent particule
     * 
     * @param env
     * @param coord
     * @return l'agent Particule
     */
    private ParticleAgent createParticleAgent(Environment env, Point coord) {
        int pasX = random.nextInt(3) - 1;
        int pasY = random.nextInt(3) - 1;

        try {
            ParticleAgent agent = (ParticleAgent) Class.forName(Config.getParticleType()).getConstructor(Environment.class, Integer.class, Integer.class, Integer.class, Integer.class).newInstance(env,coord.x, coord.y, pasX, pasY);
            return agent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
