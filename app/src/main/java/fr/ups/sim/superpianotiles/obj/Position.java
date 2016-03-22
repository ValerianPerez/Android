package fr.ups.sim.superpianotiles.obj;

import android.graphics.RectF;

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
    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;
    private RectF tileForm;

    public Position() {}

    public Position(int l, int t, int r, int b) {
        left = l;
        top = t;
        right = r;
        bottom = b;
        tileForm = new RectF(l, t, r, b);
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
        this.tileForm.set(left, this.top, this.right, this.bottom);
    }

    public void setTop(int top) {
        this.top = top;
        this.tileForm.set(this.left, top, this.right, this.bottom);
    }

    public void setRight(int right) {
        this.right = right;
        this.tileForm.set(this.left, this.top, right, this.bottom);
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
        this.tileForm.set(this.left, this.top, this.right, bottom);
    }

    public RectF getPositionForm() {
        return tileForm;
    }

    // Fonction utilisée pour vérifier que les coordonnées étaient bien correctes lors du debug.
    public String toString() {
        return "[" + left + ", " + top + ", " + right + ", " + bottom + "]";
    }
}

