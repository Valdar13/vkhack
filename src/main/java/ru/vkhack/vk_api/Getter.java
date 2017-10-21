package ru.vkhack.vk_api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Getter {

    void sendAndReceive(String url){
        try{
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printResponse(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
    }

    void sendPost(String address, File file){
        String params = "file=";
        byte[] data = null;
        InputStream is = null;

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
            OutputStream os = conn.getOutputStream();
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = fileReader.readLine()) != null)
                params = params.concat(line);
            data = params.getBytes("UTF-8");
            os.write(data);
            data = null;
            conn.connect();

            //Response:
            int responseCode= conn.getResponseCode();

            printResponse(conn.getInputStream());
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            is = conn.getInputStream();
//
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//            data = baos.toByteArray();
        } catch (Exception e) {
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {}
        }
    }
}