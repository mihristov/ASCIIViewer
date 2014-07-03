import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;

public class ASCIIViewer {
    private static BufferedImage img = null;

    static int getAvgIntensityForBlock(int x, int y, int blockLength) {
        int allIntensity = 0;
        final int countPixels = blockLength * blockLength;
        for (int i = x; i < x + blockLength; i++) {
            for (int j = y; j < y + blockLength; j++) {
                if (i < img.getWidth() && j < img.getHeight()) {
                    allIntensity += getIntensity(i, j);
                }
            }
        }
        return allIntensity / countPixels;
    }

    static int getIntensity(int x, int y) {
        final int clr = img.getRGB(x, y);
        final Color color = new Color(clr, false);
        final int intensity = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
        return intensity;
    }

    public static void main(String[] args) throws IOException {
        final File file = new File(args[0]);

        try {
            img = ImageIO.read(file);
        } catch (final IOException e) {
        }

        final OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(FileDescriptor.out), "ASCII");

        final int consoleColumns = 80;
        // final int consoleRows = 43;

        final int blockSize = (img.getHeight() / consoleColumns) + 1;
        // int numberOfBlocksWidth = (img.getWidth() / consoleRows) + 1;

        // if (numberOfBlocksHeight <= numberOfBlocksWidth){
        // numberOfBlocksHeight = numberOfBlocksWidth;
        // }
        // else{
        // numberOfBlocksWidth = numberOfBlocksHeight;
        // }

        for (int y = 0; y < img.getHeight(); y += blockSize) {
            for (int x = 0; x < img.getWidth(); x += blockSize) {
                final int intensity = getAvgIntensityForBlock(x, y, blockSize);
                if (intensity > 240) {
                    out.write(" ");
                }
                if (intensity > 200 && intensity < 240) {
                    out.write(".");
                }
                if (intensity > 160 && intensity < 200) {
                    out.write("*");
                }
                if (intensity > 120 && intensity < 160) {
                    out.write("+");
                }
                if (intensity > 80 && intensity < 120) {
                    out.write("x");
                }
                if (intensity > 40 && intensity < 80) {
                    out.write("%");
                }
                if (intensity > 0 && intensity < 40) {
                    out.write("#");
                }
            }
            out.write("\n");
        }
        out.close();
    }
}
