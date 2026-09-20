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

/**
 * Creates a Member from one row of Members.txt.
 * @param memberID unique identifier for this member, e.g. "M001"
 * @param name member's first name
 * @param surname member's surname
 * @param dateOfBirth member's date of birth
 * @param contactDetails member's contact number
 * @param joinDate date the member joined the gym
 * @param activeStatus true if the member currently has active access
 * @param height member's height in metres, used for BMI
 * @param startingWeight member's weight in kilograms when they joined
 * @param currentWeight member's most recently recorded weight in kilograms
 * @param bmi member's current body mass index
 * @param fitnessGoal free-text description of what the member is training for
 * @param assignedPlanID ID of the TrainingPlan assigned to this member
 * @param membershipList ";"-separated list of this member's Membership IDs
 */
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

/**
 * @return this member's unique ID
 */
public String getMemberID() {
    return memberID;
}

/**
 * @return this member's first name
 */
public String getName() {
    return name;
}

/**
 * @return this member's surname
 */
public String getSurname() {
    return surname;
}

/**
 * @return this member's date of birth
 */
public LocalDate getDateOfBirth() {
    return dateOfBirth;
}

/**
 * @return this member's current contact number
 */
public String getContactDetails() {
    return contactDetails;
}

/**
 * Updates this member's contact number.
 * @param contactDetails the new contact number to store
 */
public void setContactDetails(String contactDetails) {
    this.contactDetails = contactDetails;
}

/**
 * @return the date this member joined the gym
 */
public LocalDate getJoinDate() {
    return joinDate;
}

/**
 * @return true if this member's access is currently active
 */
public boolean isActiveStatus() {
    return activeStatus;
}

/**
 * Activates or deactivates this member's access.
 * @param activeStatus true to mark the member active, false to deactivate them
 */
public void setActiveStatus(boolean activeStatus) {
    this.activeStatus = activeStatus;
}

/**
 * @return this member's height in metres
 */
public double getHeight() {
    return height;
}

/**
 * @return this member's weight in kilograms when they joined
 */
public double getStartingWeight() {
    return startingWeight;
}

/**
 * @return this member's most recently recorded weight in kilograms
 */
public double getCurrentWeight() {
    return currentWeight;
}

/**
 * Updates this member's current weight and immediately recalculates their
 * BMI to match, so the two fields can never drift out of sync.
 * @param currentWeight the new weight in kilograms
 */
public void setCurrentWeight(double currentWeight) {
    this.currentWeight = currentWeight;
    this.bmi = calculateBMI();
}

/**
 * Calculates BMI from the member's current weight and height.
 * Guarded against a height of zero (or a corrupt negative value) so this
 * can never throw a divide-by-zero error, even before GUI validation exists.
 * @return the calculated BMI, or 0 if height is not a positive number
 */
public double calculateBMI() {
    if (height <= 0) {
        return 0;
    }
    return currentWeight / (height * height);
}

/**
 * @return this member's first name and surname joined with a space
 */
public String getFullName() {
    return name + " " + surname;
}

/**
 * @return this member's most recently calculated BMI
 */
public double getBmi() {
    return bmi;
}

/**
 * @return this member's fitness goal
 */
public String getFitnessGoal() {
    return fitnessGoal;
}

/**
 * @return the ID of the training plan assigned to this member
 */
public String getAssignedPlanID() {
    return assignedPlanID;
}

/**
 * @return the ";"-separated list of this member's membership IDs
 */
public String getMembershipList() {
    return membershipList;
}

/**
 * Updates this member's first name.
 * @param name the new first name
 */
public void setName(String name) {
    this.name = name;
}

/**
 * Updates this member's surname.
 * @param surname the new surname
 */
public void setSurname(String surname) {
    this.surname = surname;
}

/**
 * Updates this member's fitness goal.
 * @param fitnessGoal the new fitness goal
 */
public void setFitnessGoal(String fitnessGoal) {
    this.fitnessGoal = fitnessGoal;
}

/**
 * Assigns a different training plan to this member.
 * @param assignedPlanID the ID of the training plan to assign, e.g. "P003"
 */
public void setAssignedPlanID(String assignedPlanID) {
    this.assignedPlanID = assignedPlanID;
}

/**
 * Updates this member's height and immediately recalculates their BMI,
 * exactly as setCurrentWeight does, so the two can never drift apart.
 * @param height the new height in metres
 */
public void setHeight(double height) {
    this.height = height;
    this.bmi = calculateBMI();
}

/**
 * Links a newly created Membership to this member by appending its ID
 * to membershipList. Handles the ";" separator itself so calling code
 * never has to rebuild the list string by hand.
 * @param newMembershipID the ID of the membership to add, e.g. "MS068"
 */
public void addMembershipID(String newMembershipID) {
    if (membershipList.isEmpty()) {
        membershipList = newMembershipID;
    } else {
        membershipList = membershipList + ";" + newMembershipID;
    }
}

/**
 * @return a short one-line summary of this member, used in list displays
 */
@Override
public String toString() {
    return memberID + " | " + getFullName() + " | " + fitnessGoal + " | " + (activeStatus ? "Active" : "Inactive");
}

}
