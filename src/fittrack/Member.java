/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.time.LocalDate;

public class Member {
    private String memberID;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String contactDetails;
    private LocalDate joinDate;
    private boolean activeStatus;
    private double height;
    private double startingWeight;
    private double currentWeight;
    private double bmi;
    private String fitnessGoal;
    private String assignedPlanID;
    private String membershipList;

public Member(String memberID, String name, String surname, LocalDate dateOfBirth,
              String contactDetails, LocalDate joinDate, boolean activeStatus,
              double height, double startingWeight, double currentWeight, double bmi,
              String fitnessGoal, String assignedPlanID, String membershipList) {
    this.memberID = memberID;
    this.name = name;
    this.surname = surname;
    this.dateOfBirth = dateOfBirth;
    this.contactDetails = contactDetails;
    this.joinDate = joinDate;
    this.activeStatus = activeStatus;
    this.height = height;
    this.startingWeight = startingWeight;
    this.currentWeight = currentWeight;
    this.bmi = bmi;
    this.fitnessGoal = fitnessGoal;
    this.assignedPlanID = assignedPlanID;
    this.membershipList = membershipList;
}
public String getMemberID() {
    return memberID;
}

public String getName() {
    return name;
}

public String getSurname() {
    return surname;
}

public LocalDate getDateOfBirth() {
    return dateOfBirth;
}

public String getContactDetails() {
    return contactDetails;
}

public void setContactDetails(String contactDetails) {
    this.contactDetails = contactDetails;
}

public LocalDate getJoinDate() {
    return joinDate;
}

public boolean isActiveStatus() {
    return activeStatus;
}

public void setActiveStatus(boolean activeStatus) {
    this.activeStatus = activeStatus;
}

public double getHeight() {
    return height;
}

public double getStartingWeight() {
    return startingWeight;
}

public double getCurrentWeight() {
    return currentWeight;
}

public void setCurrentWeight(double currentWeight) {
    this.currentWeight = currentWeight;
    this.bmi = calculateBMI();
}

public double calculateBMI() {
    if (height <= 0) {
        return 0;
    }
    return currentWeight / (height * height);
}

public String getFullName() {
    return name + " " + surname;
}

public double getBmi() {
    return bmi;
}

public String getFitnessGoal() {
    return fitnessGoal;
}

public String getAssignedPlanID() {
    return assignedPlanID;
}

public String getMembershipList() {
    return membershipList;
}

@Override
public String toString() {
    return memberID + " | " + getFullName() + " | " + fitnessGoal + " | " + (activeStatus ? "Active" : "Inactive");
}

}