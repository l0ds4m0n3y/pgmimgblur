import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
    private double[][] pixelArray;
    final int PIXEL_SIZE = 1;
    int panelWidth;
    int lenght;

    Frame(double[][] pixelArray) {
        this.pixelArray = pixelArray;
        this.panelWidth = pictureBlurrer.getNumColumns();
        this.lenght = pictureBlurrer.getNumRows();
        Panel panel = new Panel(panelWidth, lenght, pixelArray);
        setName(Double.toString(pixelArray[0][0]));
        add(panel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private class Panel extends JPanel {
        Panel(int width, int length, double[][] pixelArray) {
            setPreferredSize(new Dimension(PIXEL_SIZE * width, PIXEL_SIZE * length));
            setVisible(true);
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < lenght; i++) {
                for (int j = 0; j < panelWidth; j++) {
                    g2d.setPaint(new Color((int) pixelArray[i][j], (int) pixelArray[i][j], (int) pixelArray[i][j]));
                    g2d.fillRect(PIXEL_SIZE * j, PIXEL_SIZE * i, PIXEL_SIZE, PIXEL_SIZE);
                }
            }
        }
    }
}