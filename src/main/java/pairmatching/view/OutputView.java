package pairmatching.view;

public class OutputView {
    private final static String INPUT_METHOD = "기능을 선택하세요.\n1. 페어 매칭\n2. 페어 조회\n3. 페어 초기화\nQ. 종료";
    private final static String MISSION_INFO =
            "#############################################\n"
            + "과정: 백엔드 | 프론트엔드\n"
            + "미션:\n"
            + "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n"
            + "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n"
            + "  - 레벨3: \n"
            + "  - 레벨4: 성능개선 | 배포\n"
            + "  - 레벨5: \n"
            + "############################################\n"
            + "과정, 레벨, 미션을 선택하세요.\n"
            + "ex) 백엔드, 레벨1, 자동차경주";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String DUPLICATE_PAIR_MATCHING = "매칭 정보가 있습니다. 다시 매칭하시겠습니까?\n네 | 아니오";
    private static final String PAIR_VIEW_START = "페어 매칭 결과입니다.";
    private static final String COMPLETE_INIT = "초기화 되었습니다.";

    public static void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public static void printSelectMethod() {
        printMessage(INPUT_METHOD);
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printMissionList() {
        System.out.println(MISSION_INFO);

    }

    public static void printDuplicatePairMatching() {
        System.out.println(DUPLICATE_PAIR_MATCHING);
    }

    public static void printPairView(String s) {
        System.out.println(PAIR_VIEW_START);
        System.out.println(s);
    }

    public static void printInitComplete() {
        System.out.println(COMPLETE_INIT);
    }
}
