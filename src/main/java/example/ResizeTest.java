package example;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeTest {
    public static void main(String args[]) throws IOException {
        String inputWebpPath = "test_pic/test.webp";
        String outputWebpPath = "test_pic/test_resize.webp";

        // Obtain a WebP ImageReader instance
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

        // Configure decoding parameters
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);

        // Configure the input on the ImageReader
        reader.setInput(new FileImageInputStream(new File(inputWebpPath)));

        // Decode the image
        BufferedImage image = reader.read(0, readParam);
        int height = image.getHeight();
        int width = image.getWidth();

        BufferedImage resizedImage = new BufferedImage(width * 2, height * 2, image.getType());
        Graphics graphics = resizedImage.getGraphics();
        graphics.drawImage(image, 0, 0, width * 2, height * 2, null);
        graphics.dispose();

        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
        // Configure encoding parameters
        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        writeParam.setCompressionMode(WebPWriteParam.MODE_DEFAULT);

        // Configure the output on the ImageWriter
        writer.setOutput(new FileImageOutputStream(new File(outputWebpPath)));

        // Encode
        writer.write(null, new IIOImage(resizedImage, null, null), writeParam);
    }
}
