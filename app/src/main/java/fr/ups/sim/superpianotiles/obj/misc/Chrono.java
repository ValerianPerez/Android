package fr.ups.sim.superpianotiles.obj.misc;


import android.widget.TextView;

import fr.ups.sim.superpianotiles.R;

import static java.lang.System.nanoTime;

/**
 * Created by Nolan on 23/03/2016.
 */
public class Chrono {
    private TextView textView;
    private Thread chrono;

    public Chrono(TextView tv) {
        textView = tv;

    }

    public void start() {
        chrono.start();
    }
}
