/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026231006 - Noval Akbar Ramadhany
 * 2 - 5026231007 - Nadia Lovely
 * 3 - 5026231090 - Nadia Ayula Assyaputri
 */
package GUI;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Jalankan GUI dalam Event-Dispatching Thread untuk thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Buat JFrame
                JFrame frame = new JFrame(GameMain.TITLE);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Tambahkan GameMain sebagai konten JFrame
                frame.setContentPane(new GameMain());

                // Atur ukuran dan pusatkan
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
