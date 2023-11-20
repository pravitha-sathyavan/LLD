package domain;

public class Ladder {
    int start;
    int end;

    public Ladder(int start, int end) throws Exception {
        if(start>=end){
            throw new Exception("Invalid Input for Ladder");
        }
        this.start = start;
        this.end = end;
    }

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

}
