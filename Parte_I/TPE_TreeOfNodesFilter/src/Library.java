import TPETry.Book;

import java.util.ArrayList;

public class Library {

    ArrayList<GenreNode> genresNodes;

    public Library(ArrayList<GenreNode> genresNodes) {
        this.genresNodes = genresNodes;
    }

    public boolean addNode(GenreNode node) {
        if(!this.genresNodes.contains(node)) {
            return this.genresNodes.add(node);
        }
        return false;
    }

    public void addBook(String[] genres, Book book) {
        for (String genre: genres) {
            boolean added = false;
            for (int i = 0; i < this.genresNodes.size() && !added; i++) {
                if (genresNodes.get(i).getName().equals(genre)) {
                    genresNodes.get(i).addBook(book);
                    added = true;
                }
            }
            GenreNode aux = new GenreNode(genre, book);
        }
    }

}
