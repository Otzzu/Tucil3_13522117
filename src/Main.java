package src;

import src.GUI.WordLadderGUI;

public class Main {

    
    public static void main(String[] args) {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        new WordLadderGUI();
    }
    
}
