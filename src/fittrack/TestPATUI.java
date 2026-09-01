/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import javax.swing.JOptionPane;

public class TestPATUI {
    public static void main(String[] args) {
        System.out.println("=== Members ===");
        System.out.println(Manager.memberArray.toString());
        Manager.memberArray.sortBySurname();
        System.out.println("=== Members sorted by surname ===");
        System.out.println(Manager.memberArray.toString());
        String memberSearch = JOptionPane.showInputDialog("Enter a Member ID to search:");
        int memberPos = Manager.memberArray.searchFirst(memberSearch);
        if (memberPos == -1) {
            JOptionPane.showMessageDialog(null, "Member not found.");
        } else {
            JOptionPane.showMessageDialog(null, Manager.memberArray.getMember(memberPos).toString());
        }

        System.out.println("=== Users ===");
        System.out.println(Manager.userArray.toString());
        Manager.userArray.sortByUsername();
        System.out.println("=== Users sorted by username ===");
        System.out.println(Manager.userArray.toString());
        String userSearch = JOptionPane.showInputDialog("Enter a User ID to search:");
        int userPos = Manager.userArray.searchFirst(userSearch);
        if (userPos == -1) {
            JOptionPane.showMessageDialog(null, "User not found.");
        } else {
            JOptionPane.showMessageDialog(null, Manager.userArray.getUser(userPos).toString());
        }

        System.out.println("=== Memberships ===");
        System.out.println(Manager.membershipArray.toString());
        Manager.membershipArray.sortByEndDate();
        System.out.println("=== Memberships sorted by end date ===");
        System.out.println(Manager.membershipArray.toString());
        String membershipSearch = JOptionPane.showInputDialog("Enter a Membership ID to search:");
        int membershipPos = Manager.membershipArray.searchFirst(membershipSearch);
        if (membershipPos == -1) {
            JOptionPane.showMessageDialog(null, "Membership not found.");
        } else {
            JOptionPane.showMessageDialog(null, Manager.membershipArray.getMembership(membershipPos).toString());
        }

        System.out.println("=== Attendance ===");
        System.out.println(Manager.attendanceArray.toString());
        Manager.attendanceArray.sortByDate();
        System.out.println("=== Attendance sorted by date ===");
        System.out.println(Manager.attendanceArray.toString());
        String attendanceSearch = JOptionPane.showInputDialog("Enter an Attendance ID to search:");
        int attendancePos = Manager.attendanceArray.searchFirst(attendanceSearch);
        if (attendancePos == -1) {
            JOptionPane.showMessageDialog(null, "Attendance record not found.");
        } else {
            JOptionPane.showMessageDialog(null, Manager.attendanceArray.getAttendanceRecord(attendancePos).toString());
        }

        System.out.println("=== Training Plans ===");
        System.out.println(Manager.trainingPlanArray.toString());
        Manager.trainingPlanArray.sortByPlanName();
        System.out.println("=== Training Plans sorted by name ===");
        System.out.println(Manager.trainingPlanArray.toString());
        String planSearch = JOptionPane.showInputDialog("Enter a Plan ID to search:");
        int planPos = Manager.trainingPlanArray.searchFirst(planSearch);
        if (planPos == -1) {
            JOptionPane.showMessageDialog(null, "Training plan not found.");
        } else {
            JOptionPane.showMessageDialog(null, Manager.trainingPlanArray.getTrainingPlan(planPos).toString());
        }

        System.out.println("=== Progress ===");
        System.out.println(Manager.progressArray.toString());
        Manager.progressArray.sortByDate();
        System.out.println("=== Progress sorted by date ===");
        System.out.println(Manager.progressArray.toString());
        String progressSearch = JOptionPane.showInputDialog("Enter a Progress ID to search:");
        int progressPos = Manager.progressArray.searchFirst(progressSearch);
        if (progressPos == -1) {
            JOptionPane.showMessageDialog(null, "Progress record not found.");
        } else {
            JOptionPane.showMessageDialog(null, Manager.progressArray.getProgressRecord(progressPos).toString());
        }

        System.out.println("All arrays loaded and tested.");
    }
}