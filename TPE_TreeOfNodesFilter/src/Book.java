import java.util.Arrays;

public class Book {

    String name;
    String author;
    String pages;
    String[] genres;

    public Book(String name, String author, String pages, String[] genres) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
    }

    @Override
    public String toString() {
        return /*"The book that you have selected is : " + '\'' +*/
                "Name='" + name + '\'' +
                ", Author='" + author + '\'' +
                ", Pages='" + pages + '\'' +
                ", Genres=" + Arrays.toString(genres) +
                '}';
    }
}
