package thieves.salad.amharic;

public class NumberRule {

    public int start;
    public int stop;

    public NumberRule(int value) {
        start = value;
        stop = -1;
    }

    public NumberRule(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }
}
