package Lists;

public class LinkedStack
{
    private Node top;
    private int count;
    
    public LinkedStack(){
       this.top =null;
       this.count = 0;
    }
    
    public int size() {
        return count;
    }

    public void clear() {
       top = null;
       count = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public Object peek() throws StackException {
        if(isEmpty()){               
            throw new StackException("ArrayStack is empty");
        }  
        return top.data;
    }

    public Object top() throws StackException {
        if(isEmpty()){               
            throw new StackException("ArrayStack is empty");
        }  
        return top.data;
    }

    public void push(Object element) throws StackException {
        Node newNode = new Node(element);
        if(isEmpty()){
            top = newNode;
        }else{
            newNode.next = top;
            top =  newNode;
        }
        count++;
    }

    public Object pop() throws StackException {
        if(isEmpty()){               
            throw new StackException("ArrayStack is empty");
        }
        Object element = top.data;
        top = top.next;
        count--;
        return element;
    }

    @Override
    public String toString() {
        String result = "Linked Stack\n";
        try {
            LinkedStack aux = new LinkedStack();
            while(!isEmpty()){
                result+=peek()+"\n";
                aux.push(pop());
            }
            while(!aux.isEmpty()){
                this.push(aux.pop());
            }
        } catch (StackException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    
    
}
