/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026231006 - Noval Akbar Ramadhany
 * 2 - 5026231007 - Nadia Lovely
 * 3 - 5026231090 - Nadia Ayula Assyaputri
 */
package OOP;
public enum Seed {
    CROSS("X"), NOUGHT("O"), NO_SEED(" ");

    private String icon;
    private Seed(String icon) {
        this.icon = icon;
    }

    public String getIcon(){
        return icon;
    }
}
