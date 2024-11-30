package pairmatching.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.Crew;
import pairmatching.domain.CrewShuffleManager;
import pairmatching.domain.Level;
import pairmatching.domain.PairFormatter;
import pairmatching.dto.PairRequest;
import pairmatching.exception.PairException;
import pairmatching.repository.PairRepository;

public class PairService {
    private final PairRepository pairRepository;
    private final int MAX_DUPLICATE_COUNT = 3;
    private final String FAIL_MATCHING_ERROR = "페어 매칭에 실패하였어요..";

    public PairService(PairRepository pairRepository) {
        this.pairRepository = pairRepository;
    }

    public void init() {
        pairRepository.initCrewFromFile();
        pairRepository.initMap();
    }

    public String pairMatching(PairRequest pairRequest, boolean rematch) {
        int duplicateCount = 0;
        List<Crew> originCrewList = pairRepository.findCrewListByCourse(pairRequest.course);
        if(rematch){
            pairRepository.rematchInit(pairRequest);
        }
        while (duplicateCount != MAX_DUPLICATE_COUNT){
            List<String> shuffledCrew = CrewShuffleManager.shuffleCrew(originCrewList);
            List<List<String>> matchingList = matching(shuffledCrew);
            if(isDuplicatePair(matchingList,pairRequest)){
                duplicateCount++;
                continue;
            }
            pairRepository.saveMatchingList(matchingList,pairRequest);
            return PairFormatter.printMatchingResult(matchingList);
        }
        throw new PairException(FAIL_MATCHING_ERROR);
    }

    private boolean isDuplicatePair(List<List<String>> matchingList, PairRequest pairRequest) {
        for(List<String> pairList : matchingList){
            List<String> tmpList = new ArrayList<>();
            Collections.copy(pairList,tmpList);
            Collections.sort(tmpList);
            String pairStr = tmpList.stream().collect(Collectors.joining("_")) + "_" + pairRequest.level.name() +  "_" + pairRequest.getMission().name() + "_" + pairRequest.course.name();;
            if(pairRepository.isDuplicatePair(pairStr)){
                return true;
            }
        }
        return false;
    }

    private List<List<String>> matching(List<String> shuffledCrew) {
        int idx = 0;
        List<List<String>> pairResult = new ArrayList<>();
        while (idx+2 <= shuffledCrew.size()){
            pairResult.add(shuffledCrew.subList(idx, idx + 2));
            idx+=2;
        }
        if(idx != shuffledCrew.size()){
            ArrayList<String> tmpList = new ArrayList<>();
            tmpList.addAll(pairResult.get(pairResult.size() - 1));
            tmpList.add(shuffledCrew.get(shuffledCrew.size()-1));
            pairResult.set(pairResult.size()-1,tmpList);
        }
        return pairResult;
    }

    public boolean isExistsPairMatching(PairRequest pairRequest) {
        return pairRepository.isExistsPair(pairRequest);
    }

    public String getPairViewByPairRequest(PairRequest pairRequest) {
        List<List<String>> pairView = pairRepository.findByPairRequest(pairRequest);
        return PairFormatter.printMatchingResult(pairView);
    }

    public void reset() {
        pairRepository.reset();
    }
}
