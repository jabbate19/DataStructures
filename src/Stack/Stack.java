package Stack;

public class Stack {
    int[] stack;
    int size;
    int nextIndex;

    public Stack( int size ) {
        stack = new int[size];
        this.size = size;
        this.nextIndex = 0;
    }

    public void push( int value ) {
        stack[nextIndex++] = value;
    }

    public int pop() {
        return stack[--nextIndex];
    }

    public int size() {
        return size;
    }

    public int nextIndex() {
        return nextIndex;
    }

    public void print(){
        for( int i = 0 ; i < stack.length; i++ ){
            if( i == nextIndex ){
                System.out.println( stack[i] + "<<<" );
            }else{
                System.out.println( stack[i] );
            }

        }
    }

    public static void main( String[] args ) {
        Stack s = new Stack(10);

        s.push( 1 );
        s.push( 2 );
        s.push( 3 );
        s.push( '+' );  // Converted to the ASCII for '+'

        if( s.size() != 10 )
            throw new RuntimeException();

        if( s.nextIndex() != 4 )
            throw new RuntimeException();

        if( s.pop() != '+' )
            throw new RuntimeException();

        if( s.nextIndex() != 3 )
            throw new RuntimeException();

        if( s.pop() != 3 )
            throw new RuntimeException();

        if( s.pop() != 2 )
            throw new RuntimeException();

        if( s.pop() != 1 )
            throw new RuntimeException();

        Stack s2 = new Stack(2);
        s2.push(1);
        s2.push(2);

        try {
            s2.push(3);
        } catch( ArrayIndexOutOfBoundsException e ) {
            System.out.println("Caught expected array exception");
            // Good this is expected
        }

        Stack s3 = new Stack(1);
        s3.push(1);
        s3.pop();

        try {
            s3.pop();
        } catch( ArrayIndexOutOfBoundsException e ) {
            System.out.println("Caught expected array exception");
            // Good this is expected
        }

        System.out.println("No unexpected errors encountered");
    }
}
