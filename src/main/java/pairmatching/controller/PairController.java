package pairmatching.controller;

import pairmatching.dto.PairRequest;
import pairmatching.service.PairService;

public class PairController {
    private final PairService pairSerive;

    public PairController(PairService pairService) {
        this.pairSerive = pairService;
    }

    public void init() {
        pairSerive.init();
    }

    public boolean checkPairMatching(PairRequest pairRequest){
        return pairSerive.isExistsPairMatching(pairRequest);
    }

    public String pairMatching(PairRequest pairRequest,boolean rematch) {
        String pairMatchingResult = pairSerive.pairMatching(pairRequest,rematch);
        return pairMatchingResult;
    }

    public String pairView(PairRequest pairRequest) {
        String pairViewResult = pairSerive.getPairViewByPairRequest(pairRequest);
        return pairViewResult;
    }

    public void reset() {
        pairSerive.reset();
    }
}
