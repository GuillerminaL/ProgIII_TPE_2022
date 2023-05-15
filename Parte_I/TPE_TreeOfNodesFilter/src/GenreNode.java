import TPETry.Book;

import java.util.ArrayList;

public class GenreNode {

    String name;
    ArrayList<Book> books;
    GenreNode left;
    GenreNode right;

    public GenreNode(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.left = null;
        this.right = null;
    }

    public GenreNode(String name, Book book) {
        this.name = name;
        this.books = new ArrayList<Book>();
        this.addBook(book);
        this.left = null;
        this.right = null;
    }

    public void setLeft(GenreNode left) {
        this.left = left;
    }

    public void setRight(GenreNode right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public boolean addBook(Book book){
        return this.books.add(book);
    }
}
