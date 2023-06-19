package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing renderer.ImageWriter
 */
class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}.
     * In this test we will create a grid of 10x16 red squares
     * on a yellow background measuring 800x500 pixels
     */
    @Test
    void testWriteToImage() {
        final int width = 801;
        final int height = 501;
        final int step = 50;
        final Color color1 = new Color(255, 0, 0);
        final Color color2 = new Color(255, 255, 0);

        ImageWriter imageWriter = new ImageWriter("test", width, height);//create a file for the image
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                imageWriter.writePixel(j, i, i % step == 0 || j % step == 0 ? color1 : color2);
        imageWriter.writeToImage();
    }
}