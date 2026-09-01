/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fittrack;

public class Tools {
    public static String addSpaces(String text, int width) {
        StringBuilder spaces = new StringBuilder();
        for (int i = text.length(); i < width; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}