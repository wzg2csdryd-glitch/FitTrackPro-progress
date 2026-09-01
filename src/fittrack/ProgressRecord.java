/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.time.LocalDate;

public class ProgressRecord {
    private String progressID;
    private String memberID;
    private LocalDate dateRecorded;
    private double bodyWeight;
    private String measurements;
    private String notes;

    public ProgressRecord(String progressID, String memberID, LocalDate dateRecorded,
                           double bodyWeight, String measurements, String notes) {
        this.progressID = progressID;
        this.memberID = memberID;
        this.dateRecorded = dateRecorded;
        this.bodyWeight = bodyWeight;
        this.measurements = measurements;
        this.notes = notes;
    }

    public String getProgressID() {
        return progressID;
    }

    public String getMemberID() {
        return memberID;
    }

    public LocalDate getDateRecorded() {
        return dateRecorded;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public String getMeasurements() {
        return measurements;
    }

    public String getNotes() {
        return notes;
    }

    public double weightChangeFrom(ProgressRecord previous) {
        return bodyWeight - previous.getBodyWeight();
    }

    @Override
    public String toString() {
        return progressID + " | " + memberID + " | " + dateRecorded + " | " + bodyWeight + "kg";
    }
}