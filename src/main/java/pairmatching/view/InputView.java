package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.dto.PairRequest;
import pairmatching.exception.PairException;

public class InputView {

    private static final String INVALID_METHOD_MESSAGE = "유효하지 않은 기능입니다.";
    private static final String INVALID_MISSION_INPUT = "유효하지않은 과정,레벨,미션 입력입니다.";
    private static final String INVALID_INIT_INPUT = "예,아니오로 입력해주세요.";

    public static String inputMethod() {
        OutputView.printSelectMethod();
        String method = readLine();
        validateMethod(method);
        return method;
    }

    private static void validateMethod(String method) {
        if(method == null){
            throw new PairException(INVALID_METHOD_MESSAGE);
        }
        if(method.equals("1") || method.equals("2") || method.equals("3")){
            return;
        }
        if(method.equals("Q")){
            return;
        }
        throw new PairException(INVALID_METHOD_MESSAGE);
    }

    private static String readLine() {
        return Console.readLine();
    }

    public static PairRequest inputPairRequest() {
        OutputView.printMissionList();
        String string = readLine();
        return getValidatePairRequest(string);
    }

    private static PairRequest getValidatePairRequest(String string) {
        if(string == null || trimedStringArr(string).length != 3){
            throw new PairException(INVALID_MISSION_INPUT);
        }
        String[] inputArr = trimedStringArr(string);
        if(!Level.isExists(inputArr[1])){
            throw new PairException(INVALID_MISSION_INPUT);
        }
        Level level = Level.getFromString(inputArr[1]);
        if(!Mission.isExists(level.getLevelIdx(),inputArr[2]) || !Course.isExists(inputArr[0])){
            throw new PairException(INVALID_MISSION_INPUT);
        }
        return new PairRequest(Course.getFromString(inputArr[0]),
                level,
                Mission.getFromString(level.getLevelIdx(),inputArr[2]));
    }

    private static String[] trimedStringArr(String string) {
        String[] stringArr = new String[3];
        String[] currentArr = string.split(",");
        for (int i = 0; i < 3; i++) {
            stringArr[i] = currentArr[i].trim();
        }
        return stringArr;
    }

    public static String inputInitiatePairMatching() {
        String string = readLine();
        validateInitiatePairMatching(string);
        return string;
    }

    private static void validateInitiatePairMatching(String string) {
        if(string.equals("네")){
            return;
        }
        if(string.equals("아니오")){
            return;
        }
        throw new PairException(INVALID_METHOD_MESSAGE);
    }
}

