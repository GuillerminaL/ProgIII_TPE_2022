import java.util.ArrayList;
import java.util.Iterator;

public class Library {

    private ArrayList<Book> allBooks;
    private ArrayList<GenreNode> genreNodesList;

    public Library(String path) {
        this.allBooks = readBooks(path);
        this.genreNodesList = new ArrayList<>();
    }

    public ArrayList<Book> getAllBooks() {
        return new ArrayList<>(allBooks);
    }

    private ArrayList<Book> readBooks(String path){
        CSV_Reader reader = new CSV_Reader();
        ArrayList<Book> allBooks = reader.reader(path);
        allBooks.remove(0);
        return allBooks;
    }

    public void indexByGenre(){
        for (Book book : allBooks) {                //0(n)
            String [] genre= book.getGenres();
            for (String gene : genre) {             //0(n)
                if(genreNodesList.isEmpty()){
                    GenreNode aux= new GenreNode(gene);
                    aux.add(book);
                    this.genreNodesList.add(aux);
                }else{
                    addGenreNode(gene,book);         //Recorrido de lista e inserción  0(n)
                }
            }
        }
    }

    private void addGenreNode(String gene, Book book) {
        boolean hasit = false;
        Iterator<GenreNode> it = this.genreNodesList.iterator();
        while(it.hasNext() && !hasit) {
            GenreNode node = it.next();
            if(node.getGenre().equals(gene)){
                node.add(book);
                hasit =true;
            }
        }
        if(!hasit) {
            GenreNode aux= new GenreNode(gene);
            aux.add(book);
            genreNodesList.add(aux);
        }
    }

    public ArrayList<Book> searcher(String gen){
        ArrayList<Book> aux = new ArrayList<>();
        for (GenreNode node : genreNodesList) {     //0(n)
            if(node.getGenre().equals(gen)){
                aux = node.getBooksByGenre();
                return aux;
            }
        }
        return aux;
    }



}
