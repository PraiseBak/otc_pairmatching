package pairmatching;

import pairmatching.controller.PairController;
import pairmatching.dto.PairRequest;
import pairmatching.repository.PairRepository;
import pairmatching.service.PairService;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class Application {
    private final static PairController pairController = new PairController(new PairService(new PairRepository()));
    private static final String PAIR_MATCHING = "1";
    private static final String PAIR_VIEW = "2";
    private static final String PAIR_INIT = "3";
    private static final String EXIT = "Q";

    public static void main(String[] args) {
        pairController.init();
        while (true){
            String method = inputMethod();
            boolean isContinue = startMethods(method);
            if(!isContinue){
                break;
            }
        }
    }

    private static boolean startMethods(String method) {
        if(method.equals(PAIR_MATCHING)){
            pairMatching();
            return true;
        }
        if (method.equals(PAIR_VIEW)) {
            pairView();
            return true;
        }
        if(method.equals(PAIR_INIT)){
            pairInit();
            return true;
        }
        if(method.equals(EXIT)){
            return false;
        }
        return true;
    }

    private static String inputMethod() {
        while (true){
            try {
                return InputView.inputMethod();
            }catch (IllegalArgumentException e){
                OutputView.printError(e.getMessage());
            }
        }
    }

    private static void pairMatching() {
        boolean isEnd = false;
        while (!isEnd) {
            try{
                isEnd = requestPairMatching();
            }catch (IllegalArgumentException e){
                OutputView.printError(e.getMessage());
            }
        }
    }

    private static boolean requestPairMatching() {
        PairRequest pairRequest = InputView.inputPairRequest();
        if(pairController.checkPairMatching(pairRequest)){
            if(inputInitiatePairMatching()){
                OutputView.printMessage(pairController.pairMatching(pairRequest,true));
                return true;
            }
            return false;
        }
        OutputView.printMessage(pairController.pairMatching(pairRequest,false));
        return true;
    }

    private static boolean inputInitiatePairMatching() {
        while (true){
            try {
                OutputView.printDuplicatePairMatching();
                String inputString = InputView.inputInitiatePairMatching();
                return inputString.equals("네");
            }catch (IllegalArgumentException e){
                OutputView.printError(e.getMessage());
            }
        }

    }

    private static void pairInit() {
        pairController.reset();
        OutputView.printInitComplete();
    }

    private static void pairView() {
        while (true) {
            try{
                PairRequest pairRequest = InputView.inputPairRequest();
                OutputView.printPairView(pairController.pairView(pairRequest));
                return;
            }catch (IllegalArgumentException e){
                OutputView.printError(e.getMessage());
            }catch (IllegalStateException e){
                OutputView.printError(e.getMessage());
                return;
            }
        }

    }
}
