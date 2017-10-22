package ru.vkhack.generating;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator2 {

    public void generateImage(String pathToBackgroundFile,String pathAvatar, String leedName, String maxBid,
                                  String pathToResultFile) throws IOException {
        BufferedImage background = ImageIO.read(new File(pathToBackgroundFile));

        BufferedImage Avatar = ImageIO.read(new File(pathAvatar));

        BufferedImage result = new BufferedImage(background.getWidth(),
                background.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // В качестве "холста" берём наше новое изображение
        Graphics2D g = (Graphics2D) result.getGraphics();

// Рисуем фон
        g.drawImage(background, 0, 0, null);


        // Рисуем лого лота
        addRoundAvatar(g, Avatar, Avatar.getWidth()/ 3,Avatar.getHeight()/3,193,422);
//        g.drawImage(imageLayer, 30, 50, imageLayer.getWidth(),
//                imageLayer.getHeight(), null);

// Подписываем номер лота
        g.setPaint(Color.BLACK);
        g.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 100));
        // g.setClip(0,0,background.getWidth(),background.getHeight());



// Записываем результат на диск
        ImageIO.write(result, "PNG", new File(pathToResultFile));


        BufferedImage res2 = ImageIO.read(new File(pathToResultFile));

        Graphics2D g2 =  (Graphics2D) res2.getGraphics();

        g2.setPaint(Color.BLACK);

        g2.setFont(new Font("ANDALE MONO", Font.BOLD, 60));
        g2.drawString(leedName.toUpperCase(), 153, 912);

        g2.setPaint(Color.WHITE);
        g2.setFont(new Font("ANDALE MONO", Font.BOLD, 100));
        g2.drawString(maxBid + " руб.", 153, 1212);
        ImageIO.write(res2, "PNG", new File(pathToResultFile));
    }

    //создает файлы с заданн
    public void createTemplatesForLot(String pathToBackgroundStart,String pathToBackgroundActive,String pathToBackgroundClose,String pathLotImage,
                                 String lotId, String pathForResults) throws IOException {

        //load default templates
        BufferedImage templateStart = ImageIO.read(new File(pathToBackgroundStart));
        BufferedImage templateActive = ImageIO.read(new File(pathToBackgroundActive));
        BufferedImage templateClose = ImageIO.read(new File(pathToBackgroundClose));

        BufferedImage lotImage = ImageIO.read(new File(pathLotImage));



        // В качестве "холста" берём наше новое изображение
        Graphics2D gStart = (Graphics2D) templateStart.getGraphics();
        Graphics2D gActive = (Graphics2D) templateActive.getGraphics();
        Graphics2D gClose = (Graphics2D) templateClose.getGraphics();


        // Рисуем лого лота
        addScaledLotImage(gStart, lotImage, (int)(lotImage.getWidth()*1.9),(int)(lotImage.getHeight()*1.9),800,370);
        addScaledLotImage(gActive, lotImage, lotImage.getWidth(),lotImage.getHeight(),800,370);
        addScaledLotImage(gClose, lotImage, lotImage.getWidth(),lotImage.getHeight(),800,370);


// Подписываем номер лота
        gStart.setPaint(Color.BLACK);
        gStart.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 110));
        gStart.drawString(lotId, 1250, 210);

        gActive.setPaint(Color.BLACK);
        gActive.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 110));
        gActive.drawString(lotId, 1250, 210);

        gClose.setPaint(Color.BLACK);
        gClose.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 110));
        gClose.drawString(lotId, 1250, 210);

// Записываем результат на диск
        ImageIO.write(templateStart, "PNG", new File(pathForResults+ "/templateStart"+lotId+".jpg"));
        ImageIO.write(templateActive, "PNG", new File(pathForResults+ "/templateActive"+lotId+".jpg"));
        ImageIO.write(templateClose, "PNG", new File(pathForResults+ "/templateClose"+lotId+".jpg"));
    }

    //создает c текущеймаксимальной ставкой и информацией о лидере
    public void updateImageWithUserInfo(String pathToBackgroundFile,String pathAvatar, String leedName, String maxBid, String lotId,
                                 String pathToResultFile) throws IOException {
        BufferedImage background = ImageIO.read(new File(pathToBackgroundFile));

        BufferedImage Avatar = ImageIO.read(new File(pathAvatar));

        BufferedImage result = new BufferedImage(background.getWidth(),
                background.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // В качестве "холста" берём наше новое изображение
        Graphics2D g = (Graphics2D) result.getGraphics();

// Рисуем фон
        g.drawImage(background, 0, 0, null);


        // Рисуем лого лота
        addRoundAvatar(g, Avatar, Avatar.getWidth(),Avatar.getHeight(),193,422);
//        g.drawImage(imageLayer, 30, 50, imageLayer.getWidth(),
//                imageLayer.getHeight(), null);

// Подписываем номер лота
        g.setPaint(Color.BLACK);
        g.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 75));
       // g.setClip(0,0,background.getWidth(),background.getHeight());



// Записываем результат на диск
        ImageIO.write(result, "PNG", new File(pathToResultFile + "/Updated"+ lotId + ".png"));


        BufferedImage res2 = ImageIO.read(new File(pathToResultFile + "/Updated"+ lotId + ".png"));

         Graphics2D g2 =  (Graphics2D) res2.getGraphics();

        g2.setPaint(Color.BLACK);

        g2.setFont(new Font("ANDALE MONO", Font.BOLD, 50));
         g2.drawString(leedName.toUpperCase(), 153-35, 912);

        g2.setPaint(Color.WHITE);
        g2.setFont(new Font("ANDALE MONO", Font.BOLD, 100));
        g2.drawString(maxBid + " руб.", 153, 1212);
        ImageIO.write(res2, "PNG", new File(pathToResultFile + "/Updated"+ lotId + ".png"));
    }

    private void addScaledImage(Graphics g, BufferedImage image, int width, int height,
                                int x, int y){
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      //  g.setClip(new Ellipse2D.Double(x+10, y+10, width-10, height-10));
        g.drawImage(scaledInstance, x, y, null);
    }

    private void addScaledLotImage(Graphics g, BufferedImage image, int width, int height,
                                int x, int y){
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        //  g.setClip(new Ellipse2D.Double(x+10, y+10, width-10, height-10));
        g.drawImage(scaledInstance, x, y, null);
    }

    private void addRoundAvatar(Graphics g, BufferedImage image, int width, int height,
                                   int x, int y){
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
          g.setClip(new Ellipse2D.Double(x, y, 400, 400));
        g.drawImage(scaledInstance, x, y, null);
    }

}
