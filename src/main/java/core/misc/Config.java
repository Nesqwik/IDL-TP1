package core.misc;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * R�cup�re les configurations
 */
public class Config {
    /**
     * Vrai si c'est torique
     */
    static private boolean torus;
    /**
     * largeur de la grille
     */
    static private int gridSizeX;
    /**
     * hauteur de la grille
     */
    static private int gridSizeY;
    /**
     * largeur de l'�cran
     */
    static private int canvasSizeX;
    /**
     * hauteur de l'�cran
     */
    static private int canvasSizeY;
    /**
     * taille d'une case
     */
    static private int boxSize;
    /**
     * d�lais
     */
    static private int delay;
    /**
     * m�thode de s�quencement
     */
    static private String scheduling;
    /**
     * nombre de tick
     */
    static private int nbTicks;
    /**
     * affichage de la grille
     */
    static private boolean grid;
    /**
     * affichage des traces
     */
    static private boolean trace;
    /**
     * seed
     */
    static private long seed;
    /**
     * refresh
     */
    static private int refresh;
    /**
     * nombre de particules
     */
    static private int nbParticles;
    /**
     * type de particule
     */
    static private String particleType;

    /**
     * nombre de poissons
     */
    static private int nbFish;
    /**
     * nombre de requins
     */
    static private int nbShark;
    /**
     * temps de reproduction des poissons
     */
    static private int fishBreedTime;
    /**
     * temps de reproduction des requins
     */
    static private int sharkBreedTime;
    /**
     * temps avant de mourir de faim
     */
    static private int sharkFeedTime;
    /**
     * type de poisson
     */
    static private String fishType;
    /**
     * type de requin
     */
    static private String sharkType;

    /**
     * couleur bleue
     */
    static public Color blue = new Color(106, 149, 179);
    /**
     * couleur jaune
     */
    static public Color yellow = new Color(255, 251, 82);
    /**
     * couleur verte
     */
    static public Color green = new Color(52, 164, 14);
    /**
     * couleur rouge
     */
    static public Color red = new Color(179, 37, 27);
    /**
     * couleur rose
     */
    static public Color pink = new Color(255, 107, 237);

    /**
     * rapidit� des chasseurs
     */
    static private int speedHunter;
    /**
     * rapidit� de l'avatar
     */
    static private int speedAvatar;
    /**
     * temps total du defender d'une apparition et disparition
     */
    static private int lifeDefender;
    /**
     * temps d'affichage du defender
     */
    static private int timeAppearDefender;
    /**
     * temps d'invisibilit� du defender
     */
    static private int invinsibleTime;
    /**
     * g�n�ration du labyrinthe
     */
    static private boolean generateLabyrinthe;
    /**
     * affichage de dikstra
     */
    static private boolean printDijkstra;

    /**
     * r�cup�re toutes les propri�t�s
     */
    static public void load() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");
            prop.load(input);

            torus = Boolean.parseBoolean(prop.getProperty("torus"));
            gridSizeX = Integer.parseInt(prop.getProperty("gridSizeX"));
            gridSizeY = Integer.parseInt(prop.getProperty("gridSizeY"));
            canvasSizeX = Integer.parseInt(prop.getProperty("canvasSizeX"));
            canvasSizeY = Integer.parseInt(prop.getProperty("canvasSizeY"));
            boxSize = Integer.parseInt(prop.getProperty("boxSize"));
            delay = Integer.parseInt(prop.getProperty("delay"));
            scheduling = prop.getProperty("scheduling");
            nbTicks = Integer.parseInt(prop.getProperty("nbTicks"));
            grid = Boolean.parseBoolean(prop.getProperty("grid"));
            trace = Boolean.parseBoolean(prop.getProperty("trace"));
            seed = Long.parseLong(prop.getProperty("seed"));
            refresh = Integer.parseInt(prop.getProperty("refresh"));
            nbParticles = Integer.parseInt(prop.getProperty("nbParticles"));
            particleType = prop.getProperty("particleType");

            nbFish = Integer.parseInt(prop.getProperty("nbFish"));
            nbShark = Integer.parseInt(prop.getProperty("nbShark"));

            fishBreedTime = Integer.parseInt(prop.getProperty("fishBreedTime"));
            sharkBreedTime = Integer.parseInt(prop.getProperty("sharkBreedTime"));

            sharkFeedTime = Integer.parseInt(prop.getProperty("sharkFeedTime"));

            fishType = prop.getProperty("fishType");
            sharkType = prop.getProperty("sharkType");


