/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    /**
     * Builds one member's attendance history, newest check-in first. The
     * member's records are copied out and bubble-sorted by date and time,
     * so the order is correct regardless of how the main array is arranged.
     * @param memberID the member whose history is wanted
     * @return the formatted history with a count on the first line
     */
    public String historyFor(String memberID) {
        AttendanceRecord[] found = new AttendanceRecord[size];
        int n = 0;
        for (int i = 0; i < size; i++) {
            if (attendanceArray[i].getMemberID().equals(memberID)) {
                found[n] = attendanceArray[i];
                n++;
            }
        }
        if (n == 0) {
            return "No attendance recorded for this member.";
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (found[j].getCheckInDate().atTime(found[j].getCheckInTime())
                        .isBefore(found[j + 1].getCheckInDate().atTime(found[j + 1].getCheckInTime()))) {
                    AttendanceRecord temp = found[j];
                    found[j] = found[j + 1];
                    found[j + 1] = temp;
                }
            }
        }
        String result = n + " check-ins (newest first):\n\n";
        for (int i = 0; i < n; i++) {
            result += found[i].getCheckInDate() + "  " + found[i].getCheckInTime()
                    + "   (" + found[i].getAttendanceID() + ")\n";
        }
        return result;
    }

    /**
     * Counts check-ins that fall between two dates, inclusive.
     * @param memberID the member to count for, or null/empty to count every member
     * @param from the first date of the period
     * @param to the last date of the period
     * @return the number of matching check-ins
     */
    public int countBetween(String memberID, LocalDate from, LocalDate to) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            AttendanceRecord a = attendanceArray[i];
            boolean rightMember = memberID == null || memberID.isEmpty() || a.getMemberID().equals(memberID);
            if (rightMember && !a.getCheckInDate().isBefore(from) && !a.getCheckInDate().isAfter(to)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Builds the attendance report: how many times each member checked in
     * during a period, with a grand total.
     * @param from the first date of the period
     * @param to the last date of the period
     * @param members the member list, used for names
     * @return the formatted multi-line report
     */
    public String reportBetween(LocalDate from, LocalDate to, MemberArray members) {
        String report = "ATTENDANCE REPORT: " + from + " to " + to + "\n\n";
        int total = 0;
        for (int i = 0; i < members.getSize(); i++) {
            Member m = members.getMember(i);
            int count = countBetween(m.getMemberID(), from, to);
            total += count;
            report += m.getMemberID() + Tools.addSpaces(m.getMemberID(), 8)
                    + m.getFullName() + Tools.addSpaces(m.getFullName(), 26) + count + "\n";
        }
        report += "\nTotal check-ins in period: " + total + "\n";
        return report;
    }

    /**
     * Works out the next attendance ID by scanning the whole array for the
     * highest numeric ID and adding one, independent of sort order.
     * @return the next unused attendance ID, or "A0001" if there are none
     */
    public String generateNextAttendanceID() {
        if (size == 0) {
            return "A0001";
        }
        String highest = attendanceArray[0].getAttendanceID();
        for (int i = 1; i < size; i++) {
            if (Tools.idNumber(attendanceArray[i].getAttendanceID(), "A") > Tools.idNumber(highest, "A")) {
                highest = attendanceArray[i].getAttendanceID();
            }
        }
        return Tools.generateNextID("A", highest);
    }

    /**
     * Tells whether the array has no room left. Callers check this before
     * addRecord(), because adding to a full array would crash the program.
     * @return true if size has reached the length of the underlying array
     */
    public boolean isFull() {
        return size >= attendanceArray.length;
    }

    public void addRecord(AttendanceRecord record) {
        attendanceArray[size] = record;
        size++;
    }

    public void saveToFile() {
        try {
            PrintWriter output = new PrintWriter(new File("Attendance.txt"));
            for (int i = 0; i < size; i++) {
                AttendanceRecord a = attendanceArray[i];
                output.println(a.getAttendanceID() + "#" + a.getMemberID() + "#"
                        + a.getCheckInDate() + "#" + a.getCheckInTime());
            }
            output.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Could not write to Attendance.txt");
        }
    }
}