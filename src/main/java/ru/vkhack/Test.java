package ru.vkhack;

import ru.vkhack.generating.ImageGenerator;
public class Test {

    public static void main(String[] args){
        try {

            new ImageGenerator().generateImage(
                    "/Users/victoria/IdeaProjects/vkhack/src/main/resources/back.jpg",
                    new String[]{"/Users/victoria/IdeaProjects/vkhack/src/main/resources/myphoto.jpg"},
                            "/Users/victoria/IdeaProjects/vkhack/src/main/resources/result.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
