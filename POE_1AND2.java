package com.mycompany.poe_1and2;

import javax.swing.JOptionPane;

public class POE_1AND2 {

    private static String username = "right or wrong username";
    private static String password = "right or wrong password";
    private static String name;
    private static String surname;

    private static int numTasks;
    private static int totalHours = 0;
    private static int taskCounter = 0;

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            // Display the menu and get user's choice
            String choice = JOptionPane.showInputDialog(
                    "Choose an option:\n1. Register\n2. Login\n3. Exit"
            );

            // Before doing anything user makes a choice to register, log in, or exit.
            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    // Check if user has logged in before allowing task operations
                    if (!loggedIn()) {
                        JOptionPane.showMessageDialog(null, "Please log in first.");
                        continue; // Continue loop to show menu again
                    }
                    // If logged in, proceed to task operations
                    taskOperations();
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unrecognized choice. Please choose from the choices; '1', '2', or '3'.");
            }
        }
    }

    public static void register() {
        name = JOptionPane.showInputDialog("Please enter your name");
        surname = JOptionPane.showInputDialog("Please enter your surname");

        do {
            username = JOptionPane.showInputDialog("Enter your username");
            if (!checkUserName(username)) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
            }
        } while (!checkUserName(username));
        JOptionPane.showMessageDialog(null, "Username successfully captured.");

        do {
            password = JOptionPane.showInputDialog("Enter your password");
            if (!checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            }
        } while (!checkPasswordComplexity(password));
        JOptionPane.showMessageDialog(null, "Password successfully captured.");

        // After the correct credentials have been accepted, these messages display the user's information
        JOptionPane.showMessageDialog(null, "Name: " + name + "\nSurname: " + surname + "\nUsername: " + username + "\nPassword: " + password);
        JOptionPane.showMessageDialog(null, "Registration successful");
        JOptionPane.showMessageDialog(null, "NEXT step, LOGGING IN !!");

        // Call the login method after registration
        loggedIn();
    }

    // Login method
    public static boolean loggedIn() {
        boolean loginUser = false;
        // this while loop will loop until the user successfully logs in or chooses the canceling option
        while (!loginUser) {
            String enteredUsername = JOptionPane.showInputDialog("Enter your username:");
            // when the user clicks Cancel, exit the loop
            if (enteredUsername == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return false;
            }
            String enteredPassword = JOptionPane.showInputDialog("Enter your password:");
            // when the user clicks Cancel, exit the loop
            if (enteredPassword == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return false;
            }
            // this "IF" statement checks if entered username and password match the registered user's credentials
            if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                JOptionPane.showMessageDialog(null, "Welcome " + name + ", " + surname + " it is great to see you again.");
                loginUser = true; // Setting "loginUser" to true so that it exits the loop
            } else {
                JOptionPane.showMessageDialog(null, "Username or Password Incorrect. Please try again.");
            }
            
            //i am calling this method here for the effect of part 2 begining after login
           taskOperations();
        }
          
        return true;
    }

    // Method so to handle task operations after login
    public static void taskOperations() {
        boolean exitTasks = false;
      JOptionPane.showMessageDialog(null, "Welcome to EasyKanbun");
        while (!exitTasks) {
            
            // Display task menu and get the user's choice
            String choice = JOptionPane.showInputDialog(
                    "Choose an option:\n1. Add Task\n2. Show Report\n3. Quit"
            );

            // Perform task operations based on user's choice
            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Report feature is still in development. Coming soon.");
                    break;
                case "3":
                    exitTasks = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unrecognized choice. Please choose from the choices; '1', '2', or '3'.");
            }
        }
    }

    // a method to add a task
    public static void addTask() {
        numTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks you wish to enter:"));
        // if statement for checking if task limit is reached
        if (taskCounter >= numTasks) {
            JOptionPane.showMessageDialog(null, "Task limit reached.");
            return;
        }

        // Task details input
        String taskName = JOptionPane.showInputDialog("Enter task name:");
        String taskDescription = JOptionPane.showInputDialog("Enter task description (max 50 characters):");
        
        // Validate task description length
        if (taskDescription.length() > 50) {
            JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters.");
            return;
        }
        //it will return to first input until description is 50 char or less
        String developerFirstName = JOptionPane.showInputDialog("Enter developer's first name:");
        String developerLastName = JOptionPane.showInputDialog("Enter developer's last name:");
        int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter task duration in hours:"));

        

        // Generate task ID
        String taskID = generateTaskID(taskName, developerLastName);

        // Task status menu
        String[] statusOptions = {"To Do", "Done", "Doing"};
        int statusChoice = JOptionPane.showOptionDialog(null, "Select task status:", "Task Status",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);

        // Display task details
        String taskStatus = statusOptions[statusChoice];
        JOptionPane.showMessageDialog(null, "Task Status: " + taskStatus +
                "\nDeveloper: " + developerFirstName + " " + developerLastName +
                "\nTask Number: " + taskCounter +
                "\nTask Name: " + taskName +
                "\nTask Description: " + taskDescription +
                "\nTask ID: " + taskID.toUpperCase() +
                "\nTask Duration: " + taskDuration + " hours");

        // Accumulate total hours
        totalHours += taskDuration;

        // Increment task counter
        taskCounter++;
    }
// Method to check if username meets the requirements
   public static boolean checkUserName(String username) {
        return username.length() == 5 && username.contains("_");
    }

    // Method to check if password meets the requirements
    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 && !password.equals(password.toLowerCase()) && password.matches(".*\\d.*") && !password.matches("[A-Za-z0-9 ]*");
    }

    // Task ID generation method
    public static String generateTaskID(String taskName, String developerLastName) {
        String taskID = taskName.substring(0, 2).toUpperCase() + ":" + taskCounter + ":" + developerLastName.substring(Math.max(0, developerLastName.length() - 3)).toUpperCase();
        return taskID;
    }
}