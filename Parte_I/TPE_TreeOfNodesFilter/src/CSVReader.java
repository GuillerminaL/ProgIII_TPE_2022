import TPETry.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {
        String csvFile = "C:/Users/pablo/Downloads/datasets/dataset1.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] items = line.split(cvsSplitBy);

                // ---------------------------------------------
                //Crea el libro...
                String[] genres = items[3].split(" ");
                Book aux = new Book(items[0], items[1], items[2], genres);
//                Library.addBook(genres, aux);

                System.out.println(aux);
                // ---------------------------------------------

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


