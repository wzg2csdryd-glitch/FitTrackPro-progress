/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class ProgressArray {
    private ProgressRecord[] progressArray = new ProgressRecord[150];
    private int size;

    public ProgressArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Progress.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String progressID = fields[0];
                String memberID = fields[1];
                LocalDate dateRecorded = LocalDate.parse(fields[2]);
                double bodyWeight = Double.parseDouble(fields[3]);
                String measurements = fields[4];
                String notes = fields[5];

                progressArray[size] = new ProgressRecord(progressID, memberID, dateRecorded, bodyWeight, measurements, notes);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Progress.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            ProgressRecord p = progressArray[i];
            result += p.getProgressID() + Tools.addSpaces(p.getProgressID(), 10);
            result += p.getMemberID() + Tools.addSpaces(p.getMemberID(), 10);
            result += p.getDateRecorded() + " " + p.getBodyWeight() + "kg\n";
        }
        return result;
    }

    public void sortByDate() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (progressArray[j].getDateRecorded().compareTo(progressArray[j + 1].getDateRecorded()) > 0) {
                    ProgressRecord temp = progressArray[j];
                    progressArray[j] = progressArray[j + 1];
                    progressArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String progressID) {
        for (int i = 0; i < size; i++) {
            if (progressArray[i].getProgressID().equals(progressID)) {
                return i;
            }
        }
        return -1;
    }

    public ProgressRecord getProgressRecord(int index) {
        return progressArray[index];
    }

    public int getSize() {
        return size;
    }

    /**
     * Collects one member's progress records into their own array, sorted
     * oldest to newest with a bubble sort, so comparisons between "this
     * entry" and "the previous entry" are always between neighbours.
     * @param memberID the member whose records are wanted
     * @return that member's records in date order (length is exactly the count found)
     */
    private ProgressRecord[] recordsFor(String memberID) {
        ProgressRecord[] found = new ProgressRecord[size];
        int n = 0;
        for (int i = 0; i < size; i++) {
            if (progressArray[i].getMemberID().equals(memberID)) {
                found[n] = progressArray[i];
                n++;
            }
        }
        found = java.util.Arrays.copyOf(found, n);
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (found[j].getDateRecorded().isAfter(found[j + 1].getDateRecorded())) {
                    ProgressRecord temp = found[j];
                    found[j] = found[j + 1];
                    found[j + 1] = temp;
                }
            }
        }
        return found;
    }

    /**
     * Builds one member's progress history, oldest first. Each entry after
     * the first shows the weight change compared with the entry before it
     * (using ProgressRecord.weightChangeFrom), and a summary line at the end
     * compares the latest entry with the very first.
     * @param memberID the member whose history is wanted
     * @return the formatted history and summary
     */
    public String historyFor(String memberID) {
        ProgressRecord[] records = recordsFor(memberID);
        if (records.length == 0) {
            return "No progress entries recorded for this member.";
        }
        String result = "Date        Weight     Change vs previous     Measurements / notes\n";
        for (int i = 0; i < records.length; i++) {
            ProgressRecord r = records[i];
            String change = (i == 0) ? "first entry"
                    : String.format(java.util.Locale.US, "%+.1f kg", r.weightChangeFrom(records[i - 1]));
            String weight = r.getBodyWeight() + " kg";
            result += r.getDateRecorded() + "  " + weight + Tools.addSpaces(weight, 10)
                    + change + Tools.addSpaces(change, 22)
                    + r.getMeasurements() + " | " + r.getNotes() + "\n";
        }
        ProgressRecord first = records[0];
        ProgressRecord latest = records[records.length - 1];
        result += String.format(java.util.Locale.US,
                "\nEntries: %d   First: %.1f kg   Latest: %.1f kg   Total change: %+.1f kg\n",
                records.length, first.getBodyWeight(), latest.getBodyWeight(),
                latest.weightChangeFrom(first));
        return result;
    }

    /**
     * Builds the progress summary report: for every member, how many
     * entries they have and how their weight has changed from first to latest.
     * @param members the member list, used for names
     * @return the formatted multi-line report
     */
    public String summaryReport(MemberArray members) {
        String report = "PROGRESS SUMMARY REPORT\n\n";
        report += "ID      Name                      Entries  First     Latest    Change\n";
        for (int i = 0; i < members.getSize(); i++) {
            Member m = members.getMember(i);
            ProgressRecord[] records = recordsFor(m.getMemberID());
            String start = m.getMemberID() + Tools.addSpaces(m.getMemberID(), 8)
                    + m.getFullName() + Tools.addSpaces(m.getFullName(), 26);
            if (records.length == 0) {
                report += start + "0        no entries\n";
                continue;
            }
            ProgressRecord first = records[0];
            ProgressRecord latest = records[records.length - 1];
            String count = String.valueOf(records.length);
            String firstW = first.getBodyWeight() + " kg";
            String latestW = latest.getBodyWeight() + " kg";
            report += start + count + Tools.addSpaces(count, 9)
                    + firstW + Tools.addSpaces(firstW, 10)
                    + latestW + Tools.addSpaces(latestW, 10)
                    + String.format(java.util.Locale.US, "%+.1f kg", latest.weightChangeFrom(first)) + "\n";
        }
        return report;
    }

    public String generateNextProgressID() {
        String lastID = progressArray[size - 1].getProgressID();
        return Tools.generateNextID("PR", lastID);
    }

    public void addRecord(ProgressRecord record) {
        progressArray[size] = record;
        size++;
    }

    public void saveToFile() {
        try {
            PrintWriter output = new PrintWriter(new File("Progress.txt"));
            for (int i = 0; i < size; i++) {
                ProgressRecord p = progressArray[i];
                output.println(p.getProgressID() + "#" + p.getMemberID() + "#" + p.getDateRecorded()
                        + "#" + p.getBodyWeight() + "#" + p.getMeasurements() + "#" + p.getNotes());
            }
            output.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Could not write to Progress.txt");
        }
    }
}