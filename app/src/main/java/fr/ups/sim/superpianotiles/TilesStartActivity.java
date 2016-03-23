package fr.ups.sim.superpianotiles;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import fr.ups.sim.superpianotiles.obj.Position;
import fr.ups.sim.superpianotiles.obj.Tile;

public class TilesStartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles_start);

        //ICI - Commentez le code
        TilesView tilesView = (TilesView) findViewById(R.id.view);

        //ICI - Commentez le code
        tilesView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = onTouchEventHandler(event);
                return result;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tiles_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // ICI - A compléter pour déclencher l'ouverture de l'écran de paramétrage
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Cet évènement survient quand l'utilisateur touche l'écran, lorsqu'il exerce une pression
     * continue sur l'écran et lorsqu'il la relâche.
     *     ACTION_DOWN : 0 - L'utilisateur appuie sur l'écran
     *     ACTION_UP : 1 - L'utilisateur n'appuie plus sur l'écran
     *     ACTION_MOVE : 2 - Fait suite à ACTION_DOWN, l'utilisateur garde appuyé et fait un movement
     *
     * Cette fonction retourne un booléen :
     *      true : quand l'évènement a été géré
     *      false sinon
    */
    private boolean onTouchEventHandler (MotionEvent evt){
        Log.i("TilesView", "Touch event handled - " + evt.getAction());

        // Quand l'utilisateur touche l'écran, pas quand il relâche la pression
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            float x = evt.getX();
            float y = evt.getY();

            TilesView tilesView = (TilesView) findViewById(R.id.view);
            Tile currentTile = null;

            if (! tilesView.getTiles().isEmpty())
                currentTile = tilesView.getTiles().get(0);

            /* Si la première tile est touchée, alors il faut la supprimer. */
            /* On fait aussi défiler les autres tuiles vers le bas */
            if (currentTile != null && currentTile.isTouched(x, y)) {
                tilesView.getTiles().remove(0);
                slideTiles();
                Log.i("TilesView", "Tile touched - " + currentTile.getText());
            }
            else // Si aucune tile n'a été touché, le joueur a perdu
                Log.i("TilesView", "No tile touched - Player Lost");

            return true;
        }
        else
            if (evt.getAction() == MotionEvent.ACTION_UP)   // Au relâchement de la pression sur l'écran
                return true;

        return false;
    }

    public void slideTiles() {

        /* Utiliser deux animations (dont une ne fait rien ici) et les enchaîner permet d'éviter
         * le prblème de flicker à la fin de l'animation.
         * Initialement (sans le listener et l'animation "vide" il y avait un clignotement à l'écran
         * à la fin de l'animation de défilement. */
        TilesView tilesView = (TilesView) findViewById(R.id.view);

        Animation slideTiles = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_tiles_4x4);
        tilesView.startAnimation(slideTiles);

        Thread delayPositionRefresh = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TilesView tilesView = (TilesView) findViewById(R.id.view);
                Position currPosition = new Position();
                int tileSize = 0;

                for (Tile currTile : tilesView.getTiles()) {
                    currPosition = currTile.getPositionTile();
                    tileSize = currPosition.getBottom() - currPosition.getTop();
                    currPosition.setTop(currPosition.getTop() + tileSize);
                    currPosition.setBottom(currPosition.getBottom() + tileSize);
                    currTile.setPositionTile(currPosition);
                }
            }
        };

        delayPositionRefresh.start();
    }
}
