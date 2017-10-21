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
}
