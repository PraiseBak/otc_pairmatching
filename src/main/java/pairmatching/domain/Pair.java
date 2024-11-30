package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;
import pairmatching.dto.PairRequest;

public class Pair {

    List<List<String>> frontEndPairList;
    List<List<String>> backendPairList;


    public Pair(){
        this.frontEndPairList = new ArrayList<>();
        this.backendPairList = new ArrayList<>();
    }

    public void setMatchingList(List<List<String>> matchingList, Course course) {
        if(course == Course.BACKEND){
            backendPairList = matchingList;
            return;
        }
        frontEndPairList = matchingList;
    }

    public boolean isEmpty(Course course){
        if(course == Course.BACKEND){
            return backendPairList.isEmpty();
        }
        return frontEndPairList.isEmpty();
    }

    public List<List<String>> findViewByPairRequest(PairRequest pairRequest) {
        if(pairRequest.course == Course.BACKEND){
            return backendPairList;
        }
        return frontEndPairList;
    }

    public void reset(Course course) {
        if(course == Course.BACKEND){
            this.backendPairList = new ArrayList<>();
            return;
        }
        this.frontEndPairList = new ArrayList<>();
    }
}
