import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
    private double[][] pixelArray;
    final int PIXEL_SIZE = 1;
    int width;
    int lenght;

    Frame(double[][] pixelArray) {
        this.pixelArray = pixelArray;
        width = pixelArray[0].length;
        lenght = pixelArray.length;
        Panel panel = new Panel(width, lenght, pixelArray);
        setName(Double.toString(pixelArray[0][0]));
        add(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, lenght);
        setLocationRelativeTo(null);
    }

    private class Panel extends JPanel {
        Panel(int width, int length, double[][] pixelArray) {
            setPreferredSize(new Dimension(width, length));
            setVisible(true);
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < lenght; i++) {
                for (int j = 0; j < width; j++) {
                    g2d.setPaint(new Color((int) pixelArray[i][j], (int) pixelArray[i][j], (int) pixelArray[i][j]));
                    g2d.drawRect(j, i, PIXEL_SIZE, PIXEL_SIZE);
                }
            }
        }
    }
    public static void main(String[] args) {
        new Menu();
    }
}
