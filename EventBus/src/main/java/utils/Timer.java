package utils;

import com.google.inject.Singleton;
import models.Timestamp;

@Singleton
public class Timer {
    public Timestamp getTime() {
        return new Timestamp(System.nanoTime());
    }
}
