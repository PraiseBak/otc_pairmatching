package pairmatching.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrewShuffleManager {

    public static List<String> shuffleCrew(List<Crew> originCrewList){
        List<String> nameList = originCrewList.stream()
                .map((c) -> c.getName())
                .collect(Collectors.toList());
        return Randoms.shuffle(nameList);
    }
}
