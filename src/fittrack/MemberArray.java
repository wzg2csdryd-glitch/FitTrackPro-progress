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

public class MemberArray {
    private Member[] memberArray = new Member[200];
    private int size;

    /**
     * Loads every row of Members.txt into memberArray. Each line is split
     * on "#" (keeping trailing empty fields, since some rows can legitimately
     * end with one) and converted field-by-field into the types Member's
     * constructor expects, then appended in file order.
     * If Members.txt is missing, size is left at 0 rather than throwing,
     * so the rest of the program can still run against an empty array.
     */
    public MemberArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Members.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String memberID = fields[0];
                String name = fields[1];
                String surname = fields[2];
                LocalDate dateOfBirth = LocalDate.parse(fields[3]);
                String contactDetails = fields[4];
                LocalDate joinDate = LocalDate.parse(fields[5]);
                boolean activeStatus = Boolean.parseBoolean(fields[6]);
                double height = Double.parseDouble(fields[7]);
                double startingWeight = Double.parseDouble(fields[8]);
                double currentWeight = Double.parseDouble(fields[9]);
                double bmi = Double.parseDouble(fields[10]);
                String fitnessGoal = fields[11];
                String assignedPlanID = fields[12];
                String membershipList = fields[13];

                memberArray[size] = new Member(memberID, name, surname, dateOfBirth,
                        contactDetails, joinDate, activeStatus, height, startingWeight,
                        currentWeight, bmi, fitnessGoal, assignedPlanID, membershipList);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Members.txt not found.");
        }
    }

/**
 * Builds a padded, one-member-per-line summary of every member currently
 * in the array, used to fill the Members list on screen.
 * @return the formatted multi-line summary string
 */
@Override
public String toString() {
    String result = "";
    for (int i = 0; i < size; i++) {
        result += formatMember(memberArray[i]);
    }
    return result;
}

/**
 * Formats one member as a padded, single display line, shared by
 * toString() and the search results so both always look identical.
 * @param m the member to format
 * @return the formatted line, ending in a newline
 */
private String formatMember(Member m) {
    String line = m.getMemberID() + Tools.addSpaces(m.getMemberID(), 10);
    line += m.getFullName() + Tools.addSpaces(m.getFullName(), 25);
    line += m.getFitnessGoal() + Tools.addSpaces(m.getFitnessGoal(), 20);
    line += (m.isActiveStatus() ? "Active" : "Inactive") + "\n";
    return line;
}

/**
 * Counts how many members are currently active, for the dashboard.
 * @return the number of members whose activeStatus is true
 */
public int countActive() {
    int count = 0;
    for (int i = 0; i < size; i++) {
        if (memberArray[i].isActiveStatus()) {
            count++;
        }
    }
    return count;
}

/**
 * Checks whether a member matches a search term. The term is compared
 * case-insensitively against both the member ID and the full name, and
 * matches if it appears anywhere inside either one.
 * @param m the member to test
 * @param query the search term, already trimmed and lower-cased
 * @return true if the member's ID or full name contains the term
 */
private boolean matches(Member m, String query) {
    return m.getMemberID().toLowerCase().contains(query)
            || m.getFullName().toLowerCase().contains(query);
}

/**
 * Finds the first member matching a search term by ID or name.
 * @param query the text to look for, e.g. "M012" or "zwane"
 * @return the index of the first match, or -1 if nothing matches
 */
public int searchFirstByNameOrID(String query) {
    String q = query.trim().toLowerCase();
    for (int i = 0; i < size; i++) {
        if (matches(memberArray[i], q)) {
            return i;
        }
    }
    return -1;
}

/**
 * Builds a display list of every member matching a search term by ID or
 * name, in the same format as the full members list.
 * @param query the text to look for
 * @return the matching members as formatted lines, or an empty string if none match
 */
public String searchByNameOrID(String query) {
    String q = query.trim().toLowerCase();
    String result = "";
    for (int i = 0; i < size; i++) {
        if (matches(memberArray[i], q)) {
            result += formatMember(memberArray[i]);
        }
    }
    return result;
}

/**
 * Sorts the members currently in the array into ascending order by
 * surname, using bubble sort. Only compares up to size (not the full
 * array length), since the unused slots beyond size hold no real data.
 */
public void sortBySurname() {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - 1 - i; j++) {
            if (memberArray[j].getSurname().compareTo(memberArray[j + 1].getSurname()) > 0) {
                Member temp = memberArray[j];
                memberArray[j] = memberArray[j + 1];
                memberArray[j + 1] = temp;
            }
        }
    }
}

/**
 * Searches the array for a member with the given ID.
 * @param memberID the member ID to search for, e.g. "M001"
 * @return the index of the matching member, or -1 if none is found
 */
public int searchFirst(String memberID) {
    for (int i = 0; i < size; i++) {
        if (memberArray[i].getMemberID().equals(memberID)) {
            return i;
        }
    }
    return -1;
}

/**
 * @param index position in the array to fetch
 * @return the Member stored at that position
 */
public Member getMember(int index) {
    return memberArray[index];
}

/**
 * @return the number of members actually stored in the array
 */
public int getSize() {
    return size;
}

/**
 * Works out the next member ID to use when adding a new member. It scans
 * the whole array for the highest numeric ID and adds one (e.g. "M030"
 * produces "M031"). It deliberately does not look at the last slot: sorting
 * reorders the array, so the last slot is not necessarily the newest member.
 * @return the next unused member ID, or "M001" if there are no members
 */
public String generateNextMemberID() {
    if (size == 0) {
        return "M001";
    }
    String highest = memberArray[0].getMemberID();
    for (int i = 1; i < size; i++) {
        if (Tools.idNumber(memberArray[i].getMemberID(), "M") > Tools.idNumber(highest, "M")) {
            highest = memberArray[i].getMemberID();
        }
    }
    return Tools.generateNextID("M", highest);
}

/**
 * Appends a newly created member to the end of the array and grows size
 * to match. Does not write to disk — call saveToFile() afterwards to persist.
 * @param member the new member to store
 */
public void addMember(Member member) {
    memberArray[size] = member;
    size++;
}

/**
 * Removes the member at the given position by shifting every member
 * after it one place to the left, then shrinking size by one. Does not
 * write to disk — call saveToFile() afterwards to persist.
 * @param index position of the member to remove
 */
public void removeMember(int index) {
    for (int i = index; i < size - 1; i++) {
        memberArray[i] = memberArray[i + 1];
    }
    size--;
}

/**
 * Rewrites Members.txt from scratch using the array's current contents,
 * rebuilding each line by joining that member's fields with "#" in the
 * same order the constructor expects them back in. Called after any
 * change (add, edit, delete, activate/deactivate) so the file on disk
 * never falls out of sync with what is in memory.
 */
public void saveToFile() {
    try {
        PrintWriter output = new PrintWriter(new File("Members.txt"));
        for (int i = 0; i < size; i++) {
            Member m = memberArray[i];
            output.println(m.getMemberID() + "#" + m.getName() + "#" + m.getSurname() + "#"
                    + m.getDateOfBirth() + "#" + m.getContactDetails() + "#" + m.getJoinDate() + "#"
                    + m.isActiveStatus() + "#" + m.getHeight() + "#" + m.getStartingWeight() + "#"
                    + m.getCurrentWeight() + "#" + m.getBmi() + "#" + m.getFitnessGoal() + "#"
                    + m.getAssignedPlanID() + "#" + m.getMembershipList());
        }
        output.close();
    } catch (java.io.FileNotFoundException e) {
        System.out.println("Could not write to Members.txt");
    }
}
}
