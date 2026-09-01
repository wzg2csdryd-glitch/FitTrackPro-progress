/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class ProgressArray {
    private ProgressRecord[] progressArray = new ProgressRecord[150];
    private int size;

    public ProgressArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Progress.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String progressID = fields[0];
                String memberID = fields[1];
                LocalDate dateRecorded = LocalDate.parse(fields[2]);
                double bodyWeight = Double.parseDouble(fields[3]);
                String measurements = fields[4];
                String notes = fields[5];

                progressArray[size] = new ProgressRecord(progressID, memberID, dateRecorded, bodyWeight, measurements, notes);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Progress.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            ProgressRecord p = progressArray[i];
            result += p.getProgressID() + Tools.addSpaces(p.getProgressID(), 10);
            result += p.getMemberID() + Tools.addSpaces(p.getMemberID(), 10);
            result += p.getDateRecorded() + " " + p.getBodyWeight() + "kg\n";
        }
        return result;
    }

    public void sortByDate() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (progressArray[j].getDateRecorded().compareTo(progressArray[j + 1].getDateRecorded()) > 0) {
                    ProgressRecord temp = progressArray[j];
                    progressArray[j] = progressArray[j + 1];
                    progressArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String progressID) {
        for (int i = 0; i < size; i++) {
            if (progressArray[i].getProgressID().equals(progressID)) {
                return i;
            }
        }
        return -1;
    }

    public ProgressRecord getProgressRecord(int index) {
        return progressArray[index];
    }

    public int getSize() {
        return size;
    }
}