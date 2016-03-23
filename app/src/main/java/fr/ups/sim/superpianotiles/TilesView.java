package fr.ups.sim.superpianotiles;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.ups.sim.superpianotiles.obj.data.Tile;
import fr.ups.sim.superpianotiles.obj.data.Track;

/**
 * Custom view that displays tiles
 */
public class TilesView extends ImageView {

    private Drawable mExampleDrawable;
    private boolean init = true;

    // ArrayList contenant les tiles à afficher à l'écran
    private ArrayList<Tile> tiles = new ArrayList<>();
    private Track track = null;

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
             /* Pour les pistes : Définir les [16 | 25] positions possibles suivants ces coordonnées

                L'écran est divisé en largeur sur [4 | 5] pistes
                et en hauteur sur [4 | 5] lignes.

                left de 0 à contentWidth*3/[4 | 5]
                right de contentWidth/[4 | 5] à contentWidth
                top de contentHeight*3/[4 | 5] à 0
                bottom de contentHeight à contentHeight/[4 | 5]
            */

            // Une tile est composée de sa couleur, celle de son texte, le texte en question et sa taille
            // et enfin de sa position ainsi que de la forme rectangulaire nécessaire à son dessin.

            try {
                track = new Track(25, contentWidth, contentHeight, 5, this);
            } catch (Exception e) {
                //TODO Gestion Exception Création Track
            }

            this.tiles = track.tilesDisplay();
            init = false;
        }

        if (!this.getTiles().isEmpty())
            addTiles(canvas, this.getTiles());
        else {
            Context context = getContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Victoire !", duration);
            toast.show();
            TextView textView = (TextView) findViewById(R.id.textView);
            String chronoString = textView.getText().toString();

            ((Activity)getContext()).finish();
        }

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

    /* Méthodes permettant d'éviter l'effet de flickering des animations */
    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        this.setDrawingCacheEnabled(true);
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        this.setDrawingCacheEnabled(false);
    }

    public Track getTrack(){ return track; }
}
