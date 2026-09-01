/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;



import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AttendanceArray {
    private AttendanceRecord[] attendanceArray = new AttendanceRecord[600];
    private int size;

    public AttendanceArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Attendance.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String attendanceID = fields[0];
                String memberID = fields[1];
                LocalDate checkInDate = LocalDate.parse(fields[2]);
                LocalTime checkInTime = LocalTime.parse(fields[3]);

                attendanceArray[size] = new AttendanceRecord(attendanceID, memberID, checkInDate, checkInTime);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Attendance.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            AttendanceRecord a = attendanceArray[i];
            result += a.getAttendanceID() + Tools.addSpaces(a.getAttendanceID(), 10);
            result += a.getMemberID() + Tools.addSpaces(a.getMemberID(), 10);
            result += a.getCheckInDate() + " " + a.getCheckInTime() + "\n";
        }
        return result;
    }

    public void sortByDate() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (attendanceArray[j].getCheckInDate().compareTo(attendanceArray[j + 1].getCheckInDate()) > 0) {
                    AttendanceRecord temp = attendanceArray[j];
                    attendanceArray[j] = attendanceArray[j + 1];
                    attendanceArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String attendanceID) {
        for (int i = 0; i < size; i++) {
            if (attendanceArray[i].getAttendanceID().equals(attendanceID)) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasCheckInOnDate(String memberID, LocalDate date) {
        for (int i = 0; i < size; i++) {
            if (attendanceArray[i].getMemberID().equals(memberID) && attendanceArray[i].isSameDay(date)) {
                return true;
            }
        }
        return false;
    }

    public AttendanceRecord getAttendanceRecord(int index) {
        return attendanceArray[index];
    }

    public int getSize() {
        return size;
    }
}