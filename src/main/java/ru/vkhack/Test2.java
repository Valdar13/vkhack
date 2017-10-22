package ru.vkhack;

import ru.vkhack.generating.ImageGenerator2;
public class Test2 {

    public static void main(String[] args){
        try {
            //последний параметр папка в которой будут хранится созданные шаблоны
            new ImageGenerator2().createTemplatesForLot(
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/start.png",
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/active.png",
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/close.png",
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/myphoto.jpg",
                    "LOTID",
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/results"
                        );
                   new ImageGenerator2().updateImageWithUserInfo(
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/results/templateActiveLOTID.jpg",
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/myphoto.jpg",
                    "Vika Savinova",
                    "1999",
                    "LOTID",
                    "/Users/vladimir/IdeaProjects/vkhack/src/main/resources/results");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
