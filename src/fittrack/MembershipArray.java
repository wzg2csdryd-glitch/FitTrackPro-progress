/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class MembershipArray {
    private Membership[] membershipArray = new Membership[100];
    private int size;

    public MembershipArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Memberships.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String membershipID = fields[0];
                String memberID = fields[1];
                String membershipType = fields[2];
                LocalDate startDate = LocalDate.parse(fields[3]);
                LocalDate endDate = LocalDate.parse(fields[4]);
                String paymentStatus = fields[5];

                membershipArray[size] = new Membership(membershipID, memberID, membershipType, startDate, endDate, paymentStatus);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Memberships.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            Membership m = membershipArray[i];
            result += m.getMembershipID() + Tools.addSpaces(m.getMembershipID(), 12);
            result += m.getMemberID() + Tools.addSpaces(m.getMemberID(), 10);
            result += m.getMembershipType() + Tools.addSpaces(m.getMembershipType(), 12);
            result += m.getEndDate() + " " + m.getPaymentStatus() + "\n";
        }
        return result;
    }

    public void sortByEndDate() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (membershipArray[j].getEndDate().compareTo(membershipArray[j + 1].getEndDate()) > 0) {
                    Membership temp = membershipArray[j];
                    membershipArray[j] = membershipArray[j + 1];
                    membershipArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String membershipID) {
        for (int i = 0; i < size; i++) {
            if (membershipArray[i].getMembershipID().equals(membershipID)) {
                return i;
            }
        }
        return -1;
    }

    public Membership getMembership(int index) {
        return membershipArray[index];
    }

    public int getSize() {
        return size;
    }
}