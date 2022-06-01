import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSV_Reader {

    public ArrayList<Book> reader(String path) {

        String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";

        ArrayList<Book> allBooks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] items = line.split(cvsSplitBy);

                // ---------------------------------------------

                //1. Crea un array con los g�neros del libro actual...
                String[] genres = items[3].split(" ");

                //2. Crea el libro...
                Book aux = new Book(items[0], items[1], items[2], genres);

                allBooks.add(aux);


                // ---------------------------------------------

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allBooks;



    }


}
