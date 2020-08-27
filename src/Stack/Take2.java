package Stack;

import java.util.ArrayList;
import java.util.Scanner;

public class Take2 {
    static Stack s;
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
        System.out.println( (int)'^' + " " +(int)'*' + " " + (int)'/' + " " +(int)'+' + " " +(int)'-' + ' ' + (int)'0');
        System.out.println( '^' + " " +'*' + " " + '/' + " " +'+' + " " +'-' + ' ' + '0');
        s = new Stack(100);
        int depth = 0;
        // Says which stages of PEMDAS to do, 0 = E, 1 = MD, 2 = AS
        int priority = 0;
        // For the multi digit calculator
        int val = 0;

        int index = 0;
        boolean runningVal = false;

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(""); // Scanner will return one char at a time with no delimiter

        System.out.print("Enter expression: ");
        String input = scanner.next();  // Waits for input to be available, returns only the first char as a string
        while (true) {
            char c = input.charAt(0);
            // Combines multiple digit numbers
            if (Character.isDigit(c)) {
                if (runningVal) {
                    val *= 10;
                    val += c - '0';
                } else {
                    val += c - '0';
                }
                runningVal = true;
            } else if (runningVal) {
                s.push(val);
                val = 0;
                runningVal = false;
            }



            if (c == '(') {
                depth++;
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == ')') {

                if (c == ')') {
                    // Keep everything here until next priority, is used to push everything back into the stack in original order
                    ArrayList<Integer> storage = new ArrayList<>();

                    int op2 = s.pop();
                    int operand = s.pop() + '0';  // Add back '0' to get the operand character.
                    int op1 = s.pop();
                    int result = 0;
                    System.out.println( op1 + " " + (operand) + " " + op2 );
                    while( depth != 0 ){

                            System.out.println(op1 + " " + (operand) + " " + op2);
                            storage.add(op2);
                            storage.add(operand - '0');
                            System.out.println(op1 + " " + (operand) + " " + op2 + " is not priority");
                            op2 = op1;
                            try {
                                operand = s.pop() + '0';
                                op1 = s.pop();
                                System.out.println(op1 + " " + (operand) + " " + op2);

                            } catch (Exception e) {
                                System.out.println("No available operands of this priority");
                                //System.out.println(storage.size() - 1);
                                index = s.nextIndex;
                                s.nextIndex = 0;
                                storage.add(op1);
                                System.out.println("Size: " + (storage.size() - 1));
                                for (int i = storage.size() - 1; i >= 0; i--) {
                                    System.out.println(storage.get(i));
                                    s.push(storage.get(i));
                                }
                                storage = new ArrayList<>();
                                System.out.println("Moving to new priority");
                                priority++;

                                op2 = s.pop();
                                operand = s.pop() + '0';  // Add back '0' to get the operand character.
                                op1 = s.pop();



                        }
                    }


                    if( priority == 3 ) {
                        depth--;
                    }else if(isTurn(priority, operand)){

                        System.out.println(" Stack b4 ");

                        s.print();
                        System.out.println(" Storage b4 ");
                        for (int i = storage.size() - 1; i >= 0; i--) {
                            System.out.println(storage.get(i));
                        }
                        System.out.println("Processing " + op1 + " " + operand + " " + op2 + " in priority " + priority);
                        result = process(op1, operand, op2);
                        s.pop();
                        s.pop();
                        s.push(result);
                        for (int i = storage.size() - 1; i >= 0; i--) {
                            s.push(storage.get(i));
                        }
                        s.print();
                    }






                    if (depth == 0) {
                        System.out.println("Value: " + s.pop());
                    }
                } else
                    s.push(c - '0'); // Push the "value" on to the Stack.  By subtracting '0' we convert the char '1' to the integer 1 and so on.
            }
            // else anything else we throw away and ignore for now.

            input = scanner.next();
        }
    }

    public static boolean isTurn(int p, int op) {
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

    public static int process(int op1, int operand, int op2) {

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














    }

