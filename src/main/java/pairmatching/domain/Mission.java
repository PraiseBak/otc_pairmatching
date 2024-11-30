package pairmatching.domain;

public enum Mission {
    CAR_RACE(1,"자동차경주"),
    LOTTO(1,"로또"),
    BASEBALL(1,"숫자야구게임"),

    SHOPPING_BOX(2,"장바구니"),
    PAYMENT(2,"결제"),
    SUBWAY(2,"지하철노선도"),

    OPTI(4,"성능개선"),
    DEPLOY(4,"배포")
    ;

    private final String mission;
    private final int levelIdx;

    Mission(int levelIdx,String mission)
    {
        this.levelIdx = levelIdx;
        this.mission = mission;
    }

    public static boolean isExists(int levelIdx, String string) {
        for(Mission m : Mission.values()){
            if(m.mission.equals(string) && m.levelIdx == levelIdx){
                return true;
            }
        }
        return false;
    }

    public static Mission getFromString(int levelIdx, String string) {
        for(Mission m : Mission.values()){
            if(m.mission.equals(string) && m.levelIdx == levelIdx){
                return m;
            }
        }
        return null;
    }
}
