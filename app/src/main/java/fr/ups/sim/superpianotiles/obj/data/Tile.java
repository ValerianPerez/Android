package fr.ups.sim.superpianotiles.obj.data;

import android.graphics.Paint;

/**
 * Created by Nolan on 21/03/2016.
 */
public class Tile {
    // -    Attributs privés
    private int colorTile;
    private int colorText;
    private String text;                // Texte affiché sur la tile
    private float textSize = 40;
    private Position positionTile;

    // +    Attributs publics
    public Paint pText = new Paint();
    public Paint pTile = new Paint();

    public Tile(int colorTile, int colorText, String text, float textSize, Position positionTile) {
        this.colorTile = colorTile;
        this.colorText = colorText;
        this.text = text;
        this.textSize = textSize;
        this.positionTile = positionTile;

        pText.setTextSize(textSize);
        pText.setColor(colorText);
        pTile.setColor(colorTile);
    }

    public int getColorTile() {
        return colorTile;
    }

    public int getColorText() {
        return colorText;
    }

    public String getText() {
        return text;
    }

    public float getTextSize(){
        return textSize;
    }

    public Position getPositionTile() {
        return positionTile;
    }

    public void setPositionTile(Position positionTile) {
        this.positionTile = positionTile;
    }

    /*
    * isTouched renvoie :
    *   true si le point désigné par les coordonnées (x,y) appartient à la tile
    *   false sinon
    * */
    public boolean isTouched(float x, float y) {
        return !(x < this.positionTile.getLeft()
                || y < this.positionTile.getTop()
                || x > this.positionTile.getRight()
                || y > this.positionTile.getBottom());
    }

    public void setColorTile(int c) {
        colorTile = c;
        pTile.setColor(c);
    }

    public void setColorText(int c) {
        colorText = c;
        pText.setColor(c);
    }
}
