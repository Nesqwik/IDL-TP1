package pacman;

/**
 * Element du labyrinthe
 */
public class Element {
	/**
	 * mur sud
	 */
	public boolean S;
	/**
	 * mur est
	 */
	public boolean E;
	/**
	 * indice
	 */
	public int idx;

	/**
	 * Constructeur de l'�l�ment
	 * 
	 * @param S mur au sud
	 * @param E mur � l'est
	 * @param idx indice
	 */
	public Element(boolean S, boolean E, int idx) {
		this.S = S;
		this.E = E;
		this.idx = idx;
	}
}
