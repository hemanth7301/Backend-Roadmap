package com.Backend;

import java.util.Scanner;

public class NumberGuessingGame {
    private final String[] difficulties;
    private String difficulty;
    private int chances;
    private final int originalNumber;
    private int attempts;

    NumberGuessingGame() {
        difficulties = new String[3];
        difficulties[0] = "Easy";
        difficulties[1] = "Medium";
        difficulties[2] = "Hard";
        chances = 10;
        originalNumber = (int) (Math.random() * 100) + 1;
    }

    private static void welcomeMessage() {
        System.out.println("Welcome to the Number Guessing Game");
    }

    private static void gameMessage() {
        System.out.println("You need to guess the number between 1 to 100");
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        welcomeMessage();
        gameMessage();
        game.difficultyMessage();
        game.initGame();
    }

    public boolean verifyAnswer(int guessedNumber) {
        this.attempts++;
        if (guessedNumber == this.originalNumber) {
            System.out.println("Congratulations you have guessed the number in " + attempts + " attempts");
            return true;
        } else {
            this.chances--;
            if (this.chances == 0) {
                System.out.println("Oops. You lost the game. No more chances");
                System.out.println("Ans is " + this.originalNumber);
            } else if (guessedNumber > this.originalNumber) {
                System.out.println("You guess is greater than original");
            } else {
                System.out.println("You guess is smaller than original");
            }
            return false;
        }
    }

    public void printDifficulties() {
        for (int i = 0; i < difficulties.length; i++) {
            System.out.println(i + 1 + ": " + difficulties[i] + " " + chances / (i + 1) + " chances");
        }
    }

    public void difficultyMessage() {
        System.out.println("Select the level of difficulty number");
        Scanner sc = new Scanner(System.in);
        printDifficulties();
        int option = Integer.parseInt(sc.nextLine());
        setDifficulty(option);
        setChances(this.chances / option);
        System.out.println("Selected " + getDifficulty() + " level so you have " + getChances() + " chances");
        System.out.println("Lets start the game");
    }

    public void initGame() {
        while (this.chances > 0) {
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if (verifyAnswer(choice)) {
                break;
            }
        }
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulties[difficulty - 1];
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }
}
