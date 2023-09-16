import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
    private final int WIDTH = 400;
    private final int LENGTH = 200;
    private final int BUTTON_LENGHT = 100;
    private final int BUTTON_HEIGHT = 25;
    private final int CUSHION = 5;
    private final int ITERATIONS_HEIGHT = 100;
    private JLabel iterantionsLabel = new JLabel("how many iterations?");
    private JLabel fileChosenName = new JLabel("");
    private JTextField iterationsField = new JTextField();
    private JButton uploadButton = new JButton("blur");
    private JButton fileChooserButton = new JButton("upload file");
    private static int numOfIterations = 0;
    private static File fileToBlur;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileChooserButton) {
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                fileToBlur = new File(fileChooser.getSelectedFile().getAbsolutePath());
                fileChosenName.setText(fileToBlur.getAbsolutePath());
            }
        } else if (e.getSource() == uploadButton) {
            try {
                numOfIterations = Integer.parseInt(iterationsField.getText());
                pictureBlurrer.runProgram(numOfIterations);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Something went wrong","error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    Menu() {
        setLayout(null);

        fileChooserButton.addActionListener(this);
        fileChooserButton.setBounds(WIDTH / 2 - BUTTON_LENGHT / 2, CUSHION, BUTTON_LENGHT, BUTTON_HEIGHT);
        fileChooserButton.setFont(new Font("consolas", Font.BOLD, 10));

        fileChosenName.setBounds(CUSHION, BUTTON_LENGHT - 25, WIDTH - CUSHION * 2, 15);
        fileChosenName.setHorizontalAlignment(SwingConstants.CENTER);

        iterantionsLabel.setBounds(CUSHION, ITERATIONS_HEIGHT, WIDTH / 2 - 25, 15);

        iterationsField.setBounds(WIDTH / 2 - 30, ITERATIONS_HEIGHT, 50, 15);

        uploadButton.addActionListener(this);
        uploadButton.setBounds(WIDTH / 2 - BUTTON_LENGHT / 2, LENGTH - 75, BUTTON_LENGHT, BUTTON_HEIGHT);

        setPreferredSize(new Dimension(WIDTH, LENGTH));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(fileChooserButton);
        add(uploadButton);
        add(fileChosenName);
        add(iterantionsLabel);
        add(iterationsField);
        pack();
        setVisible(true);
    }

    public static File getFileFromMenu() {
        return fileToBlur;
    }

    public static int getNumOfIterations() {
        return numOfIterations;
    }

    public static void main(String[] args) {
        new Menu();
    }
}
