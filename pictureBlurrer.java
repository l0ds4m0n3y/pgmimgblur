import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class pictureBlurrer{
    static String magicNumber;
    static int numColumns;
    static int numRows;
    static int maxValue;

    /** 
     * @param img 2D array of the output image
     * @param imgInfo a single string that contains the magic number, size, and max greyscale value that is generated in runprogram()
     * @param fName name of the originally inputted image
     * @throws IOException
     */

    private static void writeImage(double[][] img, String imgInfo, String fName) throws IOException {
        FileWriter writer = new FileWriter(fName + ".pgm");
        writer.write(imgInfo);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                writer.write((int) Math.ceil(img[i][j]) + " ");
            }
            writer.write("\n");
        }

        writer.close();
    }

    /** 
     * @param source the 2D array that is already in memory that the method will be reading from
     * @param dest  the 2D array of where the output of the method will be entered
     */
    private static void blur(double[][] source, double[][] dest) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                dest[i][j] = sum3x3(source, i, j);
            }
        }
    }
    
    /**
     * @param file file to read
     * @return double[][] 2D array of all the pixel values
     */
    public static double[][] pgmToArray(File file) throws FileNotFoundException{
        Scanner fileScanner = new Scanner(file);
        magicNumber = fileScanner.nextLine();
        numColumns = fileScanner.nextInt();
        numRows = fileScanner.nextInt();
        maxValue = fileScanner.nextInt();
        double[][] imageArray = new double[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                imageArray[i][j] = fileScanner.nextInt();
            }
        }
        fileScanner.close();
        return imageArray;
    }

    /**
     * main method that runs the blur method by reading a pgm file data into memory via 2D array and writes a new pgm file 
     * indicating how many iterations of the blur method were passed through 
     * @param iterations how many times the blur() method will be ran
     * @throws IOException
     */
    public static void runProgram(int iterations) throws IOException {
        File file = Menu.getFileFromMenu();
        double[][] blurredArray = new double[numRows][numColumns];
        double[][] imageArray = pgmToArray(file);

        //new Frame(imageArray);
        blurredArray = imageArray;

        for (int i = 0; i < iterations; i++) {
            blur(blurredArray, blurredArray);
        }
        
        writeImage(blurredArray, imgInfoToString(), getFileName(file, iterations)); //TODO uncomment this
        new Frame(blurredArray);
    }

    /** 
     * @param file Original file
     * @param iterations How many iterations of the blur method the original file will be passed through
     * @return the name of the final file
     */
    private static String getFileName(File file, int iterations){
        return file.getName().substring(0, file.getName().lastIndexOf(".")) + "-blur-" + iterations;
    }

    /** 
     * @return pgm file information
     */
    private static String imgInfoToString(){
        return magicNumber + "\n" + numColumns + " " + numRows + "\n" + maxValue + "\n";     
    }

    public static int getNumColumns() {
        return numColumns;
    }
     
    public static int getNumRows() {
        return numRows;
    }

    
    /** 
     * @param img 2D array of the original pgm file
     * @param row row number of the pixel which the method will anchor from 
     * @param col column number of the pixel which the method will anchor from 
     * 
     * @return the average pixel value of the pixels surrounding (row, col)
     */
    
    private static double sum3x3(double[][] img, int row, int col) {
        double currSum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                currSum += img[(row - 1 + i + numRows) % numRows][(col - 1 + j + numColumns) % numColumns];
            }
        }
        return currSum/9;
    }
}