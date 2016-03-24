package fr.ups.sim.superpianotiles.obj.data;

import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import fr.ups.sim.superpianotiles.obj.data.Position;
import fr.ups.sim.superpianotiles.obj.data.Tile;
import fr.ups.sim.superpianotiles.obj.misc.EnumPosition4x4;
import fr.ups.sim.superpianotiles.obj.misc.EnumPosition5x5;

/**
 * Created by Valérian on 21/03/2016.
 */
public class Track {
    private int tilesCount;
    private LinkedList<Tile> tiles;
    private int contentHeight;
    private int nbTilesDisplayed;


    public Track(int tilesCount, int contentWidth, int contentHeight, int nbTiles) throws Exception{

        this.tilesCount = tilesCount;
        tiles = new LinkedList<>();
        this.contentHeight = contentHeight;

        for (int i = 0; i < tilesCount; i++){
            int rnd;
            //Dépendent du random du quarterPosition
            switch (nbTiles){
                case 4 :
                    rnd = EnumPosition4x4.randomPosition().ordinal();
                    this.nbTilesDisplayed = 4;
                    break;
                case 5 :
                    rnd = EnumPosition5x5.randomPosition().ordinal();
                    this.nbTilesDisplayed = 5;
                    break;
                default :
                    throw new Exception();
            }

            int left = contentWidth * rnd/nbTiles;
            int right = contentWidth * (rnd+1)/nbTiles;

            int top = 0;
            int bottom = contentHeight * 1/nbTiles;

            tiles.add(new Tile(Color.BLACK, Color.WHITE, ""+(i+1), 40, new Position(left, top, right, bottom)));
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
        ArrayList<Tile> tilesToDisplay = new ArrayList<>(nbTilesDisplayed);

        //La première tuile de la liste est la tuile la plus basse à l'écran
        for (int i = 0; i < ((nbTilesDisplayed <= tiles.size()) ? nbTilesDisplayed : tiles.size()); i++){
            Tile t = tiles.get(i);

            //Dépendent du random du quarterPosition
            int left = t.getPositionTile().getLeft();
            int right = t.getPositionTile().getRight();

            //Dépendent de leur position dans la liste
            int top = contentHeight * (nbTilesDisplayed-1-i)/nbTilesDisplayed;
            int bottom = contentHeight * (nbTilesDisplayed-i)/nbTilesDisplayed;

            //Redéfinition de la position et de la forme.
            t.setPositionTile(new Position(left, top, right, bottom));

            tilesToDisplay.add(t);
        }

        return tilesToDisplay;
    }

    public Tile nextTileDisplayed() {
        Tile nextTile;

        if (!tiles.isEmpty())
            nextTile = tiles.get(nbTilesDisplayed - 1);
        else
            nextTile = new Tile(Color.WHITE, Color.WHITE, "", 40, new Position());

        return nextTile;
    }

    public void pollFirstTile(){
        tiles.poll();
        tilesCount--;
    }
}
