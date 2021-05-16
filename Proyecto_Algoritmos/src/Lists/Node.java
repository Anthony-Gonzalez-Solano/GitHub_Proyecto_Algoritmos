package Lists;

public class Node {
    public Object data; //el dato almancenado en el nodo
    public Node next; //el apuntador al sgte nodo
    public Node prev; //el apuntador al nodo anterior
    
    //Constructor
    public Node(Object data){
        this.data = data;
        this.next = this.prev = null;
    }
    
    public Node(){
        this.data = null;
        this.next = this.prev = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    
    
}
