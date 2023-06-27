package org.example;

public class CocktailMenu {

    private static final String CREATE_COCKTAIL = "Create cocktail";
    private static final String UPDATE_COCKTAIL_MENU = "Update cocktail";
    private static final String DELETE_COCKTAIL_MENU = "Delete cocktail";
    private static final String BACK_MENU = "Go back";
    private static final Object[] cocktailMenuOptions = {CREATE_COCKTAIL, UPDATE_COCKTAIL_MENU, DELETE_COCKTAIL_MENU, BACK_MENU};

    Menu menu = new Menu(System.in, System.out);

    public void runCocktailDatabaseMenu() {
        while(true) {
            Object choice = menu.getChoiceFromOptions(cocktailMenuOptions);
            if(choice.equals(CREATE_COCKTAIL)) {
                System.out.println("Unconnected Create Option");
            } else if (choice.equals(UPDATE_COCKTAIL_MENU)) {
                System.out.println("Unconnected Update Option");
            } else if (choice.equals(DELETE_COCKTAIL_MENU)) {
                System.out.println("Unconnected Delete Option");
            } else if (choice.equals(BACK_MENU)) {
                break;
            }
        }
    }
}