            speedAvatar = Integer.parseInt(prop.getProperty("speedAvatar"));
            speedHunter = Integer.parseInt(prop.getProperty("speedHunter"));
            lifeDefender = Integer.parseInt(prop.getProperty("lifeDefender"));
            timeAppearDefender = Integer.parseInt(prop.getProperty("timeAppearDefender"));
            invinsibleTime = Integer.parseInt(prop.getProperty("invinsibleTime"));
            generateLabyrinthe = Boolean.parseBoolean(prop.getProperty("generateLabyrinthe"));
            printDijkstra = Boolean.parseBoolean(prop.getProperty("printDijkstra"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * getter de isTorus
     * 
     * @return isTorus
     */
    static public boolean isTorus() {
        return torus;
    }

    /**
     * setter de isTorus
     * 
     * @param t vrai si c'est torique sinon faux
     */
    static public void setTorus(boolean t) {
        torus = t;
    }

    /**
     * getter de gridSizeX
     * 
     * @return gridSizeX
     */
    static public int getGridSizeX() {
        return gridSizeX;
    }

    /**
     * setter de gridSizeX
     * 
     * @param gsx la taille de la grille X
     */
    static public void setGridSizeX(int gsx) {
        gridSizeX = gsx;
    }

    /**
     * getter de gridSizeY
     * 
     * @return la taille de la grille Y
     */
    static public int getGridSizeY() {
        return gridSizeY;
    }

    /**
     * setter de gridSizeY
     * 
     * @param gsy la taille de la grille Y
     */
    static public void setGridSizeY(int gsy) {
        gridSizeY = gsy;
    }

    /**
     * getter de canvasSizeX
     * 
     * @return canvasSizeX
     */
    static public int getCanvasSizeX() {
        return canvasSizeX;
    }

    /**
     * setter de canvasSizeX
     * 
     * @param csx la taille de la fenetre X
     */
    static public void setCanvasSizeX(int csx) {
        canvasSizeX = csx;
    }

    /**
     * getter de canvasSizeY
     * 
     * @return canvasSizeY
     */
    static public int getCanvasSizeY() {
        return canvasSizeY;
    }

    /**
     * setter de canvasSizeY
     * 
     * @param csy la hauteur de la fen�tre y
     */
    static public void setCanvasSizeY(int csy) {
        canvasSizeY = csy;
    }

    /**
     * getter boxSize
     * 
     * @return boxSize
     */
    static public int getBoxSize() {
        return boxSize;
    }

    /**
     * setter de boxSize
     * 
     * @param bs la taille d'une case
     */
    static public void setBoxSize(int bs) {
        boxSize = bs;
    }

    /**
     * getter du delay
     * 
     * @return le delay
     */
    static public int getDelay() {
        return delay;
    }

    /**
     * setter du delay
     * 
     * @param d le d�lais
     */
    static public void setDelay(int d) {
        delay = d;
    }

    /**
     * getter du scheduling
     * 
     * @return le scheduling
     */
    static public String getScheduling() {
        return scheduling;
    }

    /**
     * setter du scheduling
     * 
     * @param s le scheduling
     */
    static public void setScheduling(String s) {
        scheduling = s;
    }

    /**
     * getter du nombre de tick
     * 
     * @return le nombre de tick
     */
    static public int getNbTicks() {
        return nbTicks;
    }

    /**
     * setter du nombre de tick
     * 
     * @param nt nombre de tick
     */
    static public void setNbTicks(int nt) {
        nbTicks = nt;
    }

    /**
     * getter isGrid
     * 
     * @return isGrid
     */
    static public boolean isGrid() {
        return grid;
    }

    /**
     * setter de isGrid
     * 
     * @param g isGrid
     */
    static public void setGrid(boolean g) {
        grid = g;
    }

    /**
     * getter de isTrace
     * 
     * @return isTrace
     */
    static public boolean isTrace() {
        return trace;
    }

    /**
     * setter de trace
     * 
     * @param tr trace
     */
    static public void setTrace(boolean tr) {
        trace = tr;
    }

    /**
     * getter du seed
     * 
     * @return seed
     */
    static public long getSeed() {
        return seed;
    }

    /**
     * setter du seed
     * 
     * @param s seed
     */
    static public void setSeed(long s) {
        seed = s;
    }

    /**
     * getter de refresh
     * 
     * @return refresh
     */
    static public int getRefresh() {
        return refresh;
    }

    /**
     * setter de refresh
     * 
     * @param re refresh
     */
    static public void setRefresh(int re) {
        refresh = re;
    }

    /**
     * getter du nombre de particules
     * 
     * @return le nombre de particules
     */
    static public int getNbParticles() {
        return nbParticles;
    }

    /**
     * setter du nombre de particules
     * 
     * @param np le nombre de particule
     */
    static public void setNbParticles(int np) {
        nbParticles = np;
    }

    /**
     * getter de particle type
     * 
     * @return particleType
     */
    public static String getParticleType() {
        return particleType;
    }

    /**
     * setter de particleType
     * 
     * @param particleType 
     */
    public static void setParticleType(String particleType) {
        Config.particleType = particleType;
    }

    /**
     * getter du nombre de poissons
     * 
     * @return nombre de poisson
     */
    public static int getNbFish() {
        return nbFish;
    }

    /**
     * setter du nombre de poisson
     * 
     * @param nbFish nombre de poisson
     */
    public static void setNbFish(int nbFish) {
        Config.nbFish = nbFish;
    }

    /**
     * getter de nbShark
     * 
     * @return le nombre de shark
     */
    public static int getNbShark() {
        return nbShark;
    }

    /**
     * setter du nombre de shark
     * 
     * @param nbShark le nombre de requins
     */
    public static void setNbShark(int nbShark) {
        Config.nbShark = nbShark;
    }

    /**
     * getter de FishBreedTime
     * 
     * @return fishBreedTime
     */
    public static int getFishBreedTime() {
        return fishBreedTime;
    }

    /**
     * setter fishBreedTime
     * 
     * @param fishBreedTime
     */
    public static void setFishBreedTime(int fishBreedTime) {
        Config.fishBreedTime = fishBreedTime;
    }

    /**
     * getter sharkBreedTime
     * 
     * @return sharkBreedTime
     */
    public static int getSharkBreedTime() {
        return sharkBreedTime;
    }

    /**
     * setter sharkBreedTime
     * 
     * @param sharkBreedTime
     */
    public static void setSharkBreedTime(int sharkBreedTime) {
        Config.sharkBreedTime = sharkBreedTime;
    }

    /**
     * getter sharkFeedTime
     * 
     * @return sharkFeedTime
     */
    public static int getSharkFeedTime() {
        return sharkFeedTime;
    }

    /**
     * setter sharkFeedTime
     * 
     * @param sharkFeedTime
     */
    public static void setSharkFeedTime(int sharkFeedTime) {
        Config.sharkFeedTime = sharkFeedTime;
    }

    /**
     * getter de fishType
     * 
     * @return fishType
     */
    public static String getFishType() {
        return fishType;
    }

    /**
     * setter de fishType
     * 
     * @param fishType
     */
    public static void setFishType(String fishType) {
        Config.fishType = fishType;
    }

    /**
     * getter sharkType
     * 
     * @return sharktype
     */
    public static String getSharkType() {
        return sharkType;
    }

    /**
     * setter de sharkType
     * 
     * @param sharkType
     */
    public static void setSharkType(String sharkType) {
        Config.sharkType = sharkType;
    }

    /**
     * getter speedHunter
     * 
     * @return speedHunter
     */
    public static int getSpeedHunter() {
        return speedHunter;
    }

    /**
     * setter speedHunter
     * 
     * @param speedHunter
     */
    public static void setSpeedHunter(int speedHunter) {
        Config.speedHunter = Math.max(1, speedHunter);
    }

    /**
     * getter speed avatar
     * 
     * @return speedAvatar
     */
    public static int getSpeedAvatar() {
        return speedAvatar;
    }

    /**
     * setter speedAvatar
     * 
     * @param speedAvatar
     */
    public static void setSpeedAvatar(int speedAvatar) {
        Config.speedAvatar = Math.max(1, speedAvatar);
    }

	/**
	 * getter lifeDefender
	 * 
	 * @return lifeDefender
	 */
	public static int getLifeDefender() {
		return lifeDefender;
	}

	/**
	 * setter lifeDefender
	 * 
	 * @param lifeDefender
	 */
	public static void setLifeDefender(int lifeDefender) {
		Config.lifeDefender = lifeDefender;
	}

	/**
	 * getter TimeAppear
	 * 
	 * @return timeAppear
	 */
	public static int getTimeAppear() {
		return timeAppearDefender;
	}

	/**
	 * setter de timeAppear
	 * 
	 * @param timeAppear
	 */
	public static void setTimeAppear(int timeAppear) {
		Config.timeAppearDefender = timeAppear;
	}

	/**
	 * getter invisibleTime
	 * 
	 * @return invisibleTime
	 */
	public static int getInvinsibleTime() {
		return invinsibleTime;
	}

	/**
	 * setter invisibleTime
	 * 
	 * @param invinsibleTime
	 */
	public static void setInvinsibleTime(int invinsibleTime) {
		Config.invinsibleTime = invinsibleTime;
	}

	/**
	 * getter generateLabyrinthe
	 * 
	 * @return generateLabyrinthe
	 */
	public static boolean isGenerateLabyrinthe() {
		return generateLabyrinthe;
	}

	/**
	 * setter generateLabyrinthe
	 * 
	 * @param generateLabyrinthe
	 */
	public static void setGenerateLabyrinthe(boolean generateLabyrinthe) {
		Config.generateLabyrinthe = generateLabyrinthe;
	}

	/**
	 * getter print dijkstra
	 * 
	 * @return printDijkstra
	 */
	public static boolean isPrintDijkstra() {
		return printDijkstra;
	}

	/**
	 * setter de printDijkstra
	 * 
	 * @param printDijkstra
	 */
	public static void setPrintDijkstra(boolean printDijkstra) {
		Config.printDijkstra = printDijkstra;
	}
    
	
    
}
