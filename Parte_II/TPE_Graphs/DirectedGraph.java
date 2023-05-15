package TPE_ParteII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DirectedGraph<T, I> {

    private HashMap<T, HashSet<Edge<T, I>>> vertexes;


    public DirectedGraph() {
        this.vertexes = new HashMap<>();
    }


    public void addVertex(T vertexId) {
        if(!this.vertexes.containsKey(vertexId)){
            this.vertexes.put(vertexId, new HashSet<>());
        }
    }

    public void addEdge(T vertexId1, T vertexId2, I tag) {
        this.addVertex(vertexId1);
        this.addVertex(vertexId2);
        Edge<T, I> aux = new Edge<>(vertexId1, vertexId2, tag);
        this.vertexes.get(vertexId1).add(aux);
    }


    public boolean containsVertex(T vertexId) {
        return this.vertexes.containsKey(vertexId);
    }


    public boolean containsEdge(T vertexId1, T vertexId2) {
        if(this.containsVertex(vertexId1) && this.containsVertex(vertexId2)) {
            for (Edge<T, I> a : this.vertexes.get(vertexId1)) {
                if (a.getEndVertex().equals(vertexId2)) return true;
            }
        }
        return false;
    }


    public Edge<T, I> getEdge(T vertexId1, T vertexId2) {
        if(this.containsEdge(vertexId1, vertexId2)) {
            for (Edge<T, I> a : this.vertexes.get(vertexId1)) {
                if (a.getEndVertex().equals(vertexId2)) return a;
            }
        }
        return null;
    }


    public void deleteEdge(T vertexId1, T vertexId2) {
        Edge<T, I> toDelete = this.getEdge(vertexId1, vertexId2);
        if(toDelete != null) {
            this.vertexes.get(vertexId1).remove(toDelete);
        }
    }


    public void deleteVertex(T vertexId) {
        if(this.containsVertex(vertexId)) {
            for(Iterator<Edge<T, I>> it = this.getEdges(); it.hasNext();) {
                Edge<T, I> a = it.next();
                if(a.getEndVertex().equals(vertexId)){
                    this.deleteEdge(a.getStartVertex(), vertexId);
                }
            }
            this.vertexes.remove(vertexId);
        }
    }


    public int vertexesQ() {
        return this.vertexes.size();
    }


    public int edgesQ() {
        return this.getEdgesSet().size();
    }

    public boolean isEmpty() {
        return this.vertexesQ() == 0;
    }

    public Iterator<T> getVertexes() {
        return this.vertexes.keySet().iterator();
    }

    public Iterator<T> getAdjs(T vertexId) {
        ArrayList<T> result = new ArrayList<>();
        if(this.containsVertex(vertexId)) {
            for(Iterator<Edge<T, I>> it = this.getEdges(vertexId); it.hasNext();) {
                result.add(it.next().getEndVertex());
            }
        }
        return result.iterator();
    }


    public Iterator<Edge<T, I>> getEdges() {
        return this.getEdgesSet().iterator();
    }

    private HashSet<Edge<T, I>> getEdgesSet() {
        HashSet<Edge<T, I>> aux = new HashSet<>();
        for (T v: vertexes.keySet()) {
            aux.addAll(this.getEdgesSet(v));
        }
        return aux;
    }


    public Iterator<Edge<T, I>> getEdges(T vertexId) {
        HashSet<Edge<T, I>> result = new HashSet<>();
        if(this.containsVertex(vertexId)) {
            result = this.getEdgesSet(vertexId);
        }
        return result.iterator();
    }

    public HashSet<Edge<T, I>> getEdgesSet(T verticeId) {
        return this.vertexes.get(verticeId);
    }

    /*----------------------------*/
    public int getTotalTagsAmount() {
        int total = 0;
        Iterator<Edge<T, I>> it = this.getEdges();
        while(it.hasNext()) { total += (int) it.next().getTag();}
        return total;
    }

    /*----------------------------*/

    public String toString() {
        return "Graph{" + "\n" +
                "vertexes(" + this.vertexesQ() + ")=" + vertexes.keySet() +
                "\n"  + "edges("  + this.edgesQ() + ")=" + this.getEdgesSet() +
                "\n" + '}' ;
    }

}


