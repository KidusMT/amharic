package thieves.salad.amharic;

import java.util.ArrayList;
import java.util.Random;

public class Group {

    private ArrayList<String> englishValues;
    private ArrayList<String> amharicValues;
    private ArrayList<String> processedValues;
    private final Random rand;

    public Group() {
        englishValues = new ArrayList<>();
        amharicValues = new ArrayList<>();
        processedValues = new ArrayList<>();
        rand = new Random();
    }

    public Group add(int value, String amharic) {
        return add(String.valueOf(value), amharic);
    }

    public Group add(String english, String amharic) {
        if (englishValues.contains(english) || amharicValues.contains(amharic)) return this;
        englishValues.add(english);
        amharicValues.add(amharic);
        return this;
    }

    public Group add(Group group) {
        for (int i = 0; i < group.size(); i++) {
            add(group.englishValues.get(i), group.amharicValues.get(i));
        }
        return this;
    }

    public Group addFromRule(int value) {
        Group group = Numbers.generateFromRules(new NumberRule(value));
        add(group);
        return this;
    }

    public Group addFromRule(int start, int stop) {
        Group group = Numbers.generateFromRules(new NumberRule(start, stop));
        add(group);
        return this;
    }

    public void build() {
        // do nothing
    }

    public String findEnglishValue(String amharic) {
        return findValue(amharic, false);
    }

    public String findAmharicValue(String english) {
        return findValue(english, true);
    }

    public String findValue(String value, boolean sourceEnglish) {
        ArrayList<String> sourceValues = sourceEnglish ? englishValues : amharicValues;
        ArrayList<String> destinationValues = sourceEnglish ? amharicValues : englishValues;
        int position = sourceValues.indexOf(value);
        return position == -1 ? null : destinationValues.get(position);
    }

    public String getNextEnglishValue(String prevAmharic) {
        return getNextValue(prevAmharic, false);
    }

    public String getNextAmharicValue(String prevEnglish) {
        return getNextValue(prevEnglish, true);
    }

    public String getNextValue(String prevValue, boolean sourceEnglish) {
        return getNextLinearValue(prevValue, sourceEnglish, false);
    }

    public String getRevNextEnglishValue(String prevAmharic) {
        return getRevNextValue(prevAmharic, false);
    }

    public String getRevNextAmharicValue(String prevEnglish) {
        return getRevNextValue(prevEnglish, true);
    }

    public String getRevNextValue(String prevValue, boolean sourceEnglish) {
        return getNextLinearValue(prevValue, sourceEnglish, true);
    }

    private String getNextLinearValue(String prevValue, boolean sourceEnglish, boolean reverse) {
        ArrayList<String> sourceValues = sourceEnglish ? englishValues : amharicValues;
        ArrayList<String> targetValues = sourceEnglish ? amharicValues : englishValues;
        int position;
        if (prevValue == null) {
            position = reverse ? size() - 1 : 0;
        } else {
            int prevPosition = sourceValues.indexOf(prevValue);
            position = (reverse ? prevPosition - 1 : prevPosition + 1);
            if (position < 0) position = 0;
            if (position > size() - 1) position = size() - 1;
        }
        String nextValue = sourceValues.get(position);
        processedValues.add(nextValue);
        return nextValue;
    }

    public String getNextRandomEnglishValue() {
        return getNextRandomValue(true);
    }

    public String getNextRandomAmharicValue() {
        return getNextRandomValue(false);
    }

    public String getNextRandomValue(boolean sourceEnglish) {
        ArrayList<String> sourceValues = sourceEnglish ? englishValues : amharicValues;
        String value = null;
        if (hasRemainingValues()) {
            do {
                value = getRandomValue(sourceValues);
            } while (processedValues.contains(value));
            processedValues.add(value);
        }
        return value;
    }

    private String getRandomValue(ArrayList<String> sourceValues) {
        return sourceValues.get(rand.nextInt(sourceValues.size()));
    }

    public void reset() {
        processedValues.clear();
    }

    public boolean hasRemainingValues() {
        return size() != processedValues.size();
    }

    public int size() {
        return amharicValues.size();
    }
}
