/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrainingPlanArray {
    private TrainingPlan[] trainingPlanArray = new TrainingPlan[20];
    private int size;

    public TrainingPlanArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("TrainingPlans.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String planID = fields[0];
                String planName = fields[1];
                String splitType = fields[2];
                String difficulty = fields[3];
                String notes = fields[4];

                trainingPlanArray[size] = new TrainingPlan(planID, planName, splitType, difficulty, notes);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("TrainingPlans.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            TrainingPlan p = trainingPlanArray[i];
            result += p.getPlanID() + Tools.addSpaces(p.getPlanID(), 8);
            result += p.getPlanName() + Tools.addSpaces(p.getPlanName(), 25);
            result += p.getDifficulty() + "\n";
        }
        return result;
    }

    public void sortByPlanName() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (trainingPlanArray[j].getPlanName().compareTo(trainingPlanArray[j + 1].getPlanName()) > 0) {
                    TrainingPlan temp = trainingPlanArray[j];
                    trainingPlanArray[j] = trainingPlanArray[j + 1];
                    trainingPlanArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String planID) {
        for (int i = 0; i < size; i++) {
            if (trainingPlanArray[i].getPlanID().equals(planID)) {
                return i;
            }
        }
        return -1;
    }

    public TrainingPlan getTrainingPlan(int index) {
        return trainingPlanArray[index];
    }

    public int getSize() {
        return size;
    }
}
