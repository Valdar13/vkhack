package ru.vkhack.vk_api;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Bot {

    public static void main(String[] args){
//        new Getter().sendAndReceive("https://api.vk.com/method/photos.getMarketUploadServer" +
//                "?group_id=-155409027" +
//                "&main_photo=1&crop_x=400&crop_y=50&crop_width=400" +
//                "&access_token=c2c5e4261d542661e411d5df25b7090ae8bb15382ee1d0d82a9f48f0b24469cc0cc9f36400f59e6bfa67c");

//            new Getter().sendAndReceive("https://oauth.vk.com/authorize?client_id=5792770" +
//                    "&group_ids=155409027&display=popup&" +
//                        "redirect_uri=blank.html&scope=manage+photos&response_type=token&v=5.68");
        Getter getter = new Getter();

//        getter.sendAndReceive("https://api.vk.com/method/users.get?user_id=11637405&v=5.68");

        getter.sendAndReceive("https://api.vk.com/method/photos.getOwnerCoverPhotoUploadServer" +
                "?group_id=155409027&crop_x=0&crop_y=0&crop_x2=1590&crop_y2=400" +
                "&access_token=c2c5e4261d542661e411d5df25b7090ae8bb15382ee1d0d82a9f48f0b24469cc0cc9f36400f59e6bfa67c&v=5.64");
        getter.sendAndReceive("https://api.vk.com/method/market.get?owner_id=-155409027" +
                "&album_id=0&count=0&offset=0&v=5.68" +
                "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc");

        getter.sendAndReceive("https://api.vk.com/method/photos.getMarketUploadServer?group_id=155409027" +
                "&main_photo=1&crop_x=400&crop_y=0&crop_width=400&v=5.68" +
                "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc");

        String uploadURL = "https:\\\\pu.vk.com\\c621515\\upload.php" +
                "?act=do_add&mid=68098233&aid=-53&gid=155409027" +
                "&hash=3688dfb5a0482105c346a284cb49994f&rhash=5cde7eb761719a97b0f8e89a754c48db" +
                "&swfupload=1&api=1&market_main_photo=1&_crop=400,0,400";

        getter.sendPost(uploadURL, new File("/Users/victoria/IdeaProjects/vkhack/src/main/resources/cat.jpeg"));
//        getter.sendAndReceive("https://api.vk.com/method/market.add/owner_id=-155409027" +
//                "&name=Новый товар&description=Очень классный товар&");

    }


}
