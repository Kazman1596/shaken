package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        File inputFile = new File("recipe-list.txt");
        File outputFile = new File("recipe-list2.txt");
        HashMap<String, String> recipeBook = new HashMap<>();
        try(Scanner scanner = new Scanner(inputFile); PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile, true))) {
            while(scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                String[] recipe = nextLine.split(" \\| ");
                String title = recipe[0];
                String ingredients = "";
                String instruction = recipe[recipe.length - 1].replaceAll("\\|", "").replaceAll("- ", "").trim();

                for (int i = 1; i < recipe.length-1; i++) {
                    ingredients += recipe[i] + " ";
                }
                writer.println(title + " | " + ingredients.replaceAll("•", "").trim() + " | " + instruction);
            }
        } catch(Exception ex) {
            System.out.println("Problem updating recipe");
        }

    }

}