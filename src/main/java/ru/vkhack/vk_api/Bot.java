package ru.vkhack.vk_api;

public class Bot {

    public static void main(String[] args){
        new Getter().sendAndReceive("https://api.vk.com/method/photos.getMarketUploadServer" +
                "?group_id=6228501&access_token=8a711f8236870c26a42f4929305024055579251d105cab95864e5f436ee42c4a31320398f10851e175e25" +
                "&main_photo=1&crop_x=400&crop_y=0&crop_width=400");
    }

}
