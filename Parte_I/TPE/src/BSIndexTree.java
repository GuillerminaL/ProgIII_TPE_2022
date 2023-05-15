import java.util.ArrayList;
import java.util.List;

public class BSIndexTree<T extends Comparable<T>> {

    private T value;
    List<Book> books;
    private BSIndexTree<T> left;
    private BSIndexTree<T> right;

    public BSIndexTree(T value) {
        this.setValue(value);
        this.books = new ArrayList<>();
        this.setLeftTree(null);
        this.setRightTree(null);
    }

    /*-------------------------------------------------------------------*/


    public List<Book> getBooksByGenre(T value) {
        if(this.hasElement(value)) {
            return this.privateGetBooks(value);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Book> privateGetBooks(T value){
        if(this.value.equals(value)) {
            return new ArrayList<>(books);
        } else {
            if (this.value.compareTo(value) > 0) {
                return this.left.privateGetBooks(value);
            } else {
                return this.right.privateGetBooks(value);
            }
        }
    }

    public void addBook(T[] values, Book book) {
        boolean added = false;
        int i = 0;
        while(i < values.length && !added) {
            if (this.value.equals(values[i])) {
                this.books.add(book);
                added = true;
            }
            i++;
        }
        if(this.left != null) this.left.addBook(values, book);
        if(this.right != null) this.right.addBook(values, book);
    }


    /*-----------------------------------------------------------------------*/

    private void setValue(T value) { this.value = value;    }

    public T getValue() { return this.value;    }

    private void setLeftTree(BSIndexTree<T> tree) {
        this.left = tree;
    }

    private void setRightTree(BSIndexTree<T> tree) {
        this.right = tree;
    }

    public boolean hasElement(T el) {
        if(this.value != null) {
            if(this.value.equals(el)) return true;
            if(this.value.compareTo(el) > 0) {
                if(this.left != null) return this.left.hasElement(el);
            } else {
                if(this.right != null) return this.right.hasElement(el);
            }
        }
        return false;
    }

    public boolean isEmpty() {   //??
        return this.getValue() == null && this.getLeftTree() == null && this.getRightTree() == null;
    }

    public boolean insert(T el) {
        if(el != null && !hasElement(el)) {
            if(this.isEmpty()) {                //??
                this.value = el;
                return true;
            }
            return privateInsert(el);
        }
        return false;
    }

    private boolean privateInsert(T el) {
        if(this.value.compareTo(el) > 0) {
            if(this.left == null) {
                setLeftTree(new BSIndexTree<>(el)); //si es nulo, crea el arbol
                return true;
            }
            return this.left.privateInsert(el);
        } else {
            if(this.right == null) {
                setRightTree(new BSIndexTree<>(el));
                return true;
            }
            return this.right.insert(el);
        }
    }



    private BSIndexTree<T> getLeftTree() {
        return this.left;
    }

    private BSIndexTree<T> getRightTree() {
        return this.right;
    }


    /*-----------------------------------------------------------------------------*/

    public int getHeight() {
        if(this.isEmpty()) return -1;
        return getHeight(0);
    }

    private int getHeight(int height) {
        if(this.left == null && this.right == null) return height;
        int leftHeight = 0;
        int rightHeight = 0;
        if(this.left != null) {
            leftHeight = this.getLeftTree().getHeight(height +1);
        }
        if(this.right != null) {
            rightHeight = this.getRightTree().getHeight(height+1);
        }
        return Math.max(rightHeight, leftHeight);
    }

    public int getLeftTreeHeight() {
        if(this.left == null) return -1;
        return this.left.getHeight();
    }

    public int getRightTreeHeight() {
        if(this.right == null) return -1;
        return this.right.getHeight();
    }


    /*-------------------------  delete method -------------------------------------------*/

    public boolean delete(T el) {
        if(el == null) return false;
        return privateDelete(el);
    }

    private boolean privateDelete(T el) {
        if(this.value.equals(el)) {
            if(this.left == null && this.right == null) {         //Si no tiene hijos (anula su valor)...
                this.value = null;
                return true;
            } else if(this.right != null) {  //Si tiene rama derecha (tenga izq o no)...
                return replaceFromRight();      //(sube el extremo derecho de la izq)
            } else {
                return replaceFromLeft();     //Si tiene sólo rama izquierda...
            }
        } else {
            if(this.value.compareTo(el) > 0) {
                if (this.left != null) {
                    boolean deleted = this.left.privateDelete(el);
                    if(this.left.getValue() == null) this.left = null;
                    return deleted;
                }
            } else {
                if(this.right != null) {
                    boolean deleted = this.right.privateDelete(el);
                    if (this.right.getValue() == null) this.right = null;
                    return deleted;
                }
            }
            return false;
        }
    }

    private boolean replaceFromRight() {
        BSIndexTree<T> aux;
        if(this.right.getLeftTree() == null) {     //Si el hijo derecho no tiene hijo izq, lo puentea
            aux = this.right.getRightTree(); //Guardo el ´rabol derecho
            this.setValue(this.right.getValue());       //Cambio el valor
            this.right = aux;                               //Le pongo el árbol derecho
        } else {                                    //Si el hijo derecho tiene algo a la izquierda, busca el extremo
            aux = this.right.getExtremeLeftTree(); //trae sólo el valor
            this.setValue(aux.getValue());
        }
        return true;
    }

    private boolean replaceFromLeft() {
        BSIndexTree<T> aux;
        if(this.left.getRightTree() != null) {     //Si el árbol izq tiene hijo der, lo busca y lo sube
            aux = this.left.getExtremeRightTree(); //Si el hijo derecho tiene hijo izq, busca el extremo
            this.setValue(aux.getValue());
        } else {                                    //El hijo izq no tiene hijo derecho...
            aux = this.left.getLeftTree();  //Guarda el nieto izq
            this.left.setLeftTree(null);
            this.setValue(this.left.getValue());             //Cambia su valor por el de su hijo
            this.setLeftTree(aux);                              //Se pone el nieto como hijo
        }
        return true;
    }

    private BSIndexTree<T> getExtremeLeftTree() {
        if(this.left.getLeftTree() != null) return this.left.getExtremeLeftTree();
        BSIndexTree<T> aux = this.getLeftTree();
        if(this.left.getRightTree() == null) { //this.left.getLeftTree() == null  Si el hijo izq no tiene hijos...
            this.left = null;
        } else { //this.left.getRightTree() != null    Si el hijo izq tiene un hijo derecho...
            this.setLeftTree(aux.getRightTree());
            aux.setRightTree(null);
        }
        return aux;
    }

    private BSIndexTree<T> getExtremeRightTree() {
        if(this.right.getRightTree() != null) return this.right.getExtremeRightTree();
        else {
            BSIndexTree<T> aux = this.getRightTree();
            if(this.right.getLeftTree() == null) { //Si el hijo izq no tiene hijos...
                this.right = null;
            } else { //Si el hijo der tiene un hijo izq...
                this.setRightTree(aux.getLeftTree());
                aux.setLeftTree(null);
            }
            return aux;
        }
    }

    /*---------------------------- end of delete method -------------------------------------------*/

    public List<T> getLongestBranch() {
        List<T> list = new ArrayList<>();
        int leftHeight = 0;
        if(this.left != null) leftHeight = this.left.getHeight();
        if(this.right != null) {
            if(this.right.getHeight() >= leftHeight) {              //Si es mayor O IGUAL, retorna la derecha
                return this.right.getList(list);
            }
        } else if(leftHeight > 0) {
            return this.left.getList(list);
        }
        return list;
    }

    private List<T> getList(List<T> list) {
        list.add(this.value);                           //se agrega y busca si hay siguientes...
        if(this.left == null && this.right == null) {   //Si no tiene hijos... retorna
            return list;
        }
        if(this.left != null) {
            this.left.getList(list);
        }
        if(this.right != null) {
            this.right.getList(list);
        }
        return list;
    }


    /*--------------------------------------------------*/

    public List<T> getFrontier() {
        List<T> list = new ArrayList<>();
        return getFrontier(list);
    }

    private List<T> getFrontier(List<T> list) {
        if(this.left == null && this.right == null) {   //Si no tiene hijos... (es frontera)
            list.add(this.value);                           //se agrega y retorna...
            return list;
        }
        if(this.left != null) {
            this.left.getFrontier(list);
        }
        if(this.right != null) {
            this.right.getFrontier(list);
        }
        return list;
    }


    /*----------------------------------------------*/

    public List<T> getElemAtLevel(int searchlevel) {
        if(searchlevel >= 0 && searchlevel <= this.getHeight()) {        //Si existe el nivel...
            List<T> list = new ArrayList<>();
            if(!this.isEmpty()) {
                if(searchlevel == 0) list.add(this.value);
                else return this.privateGetElemAtLevel(list, searchlevel, 0);
            }
            return list;
        }
        return null;
    }

    private List<T> privateGetElemAtLevel(List<T> list, int searchlevel, int actualLevel) {
        if(searchlevel == actualLevel) list.add(this.value);   //se agrega y retorna...
        if(this.left != null) {
            this.left.privateGetElemAtLevel(list, searchlevel, actualLevel + 1 );
        }
        if(this.right != null) {
            this.right.privateGetElemAtLevel(list, searchlevel, actualLevel + 1 );
        }
        return list;
    }

    /*-----------------------------------------------------*/
    public T getMaxElem() {
        return privateGetMaxElem();
    }

    private T privateGetMaxElem() {
        if(this.right == null) return this.value;
        else {
            return this.right.privateGetMaxElem();
        }
    }

    /*---------------------- print methods ----------------------------------------*/

    public void preOrderPrint() {
        if(this.isEmpty()) System.out.println("Empty tree");
        else privatePreOrderPrint();
    }

    private void privatePreOrderPrint() {
        if(this.value == null) System.out.print("N ");
        else System.out.print(this.value + " ");
        if(this.left == null) System.out.print("- ");
        else this.left.preOrderPrint();
        if(this.right == null) System.out.print("- ");
        else this.right.preOrderPrint();
    }

    /*--------------------------------------------------------*/

    public void printPosOrder() {
        if(this.isEmpty()) System.out.println("Empty tree");
        privatePrintPosOrder();
    }

    private void privatePrintPosOrder() {
        if(this.left != null) this.left.privatePrintPosOrder();
        else System.out.print("- ");
        if(this.right != null) this.right.privatePrintPosOrder();
        else System.out.print("- ");
        if(this.value == null) System.out.print("- ");
        else System.out.print(this.value + " ");
    }

    /*--------------------------------------------------------*/

    public void printInOrder() {
        if(this.isEmpty()) System.out.println("Empty tree");
        privatePrintInOrder();
    }

    private void privatePrintInOrder() {
        if(this.left != null) this.left.privatePrintInOrder();
        else System.out.print("- ");
        if(this.value != null) System.out.print(this.value + " ");
        else System.out.print("- ");
        if(this.right != null) this.right.privatePrintInOrder();
        else System.out.print("- ");
    }


}
