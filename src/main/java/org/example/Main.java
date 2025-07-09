package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static HashMap<String, Integer> storeInventory = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;
        boolean menuRunning = true;

        do{
            System.out.println("\n\n--- Grocery Inventory Menu ---");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. Check Product");
            System.out.println("4. Update Stock");
            System.out.println("5. Remove Product");
            System.out.println("6. Exit");
            System.out.print("Please enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1: //View Inventory
                    viewInventory();
                    break;
                case 2: //Add Product
                    System.out.print("What is the name of the product: ");
                    String productToAdd = sc.nextLine();
                    int addQuantity = 0;
                    boolean validQuantity = false; // Flag for the inner do-while loop

                    do {
                        System.out.print("How much are we going to add: ");
                        try {
                            addQuantity = sc.nextInt();
                            if (addQuantity > 0) {
                                validQuantity = true;
                            } else {
                                System.out.println("Quantity must be a positive number. Please try again.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a whole number for quantity.");
                            sc.next();
                        } finally {
                            sc.nextLine();
                        }
                    } while (!validQuantity);

                    addProduct(productToAdd, addQuantity);

                    break;
                case 3: //Check Product
                    System.out.print("Enter the name of the product to check: ");
                    String checkProductName = sc.nextLine();
                    checkProduct(checkProductName);
                    break;
                case 4: //Update Stock
                    System.out.print("Enter the name of the product to update: ");
                    String updateProductName = sc.nextLine();
                    int updateQuantity = -1;
                    boolean validUpdateQuantity = false;

                    do{
                        System.out.print("How much is the new quantity of the product?: ");
                        try{
                            updateQuantity = sc.nextInt();
                            if(updateQuantity >= 0){
                                validUpdateQuantity = true;
                            }else{
                                System.out.println("Invalid quantity, must be above -1");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("The input must be a whole number.");
                        } finally {
                            sc.nextLine();
                        }
                    }while(!validUpdateQuantity);

                    updateProduct(updateProductName, updateQuantity);
                    break;
                case 5: //Remove Product
                    System.out.print("Enter the name of the product to remove: ");
                    String removeProductName = sc.nextLine();
                    removeProduct(removeProductName);
                    break;
                case 6: //Exit
                    System.out.println("Exiting the inventory program. Goodbye!");
                    menuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. please enter a number between 1 - 6");
                    break;

            }

        }while(menuRunning);

    }

    public static void addProduct(String name, int quantity){
        storeInventory.put(name, quantity);
        System.out.printf("Product %s x%d has been successfully added.", name, quantity);
    }

    public static void checkProduct(String name){
        int amount = storeInventory.get(name);
        System.out.printf("%s has qty: %d", name, amount);
    }

    public static void updateProduct(String name, int quantity){
        if (!storeInventory.containsKey(name)){
            System.out.printf("There is not such product that exists.");
            return;
        } else{
            storeInventory.put(name, quantity);
            System.out.printf("Product %s has been successfully updated to %d.", name, quantity);
        }
    }

    public static void removeProduct(String name){
        if (!storeInventory.containsKey(name)){
            System.out.printf("There is not such product that exists.");
            return;
        } else{
            storeInventory.remove(name);
            System.out.printf("Product %s has been successfully removed.", name);
        }
    }

    public static void viewInventory(){
        for (Map.Entry<String, Integer> entry: storeInventory.entrySet()){
            System.out.println("\n\n--- All Grocery Items ---");
            System.out.println(entry.getKey() + " x" + entry.getValue());
        }
    }

}