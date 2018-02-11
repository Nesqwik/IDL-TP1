package pacman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import core.misc.Config;

/**
 * Labyrinthe
 */
public class Labyrinthe {

	/**
	 * largeur du labyrinthe
	 */
	private int labyX;
	/**
	 * hauteur du labyrinthe
	 */
	private int labyY;
	/**
	 * labyrinthe
	 */
	private Element[][] labyrinthe;
	/**
	 * random
	 */
	private Random random;
	/**
	 * toricit�
	 */
	private boolean torus;

	/**
	 * Constructeur du labyrinthe
	 * 
	 * @param x
	 * @param y
	 * @param random
	 */
	public Labyrinthe(int x, int y, Random random) {
		this.labyX = (int)Math.ceil(x/2);
		this.labyY = (int)Math.ceil(y/2);
		this.random = random;
		this.torus = Config.isTorus();
	}

	/**
	 * getter labyX
	 * 
	 * @return labyX
	 */
	public int getLabyX() {
		return labyX;
	}

	/**
	 * setter labyX
	 * 
	 * @param labyX
	 */
	public void setLabyX(int labyX) {
		this.labyX = labyX;
	}

	/**
	 * getter LabyY
	 * 
	 * @return labyY
	 */
	public int getLabyY() {
		return labyY;
	}

	/**
	 * setter LabyY
	 * 
	 * @param labyY
	 */
	public void setLabyY(int labyY) {
		this.labyY = labyY;
	}
	
	/**
	 * imprimer le labyrinthe
	 */
	public void print(){
		for (int y = 0; y < this.labyY; y++) {
			String ligne = "";
			for (int x = 0; x < this.labyX; x++) {
				System.out.print(this.labyrinthe[x][y].idx);
				if (this.labyrinthe[x][y].E) {
					System.out.print("|");
				} else {
					System.out.print(" ");
				}
				if (this.labyrinthe[x][y].S) {
					ligne += "---";
				} else {
					ligne += "  ";
				}
			}
			System.out.println("");
			System.out.println(ligne);
		}
	}

	/**
	 * g�n�rer le labyrinthe avec la m�thode de fusion
	 */
	public void genererLabyFusion() {
		this.labyrinthe = new Element[this.labyX][this.labyY];
		List<Point> points = new ArrayList<>();
		Map<Integer, List<Point>> map = new HashMap<>();
		
		// initialize labyrinthe avec un indice unique pour chaque case
		for (int x = 0; x < this.labyX; x++) {
			for (int y = 0; y < this.labyY; y++) {
				this.labyrinthe[x][y] = new Element(true, true, y * this.labyX + x);
				Point p = new Point(x, y);
				points.add(p);
				List<Point> lst = new ArrayList<>();
				lst.add(p);
				map.put(y * this.labyX + x, lst);
			}
		}

		Collections.shuffle(points, this.random);
		
		int nb_cloison = (this.labyX * this.labyY) - 1;
		
		int cpt = 0;
		Point pt = null;
		int colision_ajoutee = 0;
		int[] directions = new int[4];
		// ajouter une cloisin tant que le nombre maximum de cloison n'a pas �t� atteint
		while(colision_ajoutee < nb_cloison) {
			
			int nb_dir = 0;
			Element current_elmt = null;
			int xMoinsUn = 0;
			int xPlusUn = 0;
			int yMoinsUn = 0;
			int yPlusUn = 0;
			
			// chercher des voisins
			while (nb_dir == 0) {
				pt = points.get(cpt);
				current_elmt = labyrinthe[pt.x][pt.y];
				int idx = current_elmt.idx;
				xMoinsUn = torus ? (pt.x - 1 + this.labyX) % this.labyX : pt.x - 1;
				xPlusUn = torus ? (pt.x + 1) % this.labyX : pt.x + 1;
				yMoinsUn = torus ? (pt.y - 1 + this.labyY) % this.labyY : pt.y - 1;
				yPlusUn = torus ? (pt.y + 1) %this.labyY : pt.y + 1;
				
				
				//voisins
				if (xMoinsUn >= 0 && this.labyrinthe[xMoinsUn][pt.y].idx != idx) {
					directions[nb_dir] = 0;
					nb_dir++;
				}
				if (xPlusUn < this.labyX && this.labyrinthe[xPlusUn][pt.y].idx != idx) {
					directions[nb_dir] = 1;
					nb_dir++;
				}
				if (yMoinsUn >= 0 && this.labyrinthe[pt.x][yMoinsUn].idx != idx) {
					directions[nb_dir] = 2;
					nb_dir++;
				}
				if (yPlusUn < this.labyY && this.labyrinthe[pt.x][yPlusUn].idx != idx) {
					directions[nb_dir] = 3;
					nb_dir++;
				}
				
				cpt++;
				cpt %= (this.labyX * this.labyY) - 1;
			}
			
			//choisir direction aleatoire
			int rand = this.random.nextInt(nb_dir);
			int dir = directions[rand];
			Element elmt; 
			
			//mettre � jour les �l�ments
			switch (dir) {
			case 0:
				elmt = updateElement(xMoinsUn, pt.y, current_elmt.idx, map);
				elmt.E = false;
				break;
			case 1:
				current_elmt.E = false;
				elmt = updateElement(xPlusUn, pt.y, current_elmt.idx, map);
				break;
			case 2:
				elmt = updateElement(pt.x, yMoinsUn, current_elmt.idx, map);
				elmt.S = false;
				break;
			default:
				current_elmt.S = false;
				elmt = updateElement(pt.x, yPlusUn, current_elmt.idx, map);
			}
			
			colision_ajoutee++;
		}

	}

	/**
	 * Met � jour l'�l�ment
	 * 
	 * @param x position
	 * @param y position
	 * @param idx indice
	 * @param map map
	 * @return l'�l�ment modifi�
	 */
	private Element updateElement(int x, int y, int idx, Map<Integer, List<Point>> map) {
		Element elmt = this.labyrinthe[x][y];
		List<Point> new_points = new ArrayList<>();
		if (map.containsKey(idx)) {
			new_points = map.get(idx);
		} 

		if (map.containsKey(elmt.idx)) {
			List<Point> points = map.get(elmt.idx);
			for (Point p: points){
				this.labyrinthe[p.x][p.y].idx = idx;
				new_points.add(p);
			}
			map.remove(elmt.idx);
		} 
		
		elmt.idx = idx;
		map.put(idx, new_points);
		return elmt;
	}
	
	/**
	 * getter labyrinthe
	 * 
	 * @return un tableau a deux dimension d'Element
	 */
	public Element[][] getLabyrinthe() {
		return this.labyrinthe;
	}
	
}
