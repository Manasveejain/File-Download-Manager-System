package com.downloader;
import java.util.concurrent.*;
import java.util.*;
public class DownloadManager {
    private  static  final int NUM_THREADS = 4;
    private ExecutorService executorService;
    public DownloadManager(){
        executorService = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public  void startDownload(String[] fileUrls , String destination){
        List<Callable<String>> tasks = new ArrayList<>();
        for(String fileUrl : fileUrls){
            tasks.add(new DownloadTask(fileUrl, destination));
        }
        try{
            List<Future<String>> results = executorService.invokeAll(tasks);
            for(Future<String> result : results){
                try{
                    System.out.println(result.get());
                }
                catch (ExecutionException | InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }
    }
    public static  void main(String[] args){
        String[] fileUrls = {
                "http://example.com/file1.zip",
                "http://example.com/file2.zip",
                "http://example.com/file3.zip",
                "http://example.com/file4.zip"
        };
        String destination = "/path/to/save/files";
        DownloadManager downloadManager = new DownloadManager();
        downloadManager.startDownload(fileUrls , destination);
    }
}
