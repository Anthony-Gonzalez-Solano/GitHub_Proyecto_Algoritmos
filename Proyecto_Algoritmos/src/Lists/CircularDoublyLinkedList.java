package Lists;

public class CircularDoublyLinkedList {
    private Node first; //apunta al inicio de la lista dinamica
    private Node last; //apunta al ultimo nodo de la lista
    
    //Constructor                                      
    public CircularDoublyLinkedList(){
        this.first = null; this.last = null; //la lista todavia no existe
    }

    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Node aux = first;
        int count = 0;
        while(aux!=last){
            count++;
            aux = aux.next;
        }
        return count+1;
    }

    public void clear() {
        this.first = this.last = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public boolean contains(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Node aux = first;
        while(aux!=last){
            if(util.Utility.equals(aux.data, element)){
                return true;
            }
            aux = aux.next;
        }
        //verifica el ultimo nodo, por que el while no lo ve
        return util.Utility.equals(last.data, element);
    }

    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            this.first = newNode;
            this.last = newNode;
        }else{
         last.next = newNode;
         newNode.prev = last;
         last = newNode;
         
         last.next = first;
         first.prev = last;
        }
    }

    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            this.first = this.last = newNode;
        }
        newNode.next = first;
        first.prev = newNode;
        first = newNode;
        
        last.next = newNode; //enlace circular
        first.prev = last;
    }

    public void addLast(Object element) {
        add(element);
    }

    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        //CASO 1. LA LISTA ESTA VACIA
        if(isEmpty()){
            first = last = newNode;
        }else{
            //CASO 2. first.next es nulo, o no es nulo
            //y el elemento a insertar es menor al del inicio
            if(util.Utility.greaterT(first.data, element)){
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
                
            }else{
                //CASE 3. TODO LO DEMAS
                Node prev = first;
                Node aux = first.next;
                boolean added=false;
                while(aux!=last&&!added){
                    if(util.Utility.lessT(element, aux.data)){
                        prev.next = newNode;
                        newNode.prev = prev;
                        newNode.next = aux;
                        aux.prev = newNode;
                        added = true;
                    }
                    prev = aux;
                    aux = aux.next;
                }
                // si llega aqui se enlaza cuando aux = last
                if(util.Utility.lessT(element, aux.data)&&!added){
                    prev.next = newNode;
                    newNode.prev = prev;
                    newNode.next = aux;
                    aux.prev = newNode;
                }else if(!added){
                    aux.next = newNode;
                    newNode.prev = aux;
                    //muevo last al ultimo nodo
                    last = newNode;
                }
            }
        }
        //hago el enlace circular
        last.next = first;
        first.prev = last;
    }

    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
   
        //CASO 1. EL ELEMENTO A SUPRIMIR ES EL PRIMERO DE LA LISTA
        if(util.Utility.equals(first.data, element)){
            first = first.next;
        }else{
        //CASO 2. EL ELEMENTO A SUPRIMIR ESTA EN CUALQUIER OTRO NODO
            Node prev = first; //para dejar rastro, apunta al anterior de aux
            Node aux = first.next;
            while(aux!=last&&!util.Utility.equals(aux.data, element)){
                prev = aux; //un nodo atras de aux
                aux = aux.next;
            }
            //se sale del while cuando alcanza last
            //o cuando encuentra el elemento a suprimir
            
            
            if(util.Utility.equals(aux.data, element)){
                //desenlazo o desconecto el nodo
                prev.next = aux.next;
                aux.next.prev = prev;
            }
            //debo asegurarme que last apunte al ultimo nodo
            if(aux == last&&util.Utility.equals(aux.data, element)){
                last = prev;
            }
        }
        last.next = first;
        if(first==null){
            last=null;
        }else{
            first.prev = last;
        }
           
    }

    public Object removeFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Object element = first.data;
        first = first.next; //muevo el apuntador al sgte nodo
        first.prev = last;
        last.next = first;
        return element;
    }

    public Object removeLast() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Node aux = first;
        Node prev = first; //para dejar rastro, apunta al anterior de aux
        while(aux.next!=last){
            prev = aux; //un nodo atras de aux
            aux = aux.next;
        }
        //se sale del while cuando esta en el ultimo nodo
        Object element = aux.data; 
        last = prev;
        last.next = first;
        first.prev = last;
        return element;
    }

    public void sort() throws ListException {
         if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
       Node aux = null;
        for (int i = 1; i < size() ; i++) {
            for (int j = 1; j < size() ; j++) {
                         
                if (util.Utility.greaterT(getNode(j).getData(), getNode(j+1).getData())) {
 
                    aux = getNode(j);
                    if(j == 1){
                        first = getNode(j+1);
                        aux.next = getNode(j).next;
                        getNode(j).next = aux;
                        getNode(j).prev = last;
                        aux.prev = getNode(j);
                        last.next = first;
                    }else {
                        getNode(j-1).next = getNode(j+1);
                        aux.next = getNode(j).next;
                        getNode(j).next = aux;
                        getNode(j).prev = getNode(j-1);
                        aux.prev = getNode(j);
                    }
                }
            }
        }
    }

    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Node aux = first;
        int index = 1; //el primer nodo estara en el indice 1
        while(aux!=last){
            if(util.Utility.equals(aux.data, element)){
                return index; //ya lo encontro
            }
            index++;
            aux = aux.next; 
        }
        if(util.Utility.equals(aux.data, element)){
            return index;
        }
        return -1; //significa q el elemento no existe
    }

    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        return first.data;
    }

    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        return last.data;
    }

    public Object getPrev(Object element) throws ListException {
         if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Node aux = first;
        while (aux!=last) {
            if(util.Utility.equals(aux.getData(), element)){
                return aux.prev.data;
            }else{
                aux=aux.next;
            }   
        }
        if(util.Utility.equals(aux.getData(), element)){
            return aux.prev.data;
        }
       return -1;
    }

    public Object getNext(Object element) throws ListException {
         if(isEmpty()){
            throw new ListException("CircularDoublykLinkedList is empty");
        }
        Node aux = first;
        while (aux!=last) {
            if(util.Utility.equals(aux.data, element)){
                return aux.next.data;
            }else{
                aux=aux.next;
            }   
        }
        if(util.Utility.equals(aux.data, element)){
            return aux.next.data;
        }
       return -1;
    }

    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularDoublyLinkedLlist is empty");
        }
        Node aux = first;
        int i = 1; //el indice del primer elemento de la lista
        while(aux!=last){
            if(util.Utility.equals(i, index)){
                return aux; //ya lo encontro
            }
            i++;
            aux = aux.next; 
        }
        if(util.Utility.equals(i, index)){
            return aux;
        }
        
        return null; //si llega aqui, no encontro el nodo
    }

    @Override
    public String toString() {
        String result="CIRCULAR DOUBLY LINKED LIST\n";
        Node aux = first;
         //el aux es para moverme por la lista hasta el ult elemento
        while(aux!=last){
            result+=aux.data+"\n";
            aux = aux.next;
        }
        result+=aux.data+"\n";
        return result;
    }
    
    
    
}
