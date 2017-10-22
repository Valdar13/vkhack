package ru.vkhack.vk_api;

import ru.vkhack.generating.ImageGenerator2;
import ru.vkhack.models.Product;
import ru.vkhack.utils.Parser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

public class Bot {
    private static Getter getter = new Getter();
    private static String PATH_TO_DIR;
//            "/Users/victoria/IdeaProjects/vkhack/src/main/resources/";
    public static final String COMMUNITY_ID = "155403696"; //"155409027";
    private static Product product = null;
    private static ImageGenerator2 imgGenerator = new ImageGenerator2();

    static {
        try{
            PATH_TO_DIR = URLDecoder.decode(
                    Bot.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                    , "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args){
////        product = new Product("1028919", "1", PATH_TO_DIR + "lot1/");
////        new Getter().sendAndReceive("https://api.vk.com/method/photos.getMarketUploadServer" +
////                "?group_id=-155409027" +
////                "&main_photo=1&crop_x=400&crop_y=50&crop_width=400" +
////                "&access_token=c2c5e4261d542661e411d5df25b7090ae8bb15382ee1d0d82a9f48f0b24469cc0cc9f36400f59e6bfa67c");
//
////            new Getter().sendAndReceive("https://oauth.vk.com/authorize?client_id=5792770" +
////                    "&group_ids=155409027&display=popup&" +
////                        "redirect_uri=blank.html&scope=manage+photos&response_type=token&v=5.68");
////        Getter getter = new Getter();
//
////        getter.sendAndReceive("https://api.vk.com/method/users.get?user_id=11637405&v=5.68");
//
//        getter.sendAndReceive("https://api.vk.com/method/photos.getOwnerCoverPhotoUploadServer" +
//                "?group_id=155409027&crop_x=0&crop_y=0&crop_x2=1590&crop_y2=400" +
//                "&access_token=c2c5e4261d542661e411d5df25b7090ae8bb15382ee1d0d82a9f48f0b24469cc0cc9f36400f59e6bfa67c&v=5.64");
//        getter.sendAndReceive("https://api.vk.com/method/market.get?owner_id=-155409027" +
//                "&album_id=0&count=0&offset=0&v=5.68" +
//                "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc");
//
//        String json = getter.sendAndReceive("https://api.vk.com/method/photos.getMarketUploadServer?group_id=155409027" +
//                "&main_photo=1&crop_x=400&crop_y=0&crop_width=400&v=5.68" +
//                "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc");
//
////        String uploadURL = "https://pu.vk.com/c621515/upload.php" +
////                "?act=do_add&mid=68098233&aid=-53&gid=155409027" +
////                "&hash=3688dfb5a0482105c346a284cb49994f&rhash=5cde7eb761719a97b0f8e89a754c48db" +
////                "&swfupload=1&api=1&market_main_photo=1&_crop=400,0,400";
//
//        String uploadURL = Parser.getUploadUrl(json);
//
////        ---- Sending jpg -------------
////        getter.sendPost(uploadURL, new File("/Users/victoria/IdeaProjects/vkhack/src/main/resources/active.png"));
////        ------------------------------
//
//        String photo = "[{\"photo\":\"6265fb4705:y\",\"sizes\":[[\"s\",\"621515552\",\"29795\",\"4oQvfT3yzws\",75,63]," +
//                "[\"m\",\"621515552\",\"29796\",\"V50u7HhSwX8\",130,110],[\"x\",\"621515552\",\"29797\",\"vqhng_-xMk0\",604,512]" +
//                ",[\"y\",\"621515552\",\"29798\",\"YMnKiEUWD5o\",640,542],[\"o\",\"621515552\",\"29799\",\"JjCMkE_niK0\",130,110]," +
//                "[\"p\",\"621515552\",\"2979a\",\"oQWsDiSZuWM\",200,169],[\"q\",\"621515552\",\"2979b\",\"rKM_BIRC6d0\",320,271]," +
//                "[\"r\",\"621515552\",\"2979c\",\"sv04JsklD5Q\",510,432]],\"kid\":\"7ab8a3a772d92d15a6a6db89c9227ea6\",\"debug\":\"xsymyxyyyoypyqyry\"}]";
//
//        String photoHash = "c19c91b8f2cf42102826653697420d86";
//        String server = "621515";
//        String cropData = "oAAl7ywAAAAAlC5MgAAKXnfhFWm3JuYH8B*CAAKXnqDuzhpjbPgEDAAKXnxtEk1BLGEwrEAAKXoOZjG99IdmxdFAAKXocIhx7GFhwTEGAAKXotHvayHt8UUd";
//        String cropHash = "c9c7e788ba2129ae8423aef283a600bb";
//
//        String[] photoInfo = Parser.getPhotoInfo(
//                getter.sendPost(uploadURL, new File("/Users/victoria/IdeaProjects/vkhack/src/main/resources/active.png")));
//
//        String savedPhoto = getter.sendAndReceive(String.format("https://api.vk.com/method/photos.saveMarketPhoto?group_id=155409027" +
//                        "&photo=%s&server=%s&hash=%s&crop_data=%s&crop_hash=%s" +
//                        "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc"
//                , photoInfo[0], photoInfo[1], photoInfo[2], photoInfo[3], photoInfo[4]));
////                , photo, server, photoHash, cropData, cropHash));
//
//        String photo_id = "456239029";
//
//        photo_id = Parser.getPhotoId(savedPhoto);
//        getter.sendAndReceive(String.format("https://api.vk.com/method/market.edit?owner_id=-155409027" +
//                "&item_id=1028919&name=Новый_товар&description=Очень_классный_товар&category_id=1" +
//                "&price=100500&main_photo_id=%s" +
//                "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc",
//                photo_id));
//
////        getUserInfo();
//    }

    static String[] getUserInfo(String user_id, String local_id /*"68098233"*/){
        String[] userInfo = new String[2];
        String json = getter.sendAndReceive(String.format(
                "https://api.vk.com/method/users.get?user_id=%s&fields=photo_max_orig&v=5.52",
                user_id));
        String pathToAva = PATH_TO_DIR + "lot" + local_id + "/userPhoto.jpg";
        try {
//            BufferedImage userPhoto = ImageIO.read(new URL(Parser.getPhotoRef(json)));
            userInfo = Parser.getUserInfo(json);
            BufferedImage userPhoto = ImageIO.read(new URL(userInfo[1]));
            ImageIO.write(userPhoto, "JPG",
                    new File(pathToAva));
            userInfo[1] = pathToAva;
//            String[] userInfo = Parser.getUserInfo(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        json = getter.sendAndReceive("https://api.vk.com/method/photos.get?owner_id=50943396" +
//                "&album_id=profile&count=1&v=5.52");

        return userInfo; // TODO: return name and path to ava
    }


    public static void start(String product_id, String local_id, String price, String step){
        String pathToCurDir = PATH_TO_DIR + "lot" + local_id;
        product = new Product(product_id, step, local_id, pathToCurDir + "/");

        try {
            imgGenerator.createTemplatesForLot(pathToCurDir + "/start.png",
                    pathToCurDir + "/active.png",
                    pathToCurDir + "/close.png",
                    pathToCurDir + "/marketPhoto.png",
                    local_id,
                    pathToCurDir);

            editMarket(product_id, price, local_id, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void close(String product_id, String user_id, String bid, String local_id){
        String pathToCurDir = PATH_TO_DIR + "lot" + local_id;
        String[] userInfo = getUserInfo(user_id, local_id);
        if (product == null)
            product = new Product(product_id);
        try {
            imgGenerator.updateImageWithUserInfo(pathToCurDir + "/templateClose" +
                            local_id + ".jpg",
                     userInfo[1], userInfo[0], bid, local_id, PATH_TO_DIR + "lot" + local_id);
            editMarket(product_id, bid, local_id, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(String product_id, String user_id, String bid, String local_id){
        String pathToActiveImg = PATH_TO_DIR + "lot" + local_id + "/templateActive" + local_id + ".jpg";
        String[] userInfo = getUserInfo(user_id, local_id);
//        product = new Product(product_id, local_id, PATH_TO_DIR + "lot" + local_id + "/");

        if (product == null)
            product = new Product(product_id);

        try {
            imgGenerator.updateImageWithUserInfo(pathToActiveImg, /*"/Users/victoria/IdeaProjects/vkhack/src/main/resources/myphoto.jpg"*/userInfo[1], userInfo[0],
                    bid, local_id, PATH_TO_DIR + "lot" + local_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editMarket(product_id, bid, local_id, false); // send updated market info to the vk
    }

    static void editMarket(String product_id  /*"1028919"*/, String bid, String local_id, boolean needInitDescription){
//        getter.sendAndReceive("https://api.vk.com/method/photos.getOwnerCoverPhotoUploadServer" +
//                "?group_id=155409027&crop_x=0&crop_y=0&crop_x2=1590&crop_y2=400" +
//                "&access_token=c2c5e4261d542661e411d5df25b7090ae8bb15382ee1d0d82a9f48f0b24469cc0cc9f36400f59e6bfa67c&v=5.64");

        String json = getter.sendAndReceive("https://api.vk.com/method/photos.getMarketUploadServer?" +
                "group_id=" + COMMUNITY_ID +
                "&main_photo=1" +
                "&crop_x=1000" +
                "&crop_y=0" +
                "&crop_width=1700" +
                "&v=5.68" +
                "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc");

        String uploadURL = Parser.getUploadUrl(json);


        File loadingFile;
        if (needInitDescription) {
            product.description = "Шаг: " + product.step + "%0D%0A" + product.description;
            loadingFile = new File(/*"/Users/victoria/IdeaProjects/vkhack/src/main/resources/active.png"*/
                    PATH_TO_DIR + "lot" + local_id + "/templateStart" + local_id + ".jpg");
        } else
            loadingFile = new File(/*"/Users/victoria/IdeaProjects/vkhack/src/main/resources/active.png"*/
                PATH_TO_DIR + "lot" + local_id + "/Updated" + local_id + ".png");

        String[] photoInfo = Parser.getPhotoInfo(
                getter.sendPost(uploadURL, loadingFile));

        String savedPhoto = getter.sendAndReceive(String.format("https://api.vk.com/method/photos.saveMarketPhoto?group_id=" +
                        COMMUNITY_ID +
                        "&photo=%s&server=%s&hash=%s&crop_data=%s&crop_hash=%s" +
                        "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc"
                , photoInfo[0], photoInfo[1], photoInfo[2], photoInfo[3], photoInfo[4]));

        String photo_id = Parser.getPhotoId(savedPhoto);

        product.name = replaceSpaces(product.name);
        product.description = replaceSpaces(product.description);


        getter.sendAndReceive(String.format("https://api.vk.com/method/market.edit?owner_id=-%s" +
                        "&item_id=%s&name=%s&description=%s&category_id=%s" +
                        "&price=%s&main_photo_id=%s" +
                        "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc"
                ,COMMUNITY_ID, product_id, product.name, product.description, product.category
                , bid, photo_id));
    }

    private static String replaceSpaces(String str){
//        str = str.replace("\" \"", " ");
//        return str.replace(" ", "\" \"");
        str = str.replace("\n", "%0A");
        return str.replace(" ", "%20");
    }
}
