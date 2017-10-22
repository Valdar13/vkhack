package ru.vkhack;

import javafx.scene.layout.BorderStroke;
import ru.vkhack.generating.ImageGenerator;
import ru.vkhack.vk_api.Bot;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                /*new Thread(*/new SocketProcessor(socket).start();/*).start()*/;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    static class SocketProcessor /*implements Runnable */{

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void start() {
            try {
                    readInputHeaders();
                writeResponse("<html><body><h1>OK</h1></body></html>");
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
//            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
            os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
                if (s.contains("generateImage")) {
                    new ImageGenerator().generateImage(
                            "/Users/victoria/IdeaProjects/vkhack/src/main/resources/back.jpg",
                            new String[]{"/Users/victoria/IdeaProjects/vkhack/src/main/resources/myphoto.jpg"},
                            "/Users/victoria/IdeaProjects/vkhack/src/main/resources/result.jpg");
                    writeResponse("Image generated");
                    break;
                }
                if (s.contains("update")){

                    s = s.substring(s.indexOf("?") + 1);
                    String product_id = s.substring(s.indexOf("product_id="));
                    product_id = product_id.substring(11, product_id.indexOf("&"));

                    String user_id = s.substring(s.indexOf("user_id="));
                    user_id = user_id.substring(8, user_id.indexOf("&"));

                    String bid = s.substring(s.indexOf("bid="));
                    bid = bid.substring(4, bid.indexOf("&"));

                    String local_id = s.substring(s.indexOf("local_id="));
                    local_id = local_id.substring(9);
                    if (local_id.contains(" "))
                        local_id = local_id.split(" ")[0];

                    Bot.update(product_id, user_id, bid, local_id);
                    break;
                }
                else  if (s.contains("start")){

                    s = s.substring(s.indexOf("?") + 1);
                    String product_id = s.substring(s.indexOf("product_id="));
                    product_id = product_id.substring(11, product_id.indexOf("&"));

                    String price = s.substring(s.indexOf("price="));
                    price = price.substring(6, price.indexOf("&"));

                    String step = s.substring(s.indexOf("step="));
                    step = step.substring(5, step.indexOf("&"));

                    String local_id = s.substring(s.indexOf("local_id="));
                    local_id = local_id.substring(9);
                    if (local_id.contains(" "))
                        local_id = local_id.split(" ")[0];

                    Bot.start(product_id, local_id, price, step);
                    break;
                }else  if (s.contains("close")){

                    s = s.substring(s.indexOf("?") + 1);
                    String product_id = s.substring(s.indexOf("product_id="));
                    product_id = product_id.substring(11, product_id.indexOf("&"));

                    String user_id = s.substring(s.indexOf("user_id="));
                    user_id = user_id.substring(8, user_id.indexOf("&"));

                    String bid = s.substring(s.indexOf("bid="));
                    bid = bid.substring(4, bid.indexOf("&"));

                    String local_id = s.substring(s.indexOf("local_id="));
                    local_id = local_id.substring(9);
                    if (local_id.contains(" "))
                        local_id = local_id.split(" ")[0];

                    Bot.close(product_id, user_id, bid, local_id);
                    break;
                }
                System.out.println(s);
            }
        }
    }
}
