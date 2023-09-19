package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDGenerator {
    public static void main(String[] args) {
        System.out.println(getNextAvailableID("sh", List.of("sh-1", "sh-69")));
    }
    public static String getNextAvailableID(String prefix, List<String> IDList) {
        // make a Regex pattern to get the number after the prefix with dash
        Pattern pattern = Pattern.compile("sh-(\\d+)");

        List<String> sortedIds = getSortedIds(IDList, pattern);

        Matcher matcher = pattern.matcher(sortedIds.get(sortedIds.size()-1));
        if (matcher.find()) {
            int next = Integer.parseInt(matcher.group(1)) + 1;
            return prefix + "-" + next;
        }

        return null;
    }

    private static List<String> getSortedIds(List<String> IDList, Pattern pattern) {
        List<String> sortedIds = new ArrayList<>(IDList);

        sortedIds.sort((o1, o2) -> {
            Matcher matcher1 = pattern.matcher(o1);
            Matcher matcher2 = pattern.matcher(o2);

            if (matcher1.find() && matcher2.find()) {
                int number1 = Integer.parseInt(matcher1.group(1));
                int number2 = Integer.parseInt(matcher2.group(1));

                return Integer.compare(number1, number2);
            } else {
                // Handle cases where the pattern doesn't match
                return o1.compareTo(o2);
            }
        });
        return sortedIds;
    }
}
