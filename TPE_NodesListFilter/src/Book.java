import java.util.Arrays;

public class Book {
    private String name;
    private String author;
    private String pages;
    private String[] genres;

    public Book(String name, String author, String pages, String[] genres) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPages() {
        return pages;
    }

    public String[] getGenres() {
        String []gen =genres;
        return  gen;
    }

    @Override
    public String toString() {
        return  "Name='" + name + '\'' +
                ", Author='" + author + '\'' +
                ", Pages='" + pages + '\'' +
                ", Genres=" + Arrays.toString(genres);
    }
}
