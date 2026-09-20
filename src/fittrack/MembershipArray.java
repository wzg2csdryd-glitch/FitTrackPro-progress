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

public class MembershipArray {
    private Membership[] membershipArray = new Membership[100];
    private int size;

    public MembershipArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Memberships.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String membershipID = fields[0];
                String memberID = fields[1];
                String membershipType = fields[2];
                LocalDate startDate = LocalDate.parse(fields[3]);
                LocalDate endDate = LocalDate.parse(fields[4]);
                String paymentStatus = fields[5];

                membershipArray[size] = new Membership(membershipID, memberID, membershipType, startDate, endDate, paymentStatus);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Memberships.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            Membership m = membershipArray[i];
            result += m.getMembershipID() + Tools.addSpaces(m.getMembershipID(), 12);
            result += m.getMemberID() + Tools.addSpaces(m.getMemberID(), 10);
            result += m.getMembershipType() + Tools.addSpaces(m.getMembershipType(), 12);
            result += m.getEndDate() + " " + m.getPaymentStatus() + "\n";
        }
        return result;
    }

    public void sortByEndDate() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (membershipArray[j].getEndDate().compareTo(membershipArray[j + 1].getEndDate()) > 0) {
                    Membership temp = membershipArray[j];
                    membershipArray[j] = membershipArray[j + 1];
                    membershipArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String membershipID) {
        for (int i = 0; i < size; i++) {
            if (membershipArray[i].getMembershipID().equals(membershipID)) {
                return i;
            }
        }
        return -1;
    }

    public Membership getMembership(int index) {
        return membershipArray[index];
    }

    public int getSize() {
        return size;
    }

    /**
     * Finds a member's current (most recent) membership, defined as the one
     * with the latest end date. Members can hold several memberships over
     * time, so only the latest tells us whether they are still covered.
     * @param memberID the member to look up
     * @return the index of that member's latest membership, or -1 if they have none
     */
    public int findLatestFor(String memberID) {
        int best = -1;
        for (int i = 0; i < size; i++) {
            if (membershipArray[i].getMemberID().equals(memberID)) {
                if (best == -1 || membershipArray[i].getEndDate().isAfter(membershipArray[best].getEndDate())) {
                    best = i;
                }
            }
        }
        return best;
    }

    /**
     * Counts active members whose current membership ends within the next
     * few days (and has not already ended). Used for the dashboard.
     * @param today the date to measure from
     * @param days how many days ahead counts as "expiring soon"
     * @param members the member list, so inactive members can be skipped
     * @return the number of active members with a membership expiring soon
     */
    public int countExpiringWithin(LocalDate today, int days, MemberArray members) {
        int count = 0;
        for (int i = 0; i < members.getSize(); i++) {
            Member m = members.getMember(i);
            if (!m.isActiveStatus()) {
                continue;
            }
            int pos = findLatestFor(m.getMemberID());
            if (pos != -1 && !membershipArray[pos].isExpired(today)
                    && membershipArray[pos].daysRemaining(today) <= days) {
                count++;
            }
        }
        return count;
    }

    /**
     * Builds the membership expiry report: active members whose current
     * membership is about to expire, those whose membership has already
     * ended, and those with no membership on record at all.
     * @param today the date to measure from
     * @param days how many days ahead counts as "expiring soon"
     * @param members the member list, used for names and to skip inactive members
     * @return the formatted multi-line report
     */
    public String expiryReport(LocalDate today, int days, MemberArray members) {
        String expiring = "";
        String lapsed = "";
        String none = "";
        int expiringCount = 0;
        int lapsedCount = 0;
        int noneCount = 0;

        for (int i = 0; i < members.getSize(); i++) {
            Member m = members.getMember(i);
            if (!m.isActiveStatus()) {
                continue;
            }
            String who = m.getMemberID() + Tools.addSpaces(m.getMemberID(), 8)
                    + m.getFullName() + Tools.addSpaces(m.getFullName(), 26);
            int pos = findLatestFor(m.getMemberID());
            if (pos == -1) {
                none += "  " + who + "\n";
                noneCount++;
                continue;
            }
            Membership ms = membershipArray[pos];
            String detail = ms.getMembershipType() + Tools.addSpaces(ms.getMembershipType(), 10)
                    + ms.getEndDate();
            if (ms.isExpired(today)) {
                lapsed += "  " + who + detail + "  expired " + (-ms.daysRemaining(today))
                        + " days ago  (" + ms.getPaymentStatus() + ")\n";
                lapsedCount++;
            } else if (ms.daysRemaining(today) <= days) {
                expiring += "  " + who + detail + "  " + ms.daysRemaining(today)
                        + " days left  (" + ms.getPaymentStatus() + ")\n";
                expiringCount++;
            }
        }

        String report = "MEMBERSHIP EXPIRY REPORT (as at " + today + ")\n\n";
        report += "Expiring within " + days + " days (" + expiringCount + "):\n"
                + (expiring.isEmpty() ? "  None\n" : expiring);
        report += "\nAlready expired (" + lapsedCount + "):\n"
                + (lapsed.isEmpty() ? "  None\n" : lapsed);
        report += "\nNo membership on record (" + noneCount + "):\n"
                + (none.isEmpty() ? "  None\n" : none);
        return report;
    }

    /**
     * Lists every membership belonging to a member whose name matches a
     * search term, in the same style as the full memberships list.
     * @param query the text to look for inside the member's full name
     * @param members the member list, used to turn member IDs into names
     * @return the matching memberships as formatted lines, or an empty string if none match
     */
    public String searchByMemberName(String query, MemberArray members) {
        String q = query.trim().toLowerCase();
        String result = "";
        for (int i = 0; i < size; i++) {
            Membership ms = membershipArray[i];
            int pos = members.searchFirst(ms.getMemberID());
            String name = (pos == -1) ? ms.getMemberID() : members.getMember(pos).getFullName();
            if (name.toLowerCase().contains(q)) {
                result += ms.getMembershipID() + Tools.addSpaces(ms.getMembershipID(), 8);
                result += name + Tools.addSpaces(name, 26);
                result += ms.getMembershipType() + Tools.addSpaces(ms.getMembershipType(), 10);
                result += ms.getEndDate() + " " + ms.getPaymentStatus() + "\n";
            }
        }
        return result;
    }

    /**
     * Works out the next membership ID by scanning the whole array for the
     * highest numeric ID and adding one, so the result does not depend on
     * the order the array is currently sorted in.
     * @return the next unused membership ID, or "MS001" if there are none
     */
    public String generateNextMembershipID() {
        if (size == 0) {
            return "MS001";
        }
        String highest = membershipArray[0].getMembershipID();
        for (int i = 1; i < size; i++) {
            if (Tools.idNumber(membershipArray[i].getMembershipID(), "MS") > Tools.idNumber(highest, "MS")) {
                highest = membershipArray[i].getMembershipID();
            }
        }
        return Tools.generateNextID("MS", highest);
    }

    public void addMembership(Membership membership) {
        membershipArray[size] = membership;
        size++;
    }

    public void saveToFile() {
        try {
            PrintWriter output = new PrintWriter(new File("Memberships.txt"));
            for (int i = 0; i < size; i++) {
                Membership m = membershipArray[i];
                output.println(m.getMembershipID() + "#" + m.getMemberID() + "#" + m.getMembershipType()
                        + "#" + m.getStartDate() + "#" + m.getEndDate() + "#" + m.getPaymentStatus());
            }
            output.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Could not write to Memberships.txt");
        }
    }
}