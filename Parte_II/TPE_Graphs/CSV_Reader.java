package TPE_ParteII;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CSV_Reader {


    public ArrayList<String[]> reader(String path) {
        String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<String[]> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] items = line.split(cvsSplitBy);
                entries.add(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

}

