package pairmatching.domain;

import java.util.List;
import java.util.stream.Collectors;

public class PairFormatter {
    public static String printMatchingResult(List<List<String>> matchingList) {
        StringBuilder stringBuilder = new StringBuilder();
        for(List<String> pairList : matchingList){
            String pairStr = pairList.stream().collect(Collectors.joining(" : "));
            stringBuilder.append(pairStr).append("\n");
        }
        return stringBuilder.toString();
    }
}
