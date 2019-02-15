import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.sql.Date;
import java.util.Scanner;



/**
 * Created by Denislav on 2/13/2019.
 */
public class Main {

    public static void main (String[] args){
        displayResults();
    }


    /**
    * Used to parse the CSV input file line by line
    * Returns a collection (ArrayList) where each element is an object of the Entry class
    * Null values are converted to today's date
    *
    *@param filename the name of the input file
    */
    private static ArrayList<Entry> inputReader(String filename){
        ArrayList<Entry> input = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] lineOfInput = line.split(", ");
                if (lineOfInput[3].equalsIgnoreCase("null")){
                    lineOfInput[3] = LocalDate.now().toString();
                }
                input.add(new Entry(Integer.parseInt(lineOfInput[0]), Integer.parseInt(lineOfInput[1]), Date.valueOf(lineOfInput[2]), Date.valueOf(lineOfInput[3])));
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException e) {
            System.out.println("Error reading file");
        }
       return input;
    }

    /**
     *Calculate the overlapping days of two Entry objects
     *@param entry1
     *@param entry2
     *@return int = the overlapping days
     * */
    private static int calculateCollabTime(Entry entry1, Entry entry2){
        if (entry1.getDateFrom().before(entry2.getDateFrom())){
            if (entry1.getDateTo().after(entry2.getDateTo())){
                return getTimeBetweenSQLDates(entry2.getDateFrom(),entry2.getDateTo());
            } else {
                return getTimeBetweenSQLDates(entry2.getDateFrom(),entry1.getDateTo());
            }

        }
        else if (entry1.getDateFrom().after(entry2.getDateFrom())){
            if (entry1.getDateTo().before(entry2.getDateTo())){
                return getTimeBetweenSQLDates(entry1.getDateFrom(),entry1.getDateTo());
            } else {
                return getTimeBetweenSQLDates(entry1.getDateFrom(),entry2.getDateTo());
            }
        } else if (entry1.getDateFrom().equals(entry2.getDateFrom()) && entry1.getDateTo().equals(entry2.getDateTo())){
            return getTimeBetweenSQLDates(entry1.getDateFrom(),entry1.getDateTo());
        }
        return-1;
    }

    /**
     * A utility method used to get the time difference between two java.sql.Date Date objects
     * @param date1
     * @param date2
     * @return int = number of days between the two Date objects
     * */

    private static int getTimeBetweenSQLDates(Date date1, Date date2){
        long temp = ChronoUnit.DAYS.between(LocalDate.parse(date1.toString()),LocalDate.parse(date2.toString()));
        int result = (int)temp;
        return result;
    }

    /**
     * Goes through all the entries and generates a Map where each
     * pair is the key and the days worked together is the value
     *
     * @param entries - the List generated after parsing the input file
     * @return pairCollaborations map - where the biggest value will be the solution to the task
     * */
    private static Map<Pair, Integer> findAllPairs(List<Entry> entries) {
        Map<Integer, List<Entry>> projects = new HashMap<>();
        // go through each line of input (Entry) and group together instances of projects with the same projectID
        for (Entry entry : entries) {
            int projectID = entry.getProjectID();
            if (!projects.containsKey(projectID)) {
                List<Entry> projectInstances = new ArrayList<>();
                projectInstances.add(entry);
                projects.put(projectID, projectInstances);
            } else {
                List<Entry> projectInstances = projects.get(projectID);
                projectInstances.add(entry);
            }
        }
        // go through each list of instances of the same project and store the days worked together for each pair of employees
        HashMap<Pair, Integer> pairCollaborations = new HashMap<>();
        for (List<Entry> instances : projects.values()) {
            // store days worked together for each project in a temporary map
            HashMap<Pair, Integer> daysTogether = new HashMap<>();
            for (int i = 0; i < instances.size(); i++) {
                for (int l = i + 1; l < instances.size(); l++) {
                    Entry first = instances.get(i);
                    Entry second = instances.get(l);
                    int collabTime = calculateCollabTime(first, second);
                    if (collabTime > 0) {
                        Pair couple = new Pair(first.getEmployeeID(), second.getEmployeeID());
                        couple.addProjectID(first.getProjectID());
                        if (!daysTogether.containsKey(couple)) {
                            daysTogether.put(couple, collabTime);
                        } else {
                            int lastCollabTime = daysTogether.get(couple);
                            lastCollabTime += collabTime;
                            daysTogether.remove(couple);
                            daysTogether.put(couple, lastCollabTime);
                        }
                    }
                }
            }
            // store days worked together for each project in a map outside the scope of the loop
            pairCollaborations.putAll(daysTogether);
        }
        return pairCollaborations;
    }

    /**
     * A simple user interface which asks the user for the input file name
     * and displays the pair(s) who worked together the longest.
     *
     * */
    private static void displayResults () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("l------------------------------------------------------------------|");
        System.out.println("|    Make sure to place the input file in the project directory.   |");
        System.out.println("l------------------------------------------------------------------|");
        System.out.println("          Please enter the name of the input file: ");
        String s = scanner.next();
        ArrayList<Entry> entries = inputReader(s);
        // create and populate the map of pairs and days worked
        Map<Pair, Integer> couples = findAllPairs(entries);
        System.out.println();
        System.out.println("   The pair(s) that worked together the longest: ");
        System.out.println();
        // go through the map and display the biggest value(s)
        Map.Entry<Pair, Integer> maxEntry = null;
        for (Map.Entry<Pair, Integer> entry : couples.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >= 0)
            {
                maxEntry = entry;
                System.out.println(maxEntry);
            }
        }
    }
}
