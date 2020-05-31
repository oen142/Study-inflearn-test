package thejavatest.javatest;

public class Study {


    private StudyStatus studyStatus = StudyStatus.COMPLETE;
    private int limit = -1;
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
}
