/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class MemberArray {
    private Member[] memberArray = new Member[200];
    private int size;

    public MemberArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Members.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String memberID = fields[0];
                String name = fields[1];
                String surname = fields[2];
                LocalDate dateOfBirth = LocalDate.parse(fields[3]);
                String contactDetails = fields[4];
                LocalDate joinDate = LocalDate.parse(fields[5]);
                boolean activeStatus = Boolean.parseBoolean(fields[6]);
                double height = Double.parseDouble(fields[7]);
                double startingWeight = Double.parseDouble(fields[8]);
                double currentWeight = Double.parseDouble(fields[9]);
                double bmi = Double.parseDouble(fields[10]);
                String fitnessGoal = fields[11];
                String assignedPlanID = fields[12];
                String membershipList = fields[13];

                memberArray[size] = new Member(memberID, name, surname, dateOfBirth,
                        contactDetails, joinDate, activeStatus, height, startingWeight,
                        currentWeight, bmi, fitnessGoal, assignedPlanID, membershipList);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Members.txt not found.");
        }
    }
    
    @Override
public String toString() {
    String result = "";
    for (int i = 0; i < size; i++) {
        Member m = memberArray[i];
        result += m.getMemberID() + Tools.addSpaces(m.getMemberID(), 10);
        result += m.getFullName() + Tools.addSpaces(m.getFullName(), 25);
        result += m.getFitnessGoal() + Tools.addSpaces(m.getFitnessGoal(), 20);
        result += (m.isActiveStatus() ? "Active" : "Inactive") + "\n";
    }
    return result;
}

public void sortBySurname() {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - 1 - i; j++) {
            if (memberArray[j].getSurname().compareTo(memberArray[j + 1].getSurname()) > 0) {
                Member temp = memberArray[j];
                memberArray[j] = memberArray[j + 1];
                memberArray[j + 1] = temp;
            }
        }
    }
}

public int searchFirst(String memberID) {
    for (int i = 0; i < size; i++) {
        if (memberArray[i].getMemberID().equals(memberID)) {
            return i;
        }
    }
    return -1;
}

public Member getMember(int index) {
    return memberArray[index];
}

public int getSize() {
    return size;
}
}