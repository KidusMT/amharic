package thieves.salad.amharic;

import java.util.ArrayList;

public class Numbers {

    private static final String[] BASE = {
            "ziero",
            "and",
            "hulet",
            "sost",
            "arat",
            "amIst",
            "sIdIst",
            "sebat",
            "sImInt",
            "zetyegn",
            "asIr"
    };

    private static final String[] TENTHS = {
            "asra",
            "haya",
            "selasa",
            "arba",
            "amsa",
            "sIdsa",
            "seba",
            "semanya",
            "zetyena",
    };

    private static final String[] HUNDRED = {
            "meto"
    };

    private static final String[] THOUSAND = {
            "shi"
    };

    private static final String[] MILLION = {
            "million"
    };

    private ArrayList<Group> groups;

    public Numbers() {
        groups = new ArrayList<>();
    }

    public static String parse(int number) {
        if (number <= 10) {
            return parseBase(number);
        }
        if (number <= 99) {
            return parseTenths(number);
        }
        return null;
    }

    private static String parseBase(int number) {
        String value = String.valueOf(number);
        if (value.length() == 1) {
            return BASE[number];
        } else {
            if (value.length() == 2 && value.equals("10")) {
                return BASE[number];
            }
            return null;
        }
    }

    private static String parseTenths(int number) {
        String value = String.valueOf(number);
        int base = Integer.parseInt(String.valueOf(value.charAt(0)));
        int tenth = Integer.parseInt(String.valueOf(value.charAt(1)));
        String output = TENTHS[base - 1];
        if (tenth == 0) return output;
        return output + " " + BASE[tenth];
    }

    public static Group generateGroup(int stop) {
        return generateGroup(0, stop);
    }

    public static Group generateGroup(int start, int stop) {
        Group group = new Group();
        for (int i = start; i <= stop; i++) {
            group.add(i, parse(i));
        }
        group.build();
        return group;
    }

    public static Group generateFromValues(int... values) {
        Group group = new Group();
        for (int value : values) {
            group.add(value, parse(value));
        }
        group.build();
        return group;
    }

    public static Group generateFromRules(NumberRule... rules) {
        Group group = new Group();
        for (NumberRule rule : rules) {
            if (rule.stop == -1) {
                group.add(rule.start, parse(rule.start));
            } else {
                Group other = generateGroup(rule.start, rule.stop);
                group.add(other);
            }
        }
        group.build();
        return group;
    }
}