package thejavatest.javatest.study;

public class Study {


    private StudyStatus studyStatus = StudyStatus.COMPLETE;
    private int limit = -1;
    private String name;


    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudyStatus getStatus() {
        return studyStatus;
    }

    public Study(int limit) {
        if(limit <0){
            throw new IllegalArgumentException("limit ..");
        }
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Study{" +
                "studyStatus=" + studyStatus +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
