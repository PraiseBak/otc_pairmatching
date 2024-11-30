package pairmatching.domain;

public enum Course {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private String name;

    Course(String name) {
        this.name = name;
    }

    public static boolean isExists(String name) {
        for(Course course : Course.values()){
            if(course.name.equals(name)){
                return true;
            }
        }
        return false;
    }

    public static Course getFromString(String string) {
        for(Course course : Course.values()){
            if(course.name.equals(string)){
                return course;
            }
        }
        return null;
    }
}