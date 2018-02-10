package pacman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Labyrinthe {

	private int labyX;
	private int labyY;
	private Element[][] labyrinthe;
	private Random random;

	public Labyrinthe(int x, int y, Random random) {
		this.labyX = (int)Math.ceil(x/2 + 0.5);
		this.labyY = (int)Math.ceil(y/2 + 0.5);
		this.random = random;
	}

	public int getLabyX() {
		return labyX;
	}

	public void setLabyX(int labyX) {
		this.labyX = labyX;
	}

	public int getLabyY() {
		return labyY;
	}

	public void setLabyY(int labyY) {
		this.labyY = labyY;
	}

	public class Element {
		public boolean S;
		public boolean E;
		public int idx;

		public Element(boolean S, boolean E, int idx) {
			this.S = S;
			this.E = E;
			this.idx = idx;
		}
	}
	
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

	public void genererLabyFusion() {
		this.labyrinthe = new Element[this.labyX][this.labyY];
		List<Point> points = new ArrayList<>();
		Map<Integer, List<Point>> map = new HashMap<>();
		
		// initialize labyrinthe avec un indice unique pour chaque
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
		print();
		Collections.shuffle(points);
		
		int nb_cloison = (this.labyX * this.labyY) - 1;
		
		int cpt = 0;
		Point pt = null;
		int colision_ajoutee = 0;
		int[] directions = new int[4];
		while(colision_ajoutee < nb_cloison) {
			
			int nb_dir = 0;
			Element current_elmt = null;
			
			while (nb_dir == 0) {
				pt = points.get(cpt);
				current_elmt = labyrinthe[pt.x][pt.y];
				int idx = current_elmt.idx;
				
				//voisins
				if (pt.x > 0 && this.labyrinthe[pt.x - 1][pt.y].idx != idx) {
					directions[nb_dir] = 0;
					nb_dir++;
				}
				if (pt.x < this.labyX - 1 && this.labyrinthe[pt.x + 1][pt.y].idx != idx) {
					directions[nb_dir] = 1;
					nb_dir++;
				}
				if (pt.y > 0 && this.labyrinthe[pt.x][pt.y - 1].idx != idx) {
					directions[nb_dir] = 2;
					nb_dir++;
				}
				if (pt.y < this.labyY - 1 && this.labyrinthe[pt.x][pt.y + 1].idx != idx) {
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
			
			//mettre à jour les éléments
			switch (dir) {
			case 0:
				elmt = updateElement(pt, -1, 0, current_elmt.idx, map);
				elmt.E = false;
				break;
			case 1:
				current_elmt.E = false;
				elmt = updateElement(pt, 1, 0, current_elmt.idx, map);
				break;
			case 2:
				elmt = updateElement(pt, 0, -1, current_elmt.idx, map);
				elmt.S = false;
				break;
			default:
				current_elmt.S = false;
				elmt = updateElement(pt, 0, 1, current_elmt.idx, map);
			}
			
			colision_ajoutee++;
		}
		print();
	}

	private Element updateElement(Point pt, int x, int y, int idx, Map<Integer, List<Point>> map) {
		Element elmt = this.labyrinthe[pt.x + x][pt.y + y];
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
	
	public Element[][] getLabyrinthe() {
		return this.labyrinthe;
	}
	
}
