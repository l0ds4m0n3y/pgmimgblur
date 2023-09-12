import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class pictureBlurrer{
    final static int SUM_TOTAL = 0;
    final static int TOTAL_NUMBERS_SUMMED = 1;

    private static void writeImage(double[][] img, String imgInfo, String fName) throws IOException {
        FileWriter writer = new FileWriter(fName + ".pgm");
        writer.write(imgInfo);
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                writer.write((int) img[i][j] + " ");

            }
            writer.write("\n");
        }

        writer.close();
    }

    private static void blur(double[][] source, double[][] dest) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                double[] values = sum3x3(source, i, j);
                dest[i][j] = values[SUM_TOTAL] / values[TOTAL_NUMBERS_SUMMED];
            }
        }
    }

    public static void runProgram() throws IOException {
        File file = Menu.getFileFromMenu();
        Scanner fileScanner = new Scanner(file);
        String magicNumber = fileScanner.nextLine();
        int numColumns = fileScanner.nextInt();
        int numRows = fileScanner.nextInt();
        int maxValue = fileScanner.nextInt();
        double[][] imageArray = new double[numColumns][numRows];
        double[][] blurredArray = new double[numColumns][numRows];
        int iterations = Menu.getNumOfIterations();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                imageArray[i][j] = fileScanner.nextInt();
            }
        }
        //new Frame(numRows, numColumns, imageArray);

        blur(imageArray, blurredArray);
        for (int i = 0; i < iterations; i++) {
            blur(blurredArray, blurredArray);
        }

        String imgInfo = magicNumber + "\n" + numColumns + " " + numRows + "\n" + maxValue + "\n";
        String fileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + "-blur-" + iterations;
        writeImage(blurredArray, imgInfo, fileName);
        new Frame(numRows, numColumns, blurredArray);

        fileScanner.close();
    }

    /*
     * 
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
     */
    private static double[] sum3x3(double[][] img, int row, int col) {
        double[] values = { 0, 0 };
        double value = 0;
        double numOfnumbersSummed = 9;
        final int LEFT_MARGIN = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    value += img[row - LEFT_MARGIN + i][col - LEFT_MARGIN + j];
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
