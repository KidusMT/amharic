package thieves.salad.amharic;

public class QuizParamSet {

    public static final String MODE_SEQUENTIAL = "SEQUENTIAL";
    public static final String MODE_REVERSE = "REVERSE";
    public static final String MODE_RANDOM = "RANDOM";

    public static final String SOURCE_EN_TO_AM = "ENG_TO_AM";
    public static final String SOURCE_AM_TO_EN = "AM_TO_EN";

    public String quizMode;
    public String source;
    public int repeatCount;

    public QuizParamSet() {
        this(MODE_SEQUENTIAL, SOURCE_EN_TO_AM, 1);
    }

    public QuizParamSet(String quizMode, String source, int repeatCount) {
        this.quizMode = quizMode;
        this.source = source;
        this.repeatCount = repeatCount;
    }

    public QuizParamSet setQuizMode(String quizMode) {
        this.quizMode = quizMode;
        return this;
    }

    public QuizParamSet setSource(String source) {
        this.source = source;
        return this;
    }

    public QuizParamSet setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }
}
