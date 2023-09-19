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
     * makes a new file for the blurred image
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
     * uses the sum3x3 method to average pixel values in the source 2D array and writes them in the dest 2D array
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
     * reads a pgm file, adds all the file info to variables and adds all pixel values to a 2D array
     * @param file file to read
     * @return double[][] 2D array of all the pixel values
     * @throws FileNotFoundException
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
        if(iterations < 0){
            writeImage(blurredArray, imgInfoToString(), getFileName(file, iterations)); //TODO comment this to avoid too many files
        }
        new Frame(blurredArray);
    }

    /** 
     * gets the name of the pgm file and adds the suffix indicating hte number of iterations the blur method was passed through
     * @param file Original file
     * @param iterations How many iterations of the blur method the original file will be passed through
     * @return the name of the final file
     */
    private static String getFileName(File file, int iterations){
        return file.getName().substring(0, file.getName().lastIndexOf(".")) + "-blur-" + iterations;
    }

    /** 
     * returns pgm file header information
     * @return pgm file header information
     */
    private static String imgInfoToString(){
        return magicNumber + "\n" + numColumns + " " + numRows + "\n" + maxValue + "\n";     
    }
    /**
     * returns the number of columns in the 2D array 
     * @return int number of columns in the 2D array 
     */
    public static int getNumColumns() {
        return numColumns;
    }
    /**
     * returns the number of rows in the 2D array 
     * @return int number of rows in the 2D array 
     */
    public static int getNumRows() {
        return numRows;
    }

    
    /** 
     * uses the wrap around method of iterating through an array to sum the pixel values in a 2D array 
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