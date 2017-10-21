package ru.vkhack.generating;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {

    public void generateImage(String pathToBackgroundFile,String[] pathsToAdditionalImages,
                              String pathToResultFile) throws IOException {
        BufferedImage background = ImageIO.read(new File(pathToBackgroundFile));

        BufferedImage imageLayer = ImageIO.read(new File(pathsToAdditionalImages[0]));

        BufferedImage result = new BufferedImage(background.getWidth(),
                background.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // В качестве "холста" берём наше новое изображение
        Graphics2D g = (Graphics2D) result.getGraphics();

// Рисуем фон
        g.drawImage(background, 0, 0, null);


        // Рисуем аватар подписчика
        addScaledImage(g, imageLayer, imageLayer.getWidth()/4,imageLayer.getHeight()/4,30,50);
//        g.drawImage(imageLayer, 30, 50, imageLayer.getWidth(),
//                imageLayer.getHeight(), null);

// Подписываем имя подписчика
        g.setPaint(Color.BLACK);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 50));
        g.drawString("SomeString", 30, background.getHeight() - 50);

// Записываем результат на диск
        ImageIO.write(result, "PNG", new File(pathToResultFile));
    }

    private void addScaledImage(Graphics g, BufferedImage image, int width, int height,
                                int x, int y){
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        g.setClip(new Ellipse2D.Double(x+10, y+10, width-10, height-10));
        g.drawImage(scaledInstance, x, y, null);
    }
}
