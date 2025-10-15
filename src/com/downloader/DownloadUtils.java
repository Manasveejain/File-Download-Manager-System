package com.downloader;
import  java.io.*;
import java.net.*;
import java.nio.file.*;
public class DownloadUtils {
// validates given url
    public static boolean isValidUrl(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); //Only checks the headers ( no body)
            connection.setConnectTimeout(5000); //set time to 5 sec
            connection.setReadTimeout(5000);
            int responsecode = connection.getResponseCode();
            return (responsecode == HttpURLConnection.HTTP_OK);
        }
        catch(MalformedURLException e){
            System.err.println("Invalid URL: "+ urlString);
            return false;
        }
        catch(IOException e){
            System.err.println("Error checking URL: "+e.getMessage());
            return false;
        }
    }

     // Downloads a file from a URL and saves it to the specified destination.
    public static void downloadFile(String fileUrl , String destination) throws IOException{
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try (InputStream in = connection.getInputStream();
        FileOutputStream out = new FileOutputStream(destination)){
            byte[] buffer = new byte[4096];
            int bytesRead;
            while((bytesRead= in.read(buffer))!= -1){
                out.write(buffer, 0 , bytesRead);
            }
        }
        System.out.println("FIle download to: "+ destination);
    }

    /*
     * Verifies if the destination directory exists. If it doesn't, it creates the directory.
     */

    public static  boolean verifyDirectory(String directoryPath){
        Path path = Paths.get(directoryPath);
        if(!Files.exists(path)){
            try{
                Files.createDirectories(path);
                System.out.println("Directory Created: "+ directoryPath);
                return true;
            }
            catch(IOException e){
                System.out.println("Failed to create directory: "+e.getMessage());
                return false;
            }
        }
        return true;
    }
}
