package ru.vkhack.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.StringReader;

public class Parser {
    public static String getUploadUrl(String json){
        JsonParser parser = new JsonParser();
        JsonElement response = parser.parse(json).getAsJsonObject().get("response");
        JsonElement upload_url = response.getAsJsonObject().get("upload_url");
        return upload_url.getAsString();
    }

 public static String getPhotoId(String json){
        JsonParser parser = new JsonParser();
        JsonArray response = parser.parse(json).getAsJsonObject().get("response").getAsJsonArray();
        JsonElement pid = response.get(0).getAsJsonObject().get("pid");
        return pid.getAsString();
    }

    // "&photo=%s&server=%s&hash=%s&crop_data=%s&crop_hash=%s"
    public static String[] getPhotoInfo(String json){
        String[] result = new String[5];

        JsonParser parser = new JsonParser();
        JsonElement response = parser.parse(json);
        result[0] = response.getAsJsonObject().get("photo").getAsString();
        result[1] = response.getAsJsonObject().get("server").getAsString();
        result[2] = response.getAsJsonObject().get("hash").getAsString();
        result[3] = response.getAsJsonObject().get("crop_data").getAsString();
        result[4] = response.getAsJsonObject().get("crop_hash").getAsString();

        return result;
    }

//    public static String getPhotoRef(String json){
//        JsonParser parser = new JsonParser();
//        JsonElement response = parser.parse(json).getAsJsonObject().get("response");
//        return response.getAsJsonArray().get(0).getAsJsonObject().get("photo_max_orig").getAsString();
//    }

    public static String[] getProductInfo(String json){
        String[] result = new String[6];
        JsonParser parser = new JsonParser();
        JsonElement response = parser.parse(json).getAsJsonObject().get("response");
        JsonElement items = response.getAsJsonObject().get("items").getAsJsonArray().get(0);
        result[0] = items.getAsJsonObject().get("title").getAsString();
        result[1] = items.getAsJsonObject().get("category").getAsJsonObject().get("id").getAsString();
        result[2] = items.getAsJsonObject().get("description").getAsString();
        result[3] = items.getAsJsonObject().get("price").getAsJsonObject().get("amount").getAsString();
        result[4] = items.getAsJsonObject().get("id").getAsString();
        result[5] = items.getAsJsonObject().get("thumb_photo").getAsString();

        return result;
    }

    public static String[] getUserInfo(String json){
        String[] result = new String[5];
        JsonParser parser = new JsonParser();
        JsonElement response = parser.parse(json).getAsJsonObject().get("response").getAsJsonArray().get(0);
        result[0] = response.getAsJsonObject().get("first_name").getAsString() + " " +
                response.getAsJsonObject().get("last_name").getAsString();
        result[1] = response.getAsJsonObject().get("photo_max_orig").getAsString();

        return result;
    }
}
