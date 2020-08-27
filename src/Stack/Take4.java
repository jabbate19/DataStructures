package Stack;

import java.util.ArrayList;
import java.util.Scanner;

public class Take4 {
    // Primary Answer Holder
    static Stack main;
    // While finding operations for that part of Order of Operations (if any), anything skipped goes in storage.
    static Stack store;

    // For this first simple case we will require that the expression:
    // 1) be fully parenthesized and valid syntax
    // 2) only have a single digit for an operand
    // For example:  (3*(7-5)) =>  (3*2) => (6) => 6
    //
    // The first go of the algorithm is pretty easy.
    // We can ignore spaces and '('.
    //
    // The characters input are ASCII codes.  Java will convert these to integers.
    // For example: '0' is code 48, '1' is 49. '2' is 50, ...
    // By subtracting the character '0' from a character digit you can convert to an integer value:
    // '4' (code 52) - '0' (code 48) = 4

    public static void main(String[] args) {
        // For my reference
        System.out.println( (int)'^' + " " +(int)'*' + " " + (int)'/' + " " +(int)'+' + " " +(int)'-' + ' ' + (int)'0');
        System.out.println( '^' + " " +'*' + " " + '/' + " " +'+' + " " +'-' + ' ' + '0');


        main = new Stack(100);
        store = new Stack(100);

        // For the multi digit calculator
        int val = 0;
        int places = 0;
        boolean runningVal = false;

        Scanner scanner = new Scanner( System.in );
        scanner.useDelimiter( "" ); // Scanner will return one char at a time with no delimiter

        System.out.print( "Enter expression: " );
        String input = scanner.nextLine();
        //System.out.println(input);
        //System.out.println(input.length());

        // Scans the entire line, starting at the end so the first pop() is the beginning
        for( int i = input.length()-1; i >= 0 ; i-- ) {

            char c = input.charAt( i );
            if ( Character.isDigit( c ) ) {
                // Combines multiple digit numbers
                // runningVal = currently processing a multi-digit number, so it can advance the place with a multiple of 10
                if ( runningVal ) {
                    val += Math.pow(10,places) * (c - '0');
                } else {
                    val += c - '0';
                }
                runningVal = true;
                places++;

                // In case it ends with a number
                if( i == 0 ){
                    main.push( val );
                }

            } else if ( runningVal ) {
                //System.out.println("Pushing " + val);
                // If we get a non-digit, push the number we've been working on, reset the multi-digit calculator and add the non-digit
                main.push( val );
                val = 0;
                places = 0;
                runningVal = false;
                main.push( c );
            }else{
                // Random Character
                main.push( c );
            }

        }
        // See Stack > print
        main.print();

        fullSimplify( main, store );
        System.out.println("Ending Value: " + main.pop() );
    }

    public static boolean isTurn( int p, int op ) {
        System.out.println( p + " " + op );
        if (p == 0) {
            return op == '^';
        } else if (p == 1) {
            return op == '*' || op == '/';
        } else if (p == 2) {
            return op == '+' || op == '-';
        } else {
            return false;
        }

    }

    public static int process( int op1, int operand, int op2 ) {

        if (operand == '+') {
            return op1 + op2;
        } else if (operand == '-') {
            return op1 - op2;
        } else if (operand == '*') {
            return op1 * op2;
        } else if (operand == '/') {
            return op1 / op2;
        } else if (operand == '^') {
            return (int) Math.pow(op1, op2);
        } else {
            return 0;
        }
    }

    public static boolean isAnswer( Stack st ){
        boolean foundNonZero = false;
        for( int i : st.stack ){
            if( i != 0 ){
                if( foundNonZero ){
                    return false;
                }else{
                    foundNonZero = true;
                }
            }
        }
        return true;
    }

    public static int getAnswer( Stack st ){
        for( int i : st.stack ){
            if( i != 0 ){
                return i;
            }
        }
        return 0;
    }

    public static void dump( Stack from, Stack to ){
        while( from.nextIndex != 0 ){
            to.push( from.pop() );
            //System.out.println("Dump Result");
            //to.print();
        }
    }

    public static void fullSimplify( Stack s, Stack storage ){
        // depth and parentheses was broken by me
        int depth = 0;

        // Says which stages of PEMDAS to do, 0 = E, 1 = MD, 2 = AS
        int priority = 0;

        // Goes off when we have an answer
        boolean foundAnswer = false;

        int op1 = s.pop();
        int operand = s.pop();
        int op2 = s.pop();
        while( !foundAnswer && priority < 3 ){

            // isTurn checks if its the operand's turn in the order of operations
            if( isTurn( priority, operand ) ){
                //Debug println
                System.out.println( "Priority: " + priority );
                System.out.println( op1 + " " + operand + " " + op2 );

                // Process does the math
                s.push(process( op1, operand, op2 ));
                // dump puts everything from storage back into the normal stack to restart the scan
                dump( storage, s );

                s.print();
                // Try and get the next thing to do
                // If it can't find it, then there is not 3 int in the stack, so the last one standing is our answer.
                // When it tries to get the 3, if it gets ArrayOutOfBoundsException, it has the answer
                try {
                    op1 = s.pop();
                    operand = s.pop();
                    op2 = s.pop();
                }catch( Exception e ){
                    foundAnswer = true;
                }

            }else{
                // Move on to the next numbers and operand, saving the last ones used for storage
                storage.push( op1 );
                storage.push( operand );
                op1 = op2;
                try {
                    operand = s.pop();
                    op2 = s.pop();
                }catch( Exception e ){
                    // If we made it to the end of the stack, restart on the next order of operations
                    System.out.println("Priority " + priority + " Over");
                    priority++;
                    s.nextIndex = 0;
                    s.push(op2);
                    dump( storage, s );
                    op1 = s.pop();
                    operand = s.pop();
                    op2 = s.pop();
                    s.print();
                }
            }

        }
        s.nextIndex = 1;
    }











}

