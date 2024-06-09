package presentation;
import logic.Controller;

import java.io.File;
import java.util.Scanner;

public class gui {
    private Scanner scanner;
    private Controller controller;
    private boolean run;

    private static int maxTries = 3;

    public gui() {
        this.scanner = new Scanner(System.in);
        this.controller = null;
        this.run = false;
    }

    public void initSystem(){
        System.out.println("Welcome to the inventory management system");
        boolean valid = false;
        while (!valid) {
            System.out.println("Choose to initiate the system with a new inventory or load an existing one:");
            System.out.println("1 - initiate a new inventory");
            System.out.println("2 - load an existing inventory");
            switch (scanner.nextInt()) {
                case 1: {
                    valid = true;
                    this.controller = new Controller();
                    break;
                }
                case 2: {
                    boolean validPath = false;
                    String path = null;
                    while (!validPath) {
                        System.out.println("Enter configuration file path:");
                        path = this.scanner.nextLine();
                        if (!new File(path).isFile()) {
                            System.out.println("Invalid path, it might not exist or is a directory");
                        } else {
                            validPath = true;
                        }
                    }

                    valid = true;
                    this.controller = new Controller(path);
                    break;
                }
                default: {
                    System.out.println("Invalid choice, please choose one of the given options");
                }
            }

            this.run = true;
        }

        mainLoop();
    }

    private void mainLoop(){
        while (this.run) {
            String notifications = this.controller.getLowStockNotifications();
            if (notifications != null) {
                System.out.println(notifications);
            }

            // Print the main menu
            System.out.println("Choose an action:");
            System.out.println("1 - report an item sale");
            System.out.println("2 - report of a defected item");
            System.out.println("3 - make an ordering report");
            System.out.println("4 - make an inventory report");
            System.out.println("5 - make a defected and expired items report");
            System.out.println("6 - switch to manager mode and system settings");
            System.out.println("7 - exit system");

            // Take user input
            switch (scanner.nextInt()) {
                case 1:
                    reportSale();
                    break;
                case 2:
                    reportDefective();
                    break;
                case 3:
                    orderReport();
                    break;
                case 4:
                    inventoryReport();
                    break;
                case 5:
                    expiredReport();
                    break;
                case 6:
                    managerMode();
                    break;
                case 7:
                    exitProgram();
                    break;
                default:
                    System.out.println("Your choice isn't valid, please choose from the option below:");
            }
        }
    }

    private void exitProgram() {
        System.out.println("exiting program... Goodbye!");
        this.run = false;
    }

    private void managerMode() {
        int triesCounter = 0;
        boolean authenticated = false;
        while (!authenticated && triesCounter < maxTries) {
            System.out.println("Please enter password: ");
            String password = scanner.nextLine();
            boolean approved = this.controller.authenticatePassword(password);
            if (approved) {  // password is approved
                authenticated = true;
                updateSettings();
            } else {
                System.out.println("Password wasn't approved.\nTry again (Y/y) or return to main menu (N/n)?");
                String action = scanner.nextLine();
                if (!action.toLowerCase().equals("y")){
                    System.out.println("Returning to main menu..\n");
                    return;
                }
            }

            triesCounter++;
        }

        if (!authenticated) {
            System.out.println("Exceeded the allowed number of trys.");
        }
    }

    private void updateSettings() {
    }

    private void expiredReport() {
        String expiredReport = this.controller.getExpiredDefectiveReport();
        System.out.println(expiredReport);
    }

    private void inventoryReport() {
        System.out.println("Enter the categories you want to see the inventory report for: ");
        String categories = scanner.nextLine();
        String inventoryReport = this.controller.getInventoryReport(categories);
        System.out.println(inventoryReport);
    }

    private void orderReport() {
        // String orderReport = this.controller.getOrderReport();
        // System.out.println(orderReport);
    }

    private void reportDefective() {
        String choice = "y";
        while (choice.equals("y")){
            System.out.println("Enter the id of the defective item: ");
            int defectedItemId = scanner.nextInt();
            String status = this.controller.reportDefective(defectedItemId);
            System.out.println(status);
            System.out.println("Report on another item? (y/n)");
            choice = scanner.nextLine().toLowerCase();
        }
    }

    private void reportSale() {
        System.out.println("Enter the item id: ");
        int soldItem = scanner.nextInt();
        String status = this.controller.updateItemStatus(soldItem, "SOLD");
        System.out.println(status);
    }
}
