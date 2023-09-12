import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
    private final int WIDTH =500;
    private final int LENGTH = 500;
    private JLabel iterantionsLabel = new JLabel("how many iterations?");
    private JLabel fileChosenName = new JLabel("");
    private JTextField iterationsField = new JTextField();
    private JButton uploadButton = new JButton("censor that mf");
    private JButton fileChooserButton = new JButton("upload file");
    private static int numOfIterations;
    private static File fileToBlur;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileChooserButton) {
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                fileToBlur = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(fileToBlur);
                fileChosenName.setText(fileToBlur.getAbsolutePath());
            }
        } else if (e.getSource() == uploadButton) {
            try {
                numOfIterations = Integer.valueOf(iterationsField.getText());
                if(numOfIterations > 0){
                    pictureBlurrer.runProgram();
                }
                if(!fileToBlur.getName().contains(".pgm")){
                    System.out.println("boo womp");
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Something went wrong","error", JOptionPane.ERROR_MESSAGE);

            }
            
        }
    }

    Menu() {
        setLayout(null);

        uploadButton.addActionListener(this);
        uploadButton.setBounds(100, 0, 100, 100);
        fileChooserButton.addActionListener(this);
        fileChooserButton.setBounds(0, 0, 100, 100);
        fileChosenName.setBounds(0, 100, WIDTH, 100);
        iterantionsLabel.setBounds(0, 200, 100, 100);
        iterationsField.setBounds(100, 200, 100, 100);
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
}
