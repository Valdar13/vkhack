package ru.vkhack.models;
import ru.vkhack.utils.Parser;
import ru.vkhack.vk_api.Getter;

public class Product {

    public  String name;
    public String category;
    public String description;
    public  String price;
    public String id;
    public String localId;


    public Product(String productId, String _localId){
        String responce = "https://api.vk.com/method/market.getById?item_ids=-155409027_" +productId+
                "&v=5.68" + "&access_token=00ac1e2be709aec6240e075e726d524470d23973047663a959554e35d7f93ce255a251dbf28394aa82ccc";
        String json = Getter.sendAndReceive(responce);
        String [] result = Parser.getProductInfo(json);


        name = result[0];
        category = result[1];
        description = result[2];
        price = result[3];
        id = result[4];
        localId = _localId;


    }
}