import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class pictureBlurrer{
    final static int SUM_TOTAL = 0;
    final static int TOTAL_NUMBERS_SUMMED = 1;
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
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                writer.write((int) img[i][j] + " ");

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
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                double[] values = sum3x3(source, i, j);
                dest[i][j] = values[SUM_TOTAL] / values[TOTAL_NUMBERS_SUMMED];
            }
        }
    }

    /**
     * main method that runs the blur method by reading a pgm file data into memory via 2D array and writes a new pgm file 
     * indicating how many iterations of the blur method were passed through 
     * @throws IOException
     */
    public static void runProgram() throws IOException {
        File file = Menu.getFileFromMenu();
        Scanner fileScanner = new Scanner(file);
        magicNumber = fileScanner.nextLine();
        numColumns = fileScanner.nextInt();
        numRows = fileScanner.nextInt();
        maxValue = fileScanner.nextInt();
        double[][] imageArray = new double[numColumns][numRows];
        double[][] blurredArray = new double[numColumns][numRows];
        int iterations = Menu.getNumOfIterations();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                imageArray[i][j] = fileScanner.nextInt();
            }
        }

        //new Frame(imageArray);
        blurredArray = imageArray;


        //blur(imageArray, blurredArray);
        for (int i = 0; i < iterations; i++) {
            blur(blurredArray, blurredArray);
        }
        
        writeImage(blurredArray, imgInfoToString(), getFileName(file, iterations)); //TODO uncomment this
        new Frame(blurredArray);

        fileScanner.close();
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

    
    /** 
     * The method I use to sum the values around the pixel at (row, col) requires pixels to be present around it, but
     * it does not work around the edges as the scanner would go out of bounds so by implementing a try catch method
     * it lets me sum the remaining pixels around the pixel at (row, col). One problem this arises is that an undesired
     * black border would form around the image, to combat this I predetermined the amount of pixels counted to 9 and
     * by utilizing the same try catch whenever a pixel would be skipped for being out bounds the number of pixels
     * accounted for in the average would lower by 1 for a more accurate average.
     * 
     * The rest is straightforward with a for loop inside another for loop to sum the pixels starting at the top left corner
     * 
     * 
     * This method returns two numbers in a form of an array, first number is the actual sum and the second is the
     * amount of pixels to be accounted for when taking the average of the pixels surrounding the pixel at (row, col)
     * 
     * @param img 2D array of the original pgm file
     * @param row row number of the pixel which the method will anchor from 
     * @param col column number of the pixel which the method will anchor from 
     * 
     * @return two values, the first number is the final sum of all the pixels around and in pixel of coordinates col, row, the second is
     * the number of pixels that were summed, typically 9 but in edge cases the number lowers as to avoid a black border
     * forming. Use constants pictureBlurrer.SUM_TOTAL and pictureBlurrer.TOTAL_NUMBERS_SUMMED for easy distinguishment
     */
    
    private static double[] sum3x3(double[][] img, int row, int col) {
        double[] values = { 0, 0 };
        double value = 0;
        double numOfnumbersSummed = 9;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    value += img[row - 1 + i][col - 1 + j];
                } catch (Exception e) {
                    numOfnumbersSummed--;
                }
            }
        }
        values[SUM_TOTAL] = value;
        values[TOTAL_NUMBERS_SUMMED] = numOfnumbersSummed;
        return values;
    }
}
