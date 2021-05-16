package Lists;

public class ArrayStack {

    private int top;
    private Object stack[];
    private int n;
    
    public ArrayStack(int n) {
        if(n<=0) System.exit(1);
        this.n = n;
        this.stack = new Object[n];
        this.top = -1;
    }

    public int size() {
        return top+1;
    }

    public void clear() {
        stack = new Object[n];
        top = -1;
    }

    public boolean isEmpty() {
        return top < 0;
    }

    public Object peek() throws StackException {
        if(isEmpty()){
            throw new StackException("ArrayStack is empty");
        }
        return stack[top];
    }

    public Object top() throws StackException {
        if(isEmpty()){
            throw new StackException("ArrayStack is empty");
        }
        return stack[top];      
    }

    public void push(Object element) throws StackException {
        top++;
        if(top==stack.length){
            top--;
            throw new StackException("ArrayStack is full");
        }else{
            stack[top] = element;
        }
    }

    public Object pop() throws StackException {
        if(isEmpty()){
            throw new StackException("ArrayStack is empty");
        }
        Object o = stack[top];
        stack[top] = null;
        top--;
        return o;
    }

    @Override
    public String toString() {
        ArrayStack as = new ArrayStack(this.n);
        String results="Array Stack\n";
        try {
            while(!this.isEmpty()){  
                results+= this.peek().toString()+"\n";
                as.push(this.pop());
            }
            while (!as.isEmpty()) {            
                    this.push(as.pop());
            }
        } catch (StackException ex) {
            System.out.println(ex.getMessage());
        }
        return results;
    }
    
    
}
