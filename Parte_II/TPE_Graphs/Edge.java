package TPE_ParteII;

public class Edge<T, I> implements Comparable<Edge<T, I>> {


    private T startVertex;
    private T endVertex;
    private I tag;


    public Edge(T startVertex, T endVertex, I tag) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.tag = tag;
    }


    public T getStartVertex() {
        return startVertex;
    }

    public T getEndVertex() {
        return endVertex;
    }

    public I getTag() {
        return tag;
    }

    public void setTag(I tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this.endVertex == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<T, I> other = (Edge<T, I>) o;
        return (this.getStartVertex() == other.getStartVertex() &&
                this.getEndVertex() == other.getEndVertex() &&
                this.getTag() == other.getTag());
    }

    @Override
    public String toString() {
        return "{" +
                startVertex + " --> " +
                endVertex +
                " = " + tag +
                '}';
    }

    @Override
    public int compareTo(Edge<T, I> o) {
        int aux = (int) this.getTag() - (int) o.getTag();
        if (aux == 0) return 0;
        if (aux > 0) return 1;
        else return -1;
    }
}
