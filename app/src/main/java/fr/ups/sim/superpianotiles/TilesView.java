package fr.ups.sim.superpianotiles;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import fr.ups.sim.superpianotiles.obj.Position;
import fr.ups.sim.superpianotiles.obj.Tile;

/**
 * Custom view that displays tiles
 */
public class TilesView extends View {

    private Drawable mExampleDrawable;
    private boolean init = true;

    // ArrayList contenant les tiles à afficher à l'écran
    private ArrayList<Tile> tiles = new ArrayList<>();

    public TilesView(Context context) {
        super(context);
        init(null, 0);
    }

    public TilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TilesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TilesView, defStyle, 0);

        if (a.hasValue(R.styleable.TilesView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.TilesView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        if (init) {
             /* Pour les pistes : Définir les 16 positions possibles suivants ces coordonnées

                L'écran est divisé en largeur sur 4 pistes
                et en hauteur sur 4 lignes.

                left de 0 à contentWidth*3/4
                right de contentWidth/4 à contentWidth
                top de contentHeight*3/4 à 0
                bottom de contentHeight à contentHeight/4
            */

            //Tile 1
            int left = 0;
            int top = contentHeight * 3 / 4;
            int right = contentWidth / 4;
            int bottom = contentHeight;

            // Une tile est composée de sa couleur, celle de son texte, le texte en question et sa taille
            // et enfin de sa position ainsi que de la forme rectangulaire nécessaire à son dessin.
            Position positionTile1 = new Position(left, top, right, bottom);
            Tile tile1 = new Tile(Color.BLACK, Color.WHITE, "1", 40, positionTile1, new RectF(left, top, right, bottom));
            this.tiles.add(tile1);

            //Tile 2
            left = contentWidth / 4;
            top = contentHeight / 2;
            right = contentWidth / 2;
            bottom = contentHeight * 3 / 4;

            Position positionTile2 = new Position(left, top, right, bottom);
            Tile tile2 = new Tile(Color.BLACK, Color.WHITE, "2", 40, positionTile2, new RectF(left, top, right, bottom));
            this.tiles.add(tile2);

            //Tile 3
            left = contentWidth * 2 / 4;
            top = contentHeight / 4;
            right = contentWidth * 3 / 4;
            bottom = contentHeight / 2;

            Position positionTile3 = new Position(left, top, right, bottom);
            Tile tile3 = new Tile(Color.BLACK, Color.WHITE, "3", 40, positionTile3, new RectF(left, top, right, bottom));
            this.tiles.add(tile3);

            //Tile 4
            left = contentWidth * 3 / 4;
            top = 0;
            right = contentWidth;
            bottom = contentHeight / 4;

            Position positionTile4 = new Position(left, top, right, bottom);
            Tile tile4 = new Tile(Color.BLACK, Color.WHITE, "4", 40, positionTile4, new RectF(left, top, right, bottom));
            this.tiles.add(tile4);

            init = false;
        }

        addTiles(canvas, this.getTiles());

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    public void addTiles(Canvas canvas, ArrayList<Tile> tiles) {
        for (Tile t : tiles)
            addTile(t, canvas);
    }

    public void addTile(Tile tile, Canvas canvas){
        RectF rect = tile.getPositionTile().getPositionForm();
        canvas.drawRoundRect(rect, 2, 2, tile.pTile);
        canvas.drawText(tile.getText(), rect.centerX(), rect.centerY(), tile.pText);
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
