package ru.vkhack;

import ru.vkhack.generating.ImageGenerator;

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
                new Thread(new SocketProcessor(socket)).start();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                readInputHeaders();
//                writeResponse("<html><body><h1>Hello from Habrahabr</h1></body></html>");
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
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
                System.out.println(s);
            }
        }
    }
}
