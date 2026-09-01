/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Membership {
    private String membershipID;
    private String memberID;
    private String membershipType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String paymentStatus;

    public Membership(String membershipID, String memberID, String membershipType,
                       LocalDate startDate, LocalDate endDate, String paymentStatus) {
        this.membershipID = membershipID;
        this.memberID = memberID;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = paymentStatus;
    }

    public String getMembershipID() {
        return membershipID;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isExpired(LocalDate today) {
        return today.isAfter(endDate);
    }

    public int daysRemaining(LocalDate today) {
        return (int) ChronoUnit.DAYS.between(today, endDate);
    }

    @Override
    public String toString() {
        return membershipID + " | " + memberID + " | " + membershipType + " | " + paymentStatus;
    }
}