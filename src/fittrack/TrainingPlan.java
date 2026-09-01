/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

public class TrainingPlan {
    private String planID;
    private String planName;
    private String splitType;
    private String difficulty;
    private String notes;

    public TrainingPlan(String planID, String planName, String splitType, String difficulty, String notes) {
        this.planID = planID;
        this.planName = planName;
        this.splitType = splitType;
        this.difficulty = difficulty;
        this.notes = notes;
    }

    public String getPlanID() {
        return planID;
    }

    public String getPlanName() {
        return planName;
    }

    public String getSplitType() {
        return splitType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return planID + " | " + planName + " | " + splitType + " | " + difficulty;
    }
}