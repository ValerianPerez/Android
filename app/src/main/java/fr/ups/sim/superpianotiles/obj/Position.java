package fr.ups.sim.superpianotiles.obj;

/**
 * Created by Nolan on 21/03/2016.
 */

/*
* Classe Position : Définit la position d'une tile sur l'écran par rapport à ses bords
* suivant les axes :
*
* (0,0) + - - - - - - -> (MaxX, 0)
*       |
*       |
*       |
*       |
*       v
* (0, MaxY)             (MaxX, MaxY)
*
* */
public class Position {
    // -    Attributs privés
    private int left;
    private int top;
    private int right;
    private int bottom;

    public Position(int l, int t, int r, int b) {
        left = l;
        top = t;
        right = r;
        bottom = b;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    // Fonction utilisée pour vérifier que les coordonnées étaient bien correctes lors du debug.
    public String toString() {
        return "[" + left + ", " + top + ", " + right + ", " + bottom + "]";
    }
}

