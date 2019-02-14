/**
 * Created by Denislav on 2/14/2019.
 */

/*
* implements the Comparable interface to allow comparing objects of the class
*
* */

public class Collaboration implements Comparable<Collaboration> {

    private int employee1;
    private int employee2;
    private String projects;
    private int collabTime;

    public Collaboration (int e1ID, int e2ID, String projects, int collabTime){
        this.employee1 = e1ID;
        this.employee2 = e2ID;
        this.projects = projects;
        this.collabTime = collabTime;
    }
    /*
    * @Override compareTo() to find the longest collaboration time by comparison
    * */

    @Override
    public int compareTo(Collaboration collaboration) {
        return collaboration.collabTime - this.collabTime;
    }

    public int getEmployee1() {
        return employee1;
    }

    public int getEmployee2() {
        return employee2;
    }

    public String getProjects() {
        return projects;
    }

    public int getCollabTime() {
        return collabTime;
    }
}
