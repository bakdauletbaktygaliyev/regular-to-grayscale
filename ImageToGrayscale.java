import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageToGrayscale {

    public static void main(String[] args) throws Exception {
        File inputFile = new File("bakdaulet.jpeg");
        File outputFile = new File("bakdaulet_converted.jpeg");

        try {
            BufferedImage image = ImageIO.read(inputFile);
            long startTime = System.currentTimeMillis();
            convertToGrayscaleConcurrently(image, 4);
            long endTime = System.currentTimeMillis();

            long timeTaken = endTime - startTime;
            System.out.println("Time taken " + timeTaken + " milliseconds");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void convertToGrayscaleConcurrently(BufferedImage image, int numberOfThreads)
            throws InterruptedException {

        int heightPerThread = image.getHeight() / numberOfThreads;
        GrayscaleConverterThread[] threads = new GrayscaleConverterThread[numberOfThreads];
        for (int i = 0; i < threads.length; i++) {
            int startY = i * heightPerThread;
            int endY = startY + heightPerThread;
            threads[i] = new GrayscaleConverterThread(image, startY, endY);
            threads[i].start();
        }
        for (GrayscaleConverterThread thread : threads) {
            thread.join();
        }

    }

}
