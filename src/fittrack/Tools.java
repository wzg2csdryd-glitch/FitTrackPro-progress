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

    /**
     * Extracts the numeric part of an ID, e.g. idNumber("M016", "M") is 16.
     * Used to compare IDs by value rather than by where they sit in an array.
     * @param id the full ID, e.g. "MS068"
     * @param prefix the letters at the front of the ID, e.g. "MS"
     * @return the number after the prefix
     */
    public static int idNumber(String id, String prefix) {
        return Integer.parseInt(id.substring(prefix.length()));
    }

    public static String generateNextID(String prefix, String lastID) {
        String numericPart = lastID.substring(prefix.length());
        int number = Integer.parseInt(numericPart) + 1;
        String padded = String.format("%0" + numericPart.length() + "d", number);
        return prefix + padded;
    }
}