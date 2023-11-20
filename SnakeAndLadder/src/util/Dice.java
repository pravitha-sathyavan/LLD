package util;

import java.util.Random;

public class Dice {
    private static int size = 6;

    public int getRandom() {
        return new Random().nextInt(size) + 1;
    }
}
