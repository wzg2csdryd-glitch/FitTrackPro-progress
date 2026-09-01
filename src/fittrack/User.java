/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

public class User {
    private String userID;
    private String firstName;
    private String surname;
    private String username;
    private String password;
    private String userType;
    private String memberID;

    public User(String userID, String firstName, String surname, String username,
                String password, String userType, String memberID) {
        this.userID = userID;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.memberID = memberID;
    }

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getMemberID() {
        return memberID;
    }

    @Override
    public String toString() {
        return userID + " | " + username + " | " + userType;
    }
}