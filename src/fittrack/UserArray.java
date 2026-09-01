/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserArray {
    private User[] userArray = new User[50];
    private int size;

    public UserArray() {
        size = 0;
        try {
            Scanner input = new Scanner(new File("Users.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split("#", -1);

                String userID = fields[0];
                String firstName = fields[1];
                String surname = fields[2];
                String username = fields[3];
                String password = fields[4];
                String userType = fields[5];
                String memberID = fields[6];

                userArray[size] = new User(userID, firstName, surname, username, password, userType, memberID);
                size++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Users.txt not found.");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            User u = userArray[i];
            result += u.getUserID() + Tools.addSpaces(u.getUserID(), 10);
            result += u.getUsername() + Tools.addSpaces(u.getUsername(), 20);
            result += u.getUserType() + "\n";
        }
        return result;
    }

    public void sortByUsername() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (userArray[j].getUsername().compareTo(userArray[j + 1].getUsername()) > 0) {
                    User temp = userArray[j];
                    userArray[j] = userArray[j + 1];
                    userArray[j + 1] = temp;
                }
            }
        }
    }

    public int searchFirst(String userID) {
        for (int i = 0; i < size; i++) {
            if (userArray[i].getUserID().equals(userID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchUserName(String username) {
        for (int i = 0; i < size; i++) {
            if (userArray[i].getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public User getUser(int index) {
        return userArray[index];
    }

    public int getSize() {
        return size;
    }
}