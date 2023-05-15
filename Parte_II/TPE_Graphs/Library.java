package TPE_ParteII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Library {


    private DirectedGraph<String, Integer> graph;


    public Library(String path) {
        this.graph = new DirectedGraph<>();
        charge(read(path));
    }

    private ArrayList<String[]> read(String path){
        CSV_Reader reader = new CSV_Reader();
        return reader.reader(path);
    }

    private void charge(ArrayList<String[]> entries) {
        for (String[] s : entries) {
            Iterator<String> it = Arrays.stream(s).iterator();
            String current = it.next();
            graph.addVertex(current);
            while (it.hasNext()) {
                String next = it.next();
                Edge<String, Integer> edge = graph.getEdge(current, next);
                if(edge != null) {
                    edge.setTag(edge.getTag() + 1);
                } else {
                    graph.addEdge(current, next, 1);
                }
                current = next;
            }
        }
    }

    /*
    ● Servicio 1
        Obtener los N géneros más buscados luego de buscar por el género A...
     */
    public void getNMostSearchedGenresAfter(int n, String genre) {
        ArrayList<String> result = new ArrayList<>();
        int cont = 0;
        if(!graph.containsVertex(genre)) { cont = -1;}
        if(n <= 0) { cont = -2; }
        else {
            ArrayList<Edge<String, Integer>> aux = this.getAdjsInDescendentOrder(genre);
            if(aux.size() < n) {
                printResult(genre, -3, result);
            }
            for (int i = 0; i < n && i < aux.size(); i++) {
                Edge<String, Integer> e = aux.get(i);
                result.add(e.getEndVertex() + " (" + e.getTag() + " búsquedas)");
                cont++;
            }
        }
        printResult(genre, cont, result);
    }

    private ArrayList<Edge<String, Integer>> getAdjsInDescendentOrder(String genre) {
        ArrayList<Edge<String, Integer>> aux = new ArrayList<>(graph.getEdgesSet(genre));
        if(!aux.isEmpty()) Collections.sort(aux, Collections.reverseOrder());
        return aux;
    }


    /*
    ● Servicio 2
     A partir de un género A encontrar, en tiempo polinomial, la secuencia de géneros
    que más alto valor de búsqueda posee. Vamos a definir el valor de búsqueda de
    una secuencia como la suma de los arcos que la componen...
     */
    public void getHighestSequenceFrom(String genre) {
        if(!graph.containsVertex(genre)) printResult(genre, -1, new ArrayList<>());
        else {
            ArrayList<String> result = this.getHighestSequence(genre);
            if(result.size() == 1) printResult(genre, 0, new ArrayList<>());
            else {
                System.out.println("La secuencia con más alto nivel de búsqueda es: "
                        + "\n" + result + "\n");
            }
        }
    }

    private ArrayList<String> getHighestSequence(String genre) {
        ArrayList<String> result = new ArrayList<>();
        result.add(genre);
        ArrayList<Edge<String, Integer>> adjs = this.getAdjsInDescendentOrder(genre);
        if(!adjs.isEmpty()) {
            Edge<String, Integer> edge = adjs.get(0);
            String dest = edge.getEndVertex();
            return getHighestSequence(dest, result);
        }
        return result;
    }

    private ArrayList<String> getHighestSequence(String genre, ArrayList<String> result) {
        if(!result.contains(genre)) {
            result.add(genre);
            ArrayList<Edge<String, Integer>> adjs = this.getAdjsInDescendentOrder(genre);
            if(!adjs.isEmpty()) {
                Edge<String, Integer> edge = adjs.get(0);
                String dest = edge.getEndVertex();
                return getHighestSequence(dest, result);
            }
        }
        return result;
    }

    /*
    ● Servicio 3
    Obtener el graph únicamente con los géneros afines a un género A; es decir que,
    partiendo del género A, se consiguió una vinculación cerrada entre uno o más
    géneros que permitió volver al género de inicio.
     */
    public void getRelatedGenresTo(String genre) {
        DirectedGraph<String, Integer> result = this.getCycle(genre);
        if(result.isEmpty()) {
            printResult(genre, -1, new ArrayList<>());
        } else if(result.vertexesQ() == 1) {
            printResult(genre, 0, new ArrayList<>());
        } else {
            System.out.println("Grafo de géneros afines a " + genre + ": " + "\n" + result);
        }
    }

    public DirectedGraph<String, Integer> getCycle(String genre) {
        DirectedGraph<String, Integer> result = new DirectedGraph<>();
        if(graph.containsVertex(genre)){
            result.addVertex(genre);
            Iterator<String> it = graph.getAdjs(genre);
            while(it.hasNext()){
                String current = it.next();
                boolean found = getCycle(current, result, genre, genre);
                if(found) return result;
            }
            return result;
        }
        return result;
    }


    private boolean getCycle(String current, DirectedGraph<String, Integer> result, String endpoint, String last) {
        if(current.equals(endpoint)) {
            result.addEdge(last, current, 1);
            return true;
        } else {
            Iterator<String> it = graph.getAdjs(current);
            while(it.hasNext()){
                String actual = it.next();
                if(result.containsVertex(actual)) {
                    result.addEdge(last, endpoint, 1);
                    return true;
                }
                result.addEdge(last, current, 1);
                boolean found = getCycle(actual, result, endpoint, current);
                if(found) return true;
                else result.deleteEdge(last, current);
            }
        }
        return false;
    }

    private void printResult(String genre, int cont, ArrayList<String> result) {
        switch (cont) {
            case -3:
                System.out.println("\n" + "No existe la cantidad de géneros solicitados");
                break;
            case -2:
                System.out.println("\n" + "Debe ingresar un número mayor a 0" + "\n");
                break;
            case -1:
                System.out.println("\n" + "El género " + genre + " no existe" + "\n");
                break;
            case 0:
                System.out.println("\n" + "No hay búsquedas afines al género " + genre + "\n");
                break;
            case 1: System.out.println("El género más buscado luego de " + genre + " es: "
                    + result.get(0) + "\n");
                break;
            default:
                if(result.size() == 2) {
                    System.out.println("Los " + cont + " géneros más buscados luego de " + genre
                            + " son " + result.get(0) + " y " + result.get(1) + "\n");
                } else {
                    System.out.println("Los " + cont + " géneros más buscados luego de " + genre
                            + " son: " + result  + "\n");
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "Library { " +
                graph +
                '}';
    }
}



