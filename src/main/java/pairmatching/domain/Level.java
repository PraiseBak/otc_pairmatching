package pairmatching.domain;

public enum Level {
    LEVEL1("레벨1",1),
    LEVEL2("레벨2",2),
    LEVEL3("레벨3",3),
    LEVEL4("레벨4",4),
    LEVEL5("레벨5",5);

    public int getLevelIdx() {
        return levelIdx;
    }

    private final int levelIdx;
    private String name;

    Level(String name, int levelIdx) {
        this.name = name;
        this.levelIdx = levelIdx;
    }

    public static boolean isExists(String string) {
        for(Level level : Level.values()){
            if(level.name.equals(string)){
                return true;
            }
        }
        return false;
    }

    public static Level getFromString(String string) {
        for(Level level : Level.values()){
            if(level.name.equals(string)){
                return level;
            }
        }
        return null;
    }
}