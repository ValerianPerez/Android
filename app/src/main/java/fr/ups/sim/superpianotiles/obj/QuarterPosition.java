package fr.ups.sim.superpianotiles.obj;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Anadidathorion on 21/03/2016.
 */
public enum QuarterPosition {
    FIRST,
    SECOND,
    THIRTH,
    FOURTH;

    /*
    Code issu de Stack OverFlow :
                http://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum
     */
    private static final List<QuarterPosition> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static QuarterPosition randomPosition()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
