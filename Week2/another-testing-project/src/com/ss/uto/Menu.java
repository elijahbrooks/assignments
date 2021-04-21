package com.ss.uto;


import java.util.Scanner;

public class Menu {
    private String[] options;

    public Menu(String[] options) {
        this.options = options;
    }

    public String getInputAsString(){
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < options.length ; i++) {
            System.out.println("  " + (i + 1) + ") " + options[i]);
        }
        Integer choice = scan.nextInt() - 1;
        if((choice + 1 > 0) && (choice < options.length) && (options[choice] != null))
            return options[choice];
        else
            System.out.println("Invalid Input: " + (choice+1));

        return null;
    }

    public Integer getInput(){
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < options.length ; i++) {
            System.out.println("  " + (i + 1) + ") " + options[i]);
        }
        Integer choice = scan.nextInt() -1;
        if((choice + 1 > 0) && (choice < options.length) && (options[choice] != null))
            return choice;
        else {
            System.out.println("Invalid Input: " + (choice+1));
        }

        return null;
    }

    public String inputMenu(){
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < options.length ; i++) {
            System.out.println("o " + options[i]);
        }
        return scan.nextLine().trim();
    }

}
