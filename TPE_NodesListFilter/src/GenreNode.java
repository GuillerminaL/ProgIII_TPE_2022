import java.util.ArrayList;

public class GenreNode {

    String genre;
    //int value;
    ArrayList<Book> booksByGenre;

    public GenreNode(String genre) {
        this.genre = genre;
        this.booksByGenre=new ArrayList<Book>();
    }

    public String getGenre() {
        return genre;
    }

    public void add(Book book) {
        booksByGenre.add(book);
    }

    public ArrayList<Book> getBooksByGenre() {
        ArrayList<Book> copy = new ArrayList<>(this.booksByGenre);
        return copy;
    }

    @Override
    public String toString() {
        return this.getGenre();
    }

}
