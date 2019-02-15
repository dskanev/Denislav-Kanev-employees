import java.util.HashSet;
import java.util.Set;

/**
 * Created by Denislav on 2/14/2019.
 */
public class Pair {
    private int employee1;
    private int employee2;
    private Set<Integer> commonProjects;


    public Pair (int eID1, int eID2) {
        this.employee1 = eID1;
        this.employee2 = eID2;
        this.commonProjects = new HashSet<>();
    }

    /*
    * Override hashCode() and equals() to avoid adding logically equal entries;
    * Used IntelliJ's auto-generate algorithm as most up-to-date
    * */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (employee1 != pair.employee1) return false;
        if (employee2 != pair.employee2) return false;
        return commonProjects != null ? commonProjects.equals(pair.commonProjects) : pair.commonProjects == null;
    }

    @Override
    public int hashCode() {
        int result = employee1;
        result = 31 * result + employee2;
        result = 31 * result + (commonProjects != null ? commonProjects.hashCode() : 0);
        return result;
    }


    public boolean addProjectID(int projectID) {
        return commonProjects.add(projectID);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "employee1=" + employee1 +
                ", employee2=" + employee2 +
                ", days together "
                ;
    }


}
