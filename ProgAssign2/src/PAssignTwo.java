/****************************************************
 *  Program Title: Binary Search Tree Operations    *
 *  Author: Travis Munyer                           *
 *  Class: CSCI3320, Summer 2021                    *
 *  Assignment #2                                   *
 ****************************************************/

import java.util.Scanner;

/**
 * Class for programming assignment two.
 * Note: Algorithms that require interaction with the BinaryNodes have been added to the BinarySearchTree.java
 * file to keep the implementation consistent.
 */
public class PAssignTwo {
    /**
     * The main method.
     * @param args Not used.
     */
    public static void main(String[] args) {
        int selection, nodes;
        BinarySearchTree treeRoot = new BinarySearchTree();
        while (true){
            selection = showOptionsAndGetSelection();
            switch (selection){
                case 1:
                    treeRoot = constructTree();
                    break;
                case 8:
                    System.exit(0);
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    if (treeRoot.isEmpty()){
                        System.out.println("You must construct the tree first.");
                        break;
                    }
                    switch (selection){
                        case 2:
                            System.out.print("Print in descending order: ");
                            treeRoot.printTreeDescending();
                            System.out.println();
                            break;
                        case 3:
                            nodes = treeRoot.numLeaves();
                            System.out.println("Number of leaf nodes: " + nodes);
                            break;
                        case 4:
                            nodes = treeRoot.numOneChildNodes();
                            System.out.println("Number of nodes with exactly one child: " + nodes);
                            break;
                        case 5:
                            nodes = treeRoot.numTwoChildrenNodes();
                            System.out.println("Number of nodes with exactly two children: " + nodes);
                            break;
                        case 6:
                            System.out.print("Print in level-order: ");
                            treeRoot.levelOrder();
                            System.out.println();
                            break;
                        case 7:
                            int[] kValues = getK1AndK2();
                            System.out.print("Nodes between k1 and k2: ");
                            treeRoot.printBetween(kValues[0], kValues[1]);
                            break;
                    }
            }
        }
    }

    /**
     * Gets input string from user and converts into a BinarySearch tree.
     * @return User-specified BinarySearchTree.
     */
    public static BinarySearchTree constructTree(){
        String intStringTemp;
        Scanner scan = new Scanner(System.in);
        BinarySearchTree root;

        while (true){
            System.out.print("Enter initial elements (integers seperated by spaces): ");
            intStringTemp = scan.nextLine();
            root = convertIntStringToBST(intStringTemp);
            if (root != null) break;
            System.out.println("Invalid input, please enter integers seperated by spaces.");
        }

        return root;
    }

    /**
     * Converts a string of integers seperated by spaces into a BinarySearchTree.
     * @param intString The string of integers seperated by spaces.
     * @return If intString format is correct, returns specified BinarySearchTree. Otherwise, returns null.
     */
    public static BinarySearchTree convertIntStringToBST(String intString){
        if (intString == null) return null;

        String[] tempStrArray;
        Integer tempInt;
        BinarySearchTree root = new BinarySearchTree();

        // Remove leading and trailing whitespaces and reduce multi-spaces to a single space.
        intString = intString.trim().replaceAll(" +", " ");
        tempStrArray = intString.split(" ");

        for (int i = 0; i < tempStrArray.length; i++){
            tempInt = tryParseInt(tempStrArray[i]);

            // If any conversions fail, return null.
            if (tempInt == null) return null;

            root.insert(tempInt);
        }

        return root;
    }

    /**
     * Gets k1 and k2 values from user.
     * @return A int[] array, where int[0] is k1 and int[1] is k2.
     */
    public static int[] getK1AndK2(){
        int[] kValues = new int[2];
        String temp;
        Integer tempInt;
        Scanner scan = new Scanner(System.in);

        while(true){
            System.out.print("Enter k1 value: ");
            temp = scan.next();
            tempInt = tryParseInt(temp);

            if (tempInt != null) kValues[0] = tempInt;

            scan = new Scanner(System.in);
            System.out.print("Enter k2 value: ");
            temp = scan.next();
            tempInt = tryParseInt(temp);

            if (tempInt != null) kValues[1] = tempInt;

            if (kValues[0] < kValues[1]) break;

            System.out.println("K1 must be less than k2.");
        }

        return kValues;
    }

    /**
     * Show menu options and get user selection.
     * @return An int representing user menu selection.
     */
    public static int showOptionsAndGetSelection() {
        Integer selection;
        String temp;
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Enter choice [1-8] from menu below: ");
            System.out.println("1) Construct a tree ");
            System.out.println("2) Print tree in a descending order ");
            System.out.println("3) Print number of leaves in tree ");
            System.out.println("4) Print the number of nodes in T that contain only one child ");
            System.out.println("5) Print the number of nodes in T that contain only two children ");
            System.out.println("6) Print the level order traversal of the tree ");
            System.out.println("7) Print all elements in the tree between k1 and k2 ");
            System.out.println("8) Exit program \n");
            System.out.print("Choose an option: ");

            temp = scan.next();
            selection = tryParseInt(temp);
            System.out.println();

            if (selection != null){
                switch (selection){
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        return selection;
                }
            }

            System.out.println("Please select a shown option.");
        }
    }

    /**
     * A utility method to safely convert a string to integer, similar to the built-in c# function.
     * @param value String value for conversion attempt.
     * @return The converted integer or null if input is incorrect.
     */
    private static Integer tryParseInt(String value){
        try{
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex){
            return null;
        }
    }
}
