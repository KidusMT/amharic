package thieves.salad.amharic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Quiz {

    private Group group;
    private ArrayList<QuizParamSet> paramSets;
    private ArrayList<String> correctValues;
    private ArrayList<String> wrongValues;
    private final Random rand;

    public Quiz(Group group) {
        this.group = group;
        paramSets = new ArrayList<>();
        correctValues = new ArrayList<>();
        wrongValues = new ArrayList<>();
        rand = new Random();
    }

    public Quiz addParamSet(String quizMode, String source, int repeatCount) {
        QuizParamSet set = new QuizParamSet(quizMode, source, repeatCount);
        paramSets.add(set);
        return this;
    }

    public Quiz generateRandomSets(int maxSets) {
        paramSets.clear();
        for (int i = 0; i < maxSets; i++) {
            paramSets.add(generateSet());
        }
        return this;
    }

    private QuizParamSet generateSet() {
        int mode = rand.nextInt(3);
        String quizMode;
        switch (mode) {
            case 0:
                quizMode = QuizParamSet.MODE_SEQUENTIAL;
                break;
            case 1:
                quizMode = QuizParamSet.MODE_REVERSE;
                break;
            default:
                quizMode = QuizParamSet.MODE_RANDOM;
        }

        int s = rand.nextInt(2);
        String source = s == 0 ? QuizParamSet.SOURCE_EN_TO_AM : QuizParamSet.SOURCE_AM_TO_EN;
        int repeatCount = rand.nextInt(quizMode.equals(QuizParamSet.MODE_RANDOM) ? 4 : 1) + 1;

        return new QuizParamSet(quizMode, source, repeatCount);
    }

    public void start() {
        int setCount = paramSets.size();
        for (int i = 0; i < setCount; i++) {
            System.out.printf("Set %d of %d\n", (i + 1), setCount);
            QuizParamSet set = paramSets.get(i);
            boolean sourceEnglish = set.source.equals(QuizParamSet.SOURCE_EN_TO_AM);
            System.out.printf("Translating from [%s to %s]\n",
                    (sourceEnglish ? "English" : "Amharic"),
                    (sourceEnglish ? "Amharic" : "English")
            );
            for (int j = 0; j < set.repeatCount; j++) {
                System.out.printf("Pass %d of %d\n", (j + 1), set.repeatCount);
                reset();
                String prevValue = null;
                Scanner in = new Scanner(System.in);
                while (group.hasRemainingValues()) {
                    String value = getNextValue(prevValue, set);
                    String expected = group.findValue(value, sourceEnglish);
                    System.out.printf("- %s: ", value);
                    String input = in.nextLine();
                    boolean right = input.equals(expected);
                    (right ? correctValues : wrongValues).add(value);
                    prevValue = value;
                }

                int correct = correctValues.size();
                int wrong = wrongValues.size();
                int total = correct + wrong;

                System.out.printf("Results: %d / %d\n", correct, total);
                if (wrong != 0) {
                    System.out.println("Errors:");
                    for (String value : wrongValues) {
                        System.out.printf("\t[ %s : %s ]\n", value, group.findValue(value, sourceEnglish));
                    }
                }
                System.out.println("------------------------------------\n");
                System.out.println("Press Enter to continue...");
                in.nextLine();
                System.out.println("\n\n\n\n\n\n");
            }
        }
    }

    private void reset() {
        group.reset();
        correctValues.clear();
        wrongValues.clear();
    }

    private String getNextValue(String prevValue, QuizParamSet set) {
        boolean sourceEnglish = set.source.equals(QuizParamSet.SOURCE_EN_TO_AM);
        switch (set.quizMode) {
            case QuizParamSet.MODE_SEQUENTIAL:
                return group.getNextValue(prevValue, sourceEnglish);
            case QuizParamSet.MODE_REVERSE:
                return group.getRevNextValue(prevValue, sourceEnglish);
            default:
                return group.getNextRandomValue(sourceEnglish);
        }
    }
}