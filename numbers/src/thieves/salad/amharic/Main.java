package thieves.salad.amharic;

public class Main {
    public static void main(String[] args) {
        Group group = new Group();
        group
            .add(Numbers.generateFromRules(new NumberRule(10, 30)));
//                .addFromRule(50, 60);
//                .add(Numbers.generateFromValues(33, 36));

        new Quiz(group)
//                .addParamSet(QuizParamSet.MODE_SEQUENTIAL, QuizParamSet.SOURCE_EN_TO_AM, 1)
//                .addParamSet(QuizParamSet.MODE_REVERSE, QuizParamSet.SOURCE_EN_TO_AM, 2)
                .addParamSet(QuizParamSet.MODE_RANDOM, QuizParamSet.SOURCE_EN_TO_AM, 1)
                .addParamSet(QuizParamSet.MODE_SEQUENTIAL, QuizParamSet.SOURCE_AM_TO_EN, 2)
                .addParamSet(QuizParamSet.MODE_SEQUENTIAL, QuizParamSet.SOURCE_AM_TO_EN, 2)
//                .generateRandomSets(3)
                .start();
    }
}