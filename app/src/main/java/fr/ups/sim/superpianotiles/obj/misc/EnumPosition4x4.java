package fr.ups.sim.superpianotiles.obj.misc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Valérian on 21/03/2016.
 */
public enum EnumPosition4x4 {
    FIRST,
    SECOND,
    THIRD,
    FOURTH;

    /*
    Code issu de Stack OverFlow :
                http://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum
     */
    private static final List<EnumPosition4x4> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static EnumPosition4x4 randomPosition()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
