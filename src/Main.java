import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;



/**
 * Created by Denislav on 2/13/2019.
 */
public class Main {

    public static void main (String[] args){
        ArrayList<Entry> entries = inputReader("input.txt");

        }





/* // Read and parse file data

1.	Sort list by project number
2.	Sort list by employee number

3.	Pick employee
4.	Pick project
5.	Pick next employee with same project
6.	Create a record with a key == the names of the employees
7.	Check if their timelines overlap
8.	Add the overlapping time to a list of common work time
a.	Repeat for all common projects
b.	Merge time ranges when finished, calculate number of days worked together
i.	Repeat for all employees
// Output the key of the record with highest number of days worked together
*/

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
                    System.out.println(lineOfInput[3]);
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
    

}
