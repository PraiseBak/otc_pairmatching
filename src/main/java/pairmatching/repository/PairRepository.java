package pairmatching.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.dto.PairRequest;
import pairmatching.exception.PairException;
import pairmatching.exception.PairStateException;
import pairmatching.view.OutputView;

public class PairRepository {

    private static final String FILE_MISSING_MSG = "파일이 존재하지 않습니다.";
    private static final String PAIR_DOESNT_EXISTS = "매칭 이력이 없습니다.";
    private static final String BACKEND_CREW_SRC = "./src/main/resources/backend-crew.md";
    private static final String FRONTEND_CREW_SRC = "./src/main/resources/frontend-crew.md";
    private static final String FILE_ERROR_MSG = "파일 입출력에 실패하였습니다.";
    private final ArrayList<Crew> backendCrewList = new ArrayList<>();
    private final ArrayList<Crew> frontendCrewList = new ArrayList<>();
    private final Map<Mission, Pair> pairRepository = new HashMap<>();
    private Set<String> crewMatchingHistory = new HashSet<>();

    public void initCrewFromFile() {
        try {
            File bakcendCrewFile = new File(BACKEND_CREW_SRC);
            File frontendCrewFile = new File(FRONTEND_CREW_SRC);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(bakcendCrewFile)));
            readFileWithBufferedReader(bufferedReader,Course.BACKEND,backendCrewList);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(frontendCrewFile)));
            readFileWithBufferedReader(bufferedReader,Course.FRONTEND, (ArrayList<Crew>) frontendCrewList);
        } catch (FileNotFoundException e) {
            OutputView.printError(FILE_MISSING_MSG);
        }
    }

    private void readFileWithBufferedReader(BufferedReader bufferedReader,Course course,ArrayList<Crew> crewList)  {
        try {
            String line = bufferedReader.readLine();;
            do {
                crewList.add(new Crew(course,line));
                line = bufferedReader.readLine();
            } while (line != null);
        }catch (IOException e){
            OutputView.printError(FILE_ERROR_MSG);
        }
    }

    public void initMap() {
        for(Mission mission : Mission.values()){
            pairRepository.put(mission,new Pair());
        }
    }

    public List<Crew> getBackendCrewList() {
        return backendCrewList;
    }

    public List<Crew> getFrontendCrewList() {
        return frontendCrewList;
    }

    public Map<Mission, Pair> getPairRepository() {
        return pairRepository;
    }

    public List<Crew> findCrewListByCourse(Course course) {
        if(course == Course.BACKEND){
            return backendCrewList;
        }
        return frontendCrewList;
    }

    public void saveMatchingList(List<List<String>> matchingList, PairRequest pairRequest) {
        Pair pair = pairRepository.get(pairRequest.getMission());
        for(List<String> pairList : matchingList){
            String pairStr = pairList.stream().collect(Collectors.joining("_")) + "_" + pairRequest.level.name() + "_" + pairRequest.getMission().name() + "_" + pairRequest.course.name();
            crewMatchingHistory.add(pairStr);
        }
        pair.setMatchingList(matchingList,pairRequest.course);
    }

    public boolean isDuplicatePair(String pairStr) {
        return crewMatchingHistory.contains(pairStr);
    }

    public boolean isExistsPair(PairRequest pairRequest) {
        Pair pair = pairRepository.get(pairRequest.getMission());
        return !pair.isEmpty(pairRequest.course);
    }

    public List<List<String>> findByPairRequest(PairRequest pairRequest) {
        Pair pair = pairRepository.get(pairRequest.getMission());
        if(pair == null || pair.isEmpty(pairRequest.course)){
            throw new PairStateException(PAIR_DOESNT_EXISTS);
        }
        List<List<String>> viewByPairRequest = pairRepository.get(pairRequest.getMission())
                .findViewByPairRequest(pairRequest);
        return viewByPairRequest;
    }

    public void reset() {
        crewMatchingHistory = new HashSet<>();
        for(Mission key : this.pairRepository.keySet()){
            pairRepository.put(key,new Pair());
        }
    }

    public void rematchInit(PairRequest pairRequest) {
        pairRepository.get(pairRequest.getMission()).reset(pairRequest.course);
        for(String key : crewMatchingHistory){
            String sameKey = "_" + pairRequest.level.name() + "_" + pairRequest.getMission().name() + "_"
                    + pairRequest.course.name();
            if(key.contains(sameKey)){
                crewMatchingHistory.remove(key);
            }
        }
    }
}
