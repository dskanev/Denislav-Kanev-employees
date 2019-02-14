import java.sql.Date;

/**
 * Created by Denislav on 2/13/2019.
 */
public class Entry {
    private int employeeID;
    private int projectID;
    private Date dateFrom;
    private Date dateTo;

    public Entry (int employeeID, int projectID, Date dateFrom, Date dateTo){
        this.employeeID = employeeID;
        this.projectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public int getProjectID() {
        return projectID;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    @Override
    public String toString() {
        return "" + employeeID + " " + projectID + " " + dateFrom + " " + dateTo;
    }
}
