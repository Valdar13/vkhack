package ru.vkhack.models;
import ru.vkhack.utils.Parser;
import ru.vkhack.vk_api.Bot;
import ru.vkhack.vk_api.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Product {

    public  String name;
    public String category;
    public String description;
    public  String price;
    public String id;
    public String localId;
    public String step;

    public Product(String productId, String step, String _localId, String pathToDir){
        String responce = "https://api.vk.com/method/market.getById?item_ids=-" + Bot.COMMUNITY_ID + "_" +productId+
                "&extended=1&v=5.68" + "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc";
        String json = Getter.sendAndReceive(responce);
        String [] result = Parser.getProductInfo(json);


        name = result[0];
        category = result[1];
        description = result[2];
        price = result[3];
        id = result[4];
        localId = _localId;
        this.step = step;

        saveImg(result[5], pathToDir + "/marketPhoto.png");
    }

    public Product(String productId){
        String responce = "https://api.vk.com/method/market.getById?item_ids=-" + Bot.COMMUNITY_ID + "_" +productId+
                "&extended=1&v=5.68" + "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc";
        String json = Getter.sendAndReceive(responce);
        String [] result = Parser.getProductInfo(json);
        name = result[0];
        category = result[1];
        description = result[2];
        price = result[3];
        id = result[4];
    }

    private void saveImg(String url, String pathToFile){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
            ImageIO.write(image, "PNG", new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}