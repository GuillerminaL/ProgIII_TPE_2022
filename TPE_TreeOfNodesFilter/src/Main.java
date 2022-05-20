public class Main {

    public static void main(String[] args) {


        String [] items = new String[4];
        items[0] = "drama policial fantastico arte";

        String[] generos = items[0].split(" ");
        for (int i = 0; i < generos.length; i++) {
            System.out.println(generos[i]);
        }

    }
}
