import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//        String path = "C:/Users/diego/Downloads/datasets/dataset1.csv";
        String path = "C:/Users/pablo/Desktop/PROGIII/datasets/dataset1.csv";

        Library libreria = new Library(path);

        long indexStartTime = System.nanoTime();
        libreria.indexByGenre();
        long indexEstimatedTime = System.nanoTime() - indexStartTime;


        long searchStartTime = System.nanoTime();
        ArrayList<Book> search = libreria.searcher("moda");
        long searchEstimatedTime = System.nanoTime() - searchStartTime;

//        for(Book book: search) {
//            System.out.println(book);
//        }

        System.out.println("Index estimated time: " + indexEstimatedTime);
        System.out.println("Search estimated time: " + searchEstimatedTime);

    }
}
