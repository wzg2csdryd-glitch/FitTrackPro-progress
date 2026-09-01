/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecord {
    private String attendanceID;
    private String memberID;
    private LocalDate checkInDate;
    private LocalTime checkInTime;

    public AttendanceRecord(String attendanceID, String memberID, LocalDate checkInDate, LocalTime checkInTime) {
        this.attendanceID = attendanceID;
        this.memberID = memberID;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
    }

    public String getAttendanceID() {
        return attendanceID;
    }

    public String getMemberID() {
        return memberID;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public boolean isSameDay(LocalDate date) {
        return checkInDate.equals(date);
    }

    @Override
    public String toString() {
        return attendanceID + " | " + memberID + " | " + checkInDate + " | " + checkInTime;
    }
}
