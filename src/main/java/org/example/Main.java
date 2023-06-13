package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> titleArray = new ArrayList<String>();
        File titleFile = new File("recipe-titles.txt");

        try (Scanner scanner = new Scanner(titleFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                titleArray.add(line);
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong");
        }

        File recipeFile = new File("recipe-list.txt");
        File recipeBook = new File("recipes.txt");

        try (Scanner bodyScanner = new Scanner(recipeBook); PrintWriter writer = new PrintWriter(new FileOutputStream(recipeFile, false))) {
            for (int i = 0; i < titleArray.size() -1 ; i ++) {
                String currentTitle = titleArray.get(i);
                String nextTitle = titleArray.get(i + 1);
                StringBuilder description = new StringBuilder();
                while (bodyScanner.hasNextLine()) {
                    String currentLine = bodyScanner.nextLine();

                        if (currentLine.contains(nextTitle)) {
                            writer.println(currentTitle + " | " + description);
                            break;
                        }
                    description.append(" ").append(currentLine);

                }
            }
        } catch (Exception ex) {
            System.out.println("Problem fetching recipes");
        }

    }

}