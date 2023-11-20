package domain;

import java.util.HashMap;
import java.util.List;

public class Board {
    int start;
    int end;
    HashMap<Integer,Snake> snakes;
    HashMap<Integer,Ladder> ladders;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public HashMap<Integer,Snake> getSnakes() {
        return snakes;
    }

    public void setSnakes(HashMap<Integer,Snake> snakes) {
        this.snakes = snakes;
    }

    public HashMap<Integer,Ladder> getLadder() {
        return ladders;
    }

    public void setLadder(HashMap<Integer,Ladder> ladders) {
        this.ladders = ladders;
    }

    public Board(int start, int end, HashMap<Integer,Snake> snakes, HashMap<Integer,Ladder> ladders) {
        this.start = start;
        this.end = end;
        this.snakes = snakes;
        this.ladders = ladders;
    }
}
