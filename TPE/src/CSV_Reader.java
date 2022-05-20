import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSV_Reader {

    public static void main(String[] args) {

        String csvFile = "C:/Users/pablo/Downloads/datasets/dataset1.csv";
        String line = "";
        String cvsSplitBy = ",";


        BSIndexTree<String> genresTree = new BSIndexTree<>(null);  //Árbol de búsqueda por género...
        ArrayList<String> genresIndex = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] items = line.split(cvsSplitBy);

                // ---------------------------------------------

                //1. Crea un array con los géneros del libro actual...
                String[] genres = items[3].split(" ");

                //2. Crea el libro...
                Book aux = new Book(items[0], items[1], items[2], genres);

                //3.Inicializa el árbol de búsqueda con los géneros y los indexa en un array...
                for (String genre: genres) {
                    genresTree.insert(genre);
                    if(!genresIndex.contains(genre)) genresIndex.add(genre);
                }

                //4. Agrega el libro al árbol de búsqueda...
                genresTree.addBook(genres, aux);

                // ---------------------------------------------

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*---------------------------- OUTS ------------------------------------------*/
        System.out.println("Árbol (Pre-order: ");
        genresTree.preOrderPrint();

        System.out.println(" ");
        System.out.println(" ");

        System.out.println("Índice de géneros: ");
        int cont = 0;
        for(String genre: genresIndex) {
            System.out.print(cont + "." + genre + ", ");
            cont++;
        }

        System.out.println(" ");
        System.out.println(" ");


        /*---- filter  ----*/
        String filter = genresIndex.get(1);

        List<Book> filtrados = genresTree.getBooksByGenre(filter);
        if(filtrados.isEmpty()) {
            System.out.println("Your search had no results");
        } else {
            System.out.println("Your search by " + filter + " had " + filtrados.size() + " results:");
            for (Book book:filtrados) {
                System.out.println(book.toString());
            }
        }

    }

}
