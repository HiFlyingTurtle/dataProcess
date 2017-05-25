package com.cms.test;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**  
* 创建时间：2017年5月15日 上午10:03:50  
* @author wwh 
* @since JDK 1.8
* 文件名称：FileReader.java  
*/
public class FileReader implements Runnable {
    private final BlockingQueue<String> sharedQueue;
	
	private final Line line;
	
	private String fileName = "D:\\stockData\\2006.csv";
	
	public FileReader(BlockingQueue<String> sharedQueue, Line line,String fileName){
		this.sharedQueue = sharedQueue;
		this.line = line;
		this.fileName = fileName;
	}
	
	public void readFile(){
		System.out.println("file reader Begainning....");
		try{
			long start = System.currentTimeMillis();
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(this.fileName)));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis),  10* 1024 * 1024);
			int count=0;
			while(in.ready()){   
				String line = in.readLine();  
				count++;
				sharedQueue.put(line);
			}
			System.out.println("read line count:"+count);
	//		System.out.println("read file:"+sharedQueue.size());
			in.close();
			long end = System.currentTimeMillis();
			line.add();
			System.out.println("第"+line.i+"次"+"read  file time spend:"+(end - start));
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("file reader Ending....");
	}
	
	@Override
	public void run() {
		try{
			readFile();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
     public static void main(String[] args) {
    	 System.out.println("start...");
    	 BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<String>();
         Line line=new Line();
    	 new Thread(new FileReader(sharedQueue, line, "D:\\stockData\\2006.csv")).start();
    	 new Thread(new LineDealer(sharedQueue, line)).start();
		 System.out.println("ending..."); 
	}

}
