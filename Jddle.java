/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jddle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Erol
 */
public class Jddle {
    
    private static final int MAX_LIVES = 5;
    private static int currentLives = MAX_LIVES;
    private static long lastLifeLostTime = 0;
    private static final long REGENERATION_TIME = 2 * 5 * 1000; // 30 seconds in milliseconds
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          // Create a list of riddles for level 1
          
        ArrayList<Riddle> LVL1 = new ArrayList<>();
        ArrayList<Riddle> LVL2 = new ArrayList<>();
        Scanner scan = new Scanner (System.in);

        
        // **********Level 1************
        
        LVL1.add(new Riddle(
                "Dalawang balon, hindi malingon",
                "Ears,ears,Ear,ear,Tenga,tenga,Tainga,tainga"));
        LVL1.add(new Riddle(
                "Ako ay may kaibigan, kasama ko kahit saan.",
                "Shadow,shadow,Anino,anino"));
        LVL1.add(new Riddle(
                "Buto't balat, Lumilipad. Ano ako?",
                "Kite,kite,Sarangola,sarangola,Saranggola,saranggola"));
        LVL1.add(new Riddle(
                "Isang prinsesa, nakaupo sa tasa.",
                "Kasoy,kasoy"));
        LVL1.add(new Riddle(
                "Kung kailan mo pinatay, saka pa humaba ang buhay.",
                "Candle,candle,Kandila,kandila"));
        LVL1.add(new Riddle(
                "Hindi tao, hindi hayop, ngunit lumuluha.",
                "Gripo,gripo"));
        

        // Game logic: loop through riddles and check answers
        for (Riddle riddle : LVL1) {
            if (currentLives == 0) {
                System.out.println("Wala ka nang buhay! Hintay ka muna bago sumagot ulit.");
                waitForLifeRegeneration();
            }
            
            System.out.println("Buhay: " + currentLives); // Display remaining lives
            System.out.println(riddle.getRiddle());
            String userAnswer = scan.nextLine();

            if (riddle.checkAnswer(userAnswer)) {
                System.out.println("Tama!");
            } else {
                System.out.println("Mali!");
                loseLife(); // Deduct a life if the answer is wrong
            }

            System.out.println(); // Blank line for readability between riddles
        }

        System.out.println("Natapos mo na ang Level 1!");
    }

    // Method to handle losing a life
    private static void loseLife() {
        currentLives--;
        lastLifeLostTime = System.currentTimeMillis();
        System.out.println("Nawalan ka ng isang buhay! Buhay na natitira: " + currentLives);
    }

    // Method to wait for life regeneration
    private static void waitForLifeRegeneration() {
        while (currentLives < MAX_LIVES) {
            long currentTime = System.currentTimeMillis();
            long timeSinceLastLifeLost = currentTime - lastLifeLostTime;

            if (timeSinceLastLifeLost >= REGENERATION_TIME) {
                currentLives++;
                lastLifeLostTime = System.currentTimeMillis(); // Update last life lost time
                System.out.println("Nadagdagan ang buhay mo! Buhay na natitira: " + currentLives);
            } else {
                long timeRemaining = REGENERATION_TIME - timeSinceLastLifeLost;
                System.out.println("Naghihintay... Buhay regenerates in: " + (timeRemaining / 1000) + " seconds.");
                try {
                    Thread.sleep(5000); // Check every 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


// Riddle class with multiple answers split from a single string
class Riddle {
    private String riddle;
    private List<String> answers; // Store multiple correct answers

    public Riddle(String riddle, String answersString) {
        this.riddle = riddle;
        // Split the string by commas and store as a list
        this.answers = Arrays.asList(answersString.split(","));
    }

    public String getRiddle() {
        return riddle;
    }

    // Check if the user's answer matches any of the possible answers
    public boolean checkAnswer(String userAnswer) {
        for (String answer : answers) {
            if (answer.equalsIgnoreCase(userAnswer.trim())) {
                return true;
            }
        }
        return false;
    }

    // Getter for the list of answers (for displaying the correct answers)
    public List<String> getAnswers() {
        return answers;
    }
}