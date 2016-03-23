package fr.ups.sim.superpianotiles.obj;

import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Valérian on 21/03/2016.
 */
public class Track {
    private int tilesCount;
    private List<Tile> tiles;
    private int contentWidth;
    private int contentHeight;


    public Track(int tilesCount, int contentWidth, int contentHeight){

        this.tilesCount = tilesCount;
        tiles = new LinkedList<>();
        this.contentHeight = contentHeight;
        this.contentWidth = contentWidth;

        for (int i = 0; i < tilesCount; i++){

            //Dépendent du random du quarterPosition
            int rnd = QuarterPosition.randomPosition().ordinal();
            int left = contentWidth * rnd/4;
            int right = contentWidth * (rnd+1)/4;

            //Dépendent de leur position dans la liste
            int top = contentHeight * 1/4;
            int bottom = contentHeight * 2/4;

            tiles.add(new Tile(Color.RED, Color.WHITE, "1", 40, new Position(left, top, right, bottom), new RectF(left, top, right, bottom)));
        }
    }

    public int getTilesCount() {
        return tilesCount;
    }

    /**
     * Définit la position des tuiles pour leur affichage.
     *
     * @return La liste des tuiles à afficher.
     */
    public ArrayList<Tile> tilesDisplay(){
        ArrayList<Tile> tilesToDisplay = new ArrayList<>(4);

        //La première tuile de la liste est la tuile la plus basse à l'écran
        for (int i = 0; i < 4; i++){
            //Impossible de faire un poll...
            Tile t = tiles.get(0);

            //Dépendent du random du quarterPosition
            int left = t.getPositionTile().getLeft();
            int right = t.getPositionTile().getRight();

            //Dépendent de leur position dans la liste
            int top = contentHeight * i/4;
            int bottom = contentHeight * (i+1)/4;

            //Redéfinition de la position et de la forme.
            t.setPositionTile(new Position(left, top, right, bottom));
            //t.setTileForm(new RectF(left, top, right, bottom));

            tilesToDisplay.add(t);
        }

        return tilesToDisplay;
    }
}
