package fr.ups.sim.superpianotiles.obj;

import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anadidathorion on 21/03/2016.
 */
public class Track {
    private int tilesCount;
    private List<Tile> tiles;

    public Track(int tilesCount){

        this.tilesCount = tilesCount;
        tiles = new LinkedList<>();

        for (int i = 0; i < tilesCount; i++){
            //La position des tuiles n'est pas définit à leur création.
            tiles.add(new Tile(Color.RED, Color.WHITE, "1", 40, null, null));
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
    public List<Tile> tilesDisplay(int contentWidth, int contentHeight){
        List<Tile> tilesToDisplay = new ArrayList<>(4);

        //La première tuile de la liste est la tuile la plus basse à l'écran
        for (int i = 4; i > 0; i--){
            //Impossible de faire un poll...
            Tile t = tiles.get(0);

            //Dépendent du random du quarterPosition
            int rnd = QuarterPosition.randomPosition().ordinal();
            int left = contentWidth * rnd/4;
            int right = contentWidth * (rnd+1)/4;

            //Dépendent de leur position dans la liste
            int top = contentHeight * i/4;
            int bottom = contentHeight * (i+1)/4;

            //Redéfinition de la position et de la forme.
            t.setPositionTile(new Position(left, top, right, bottom));
            t.setTileForm(new RectF(left, top, right, bottom));

            tilesToDisplay.add(t);
        }

        return tilesToDisplay;
    }
}
