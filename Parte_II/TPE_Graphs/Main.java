package TPE_ParteII;


public class Main {

    public static void main(String[] args) {

        String path = "C:/Users/pablo/Desktop/PROGIII/TPE_ParteII/Datasets_EtapaII/dataset1.csv";

        Library libre = new Library(path);

        libre.getNMostSearchedGenresAfter(3, "arte");

        libre.getHighestSequenceFrom("arte");

        libre.getRelatedGenresTo("arte");

    }

}
