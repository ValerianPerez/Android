package fr.ups.sim.superpianotiles;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import fr.ups.sim.superpianotiles.obj.data.Position;
import fr.ups.sim.superpianotiles.obj.data.Tile;

import static java.lang.System.nanoTime;

public class TilesStartActivity extends Activity {

    private Thread chrono;
    private long chronoValue;
    private long score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_tiles_start);

        //ICI - Commentez le code
        TilesView tilesView = (TilesView) findViewById(R.id.view);

        /* Lancement du chronomètre */
        launchChronoScore();

        /* Listener appelant la callback onTouchEventHandler lorsque l'utilisateur touche l'écran.
         * La callback est appelé à la pression, au relâchement ou quand l'utilisateur reste appuyé.
         * Cf. commentaire de onTouchEventHandler.
         */
        tilesView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEventHandler(event);
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

            if (!tilesView.getTiles().isEmpty())
                currentTile = tilesView.getTiles().get(0);

            /* Si la première tile est touchée, alors il faut la supprimer. */
            /* On fait aussi défiler les autres tuiles vers le bas */
            if (currentTile != null && currentTile.isTouched(x, y)) {
                score += (Long.parseLong(currentTile.getText()) * 100 * chronoValue / 11);
                tilesView.getTiles().remove(0);
                tilesView.getTrack().pollFirstTile();
                slideTiles();
                Log.i("TilesView", "Tile touched - " + currentTile.getText());
            }
            else { // Si aucune tile n'a été touché, ou si ce n'était pas le bonne,
                // le joueur perd des points
                Log.i("TilesView", "No tile touched - Points loss");
                score -= Long.parseLong(currentTile.getText()) * 300 * chronoValue / 13;
            }

            return true;
        }
        else
            if (evt.getAction() == MotionEvent.ACTION_UP)   // Au relâchement de la pression sur l'écran
                return true;

        return false;
    }

    /* Fait défiler les tiles vers le bas avec une animation quand l'utilisateur touche la bonne tile. */
    public void slideTiles() {
        TilesView tilesView = (TilesView) findViewById(R.id.view);

        /* Lancement de l'animation définie en xml dans le fichier res/anim/slide_tiles_4x4
         * Surcharge des évènements onAnimationStart et onAnimationEnd dans TilesView */
        Animation slideTiles = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_tiles_4x4);
        tilesView.startAnimation(slideTiles);

        /* Ce thread permet de synchroniser l'actualisation des positions des tiles avec la fin de
         * l'animation de la vue (100ms). */
        Thread delayPositionRefresh = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TilesView tilesView = (TilesView) findViewById(R.id.view);
                Position currPosition = null;
                int tileSize = 0;

                /* On modifie les positions des tuiles à afficher */
                for (Tile currTile : tilesView.getTiles()) {
                    currPosition = currTile.getPositionTile();
                    tileSize = currPosition.getBottom() - currPosition.getTop();
                    currPosition.setTop(currPosition.getTop() + tileSize);
                    currPosition.setBottom(currPosition.getBottom() + tileSize);
                    currTile.setPositionTile(currPosition);
                }

                try {
                    tilesView.getTiles().add(tilesView.getTrack().nextTileDisplayed());
                }
                catch (Exception e) {
                    System.err.println(e);
                }
            }
        };

        delayPositionRefresh.start();
    }

    public void launchChronoScore() {
        final TextView textViewChrono = (TextView) findViewById(R.id.textViewChrono);
        final TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
        final long launchTime;

        launchTime = nanoTime();

        chrono = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        chronoValue = nanoTime() - launchTime;
                        sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long s = chronoValue / 1000000000;
                                long dcm = (int) (chronoValue / 1000000)%1000;
                                textViewChrono.setText(s + ":" + dcm);
                                textViewScore.setText("" + score);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        chrono.start();
    }
}
