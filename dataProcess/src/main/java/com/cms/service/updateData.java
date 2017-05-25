package com.cms.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cms.bean.Context;
import com.cms.writer.CsvWriter;
/**  
* 创建时间：2017年5月18日 下午1:38:29  
* @author wwh 
* @since JDK 1.8
* 实现增量数据的更新
* 文件名称：updateData.java  
*/
public class updateData {
	/**
	 * 实现思路：  存储当天数据的文件按照
	 *        文件名按照日期来命名 如:20160501
	 *        1.运行程序，检查指定的文件存储路径下是否存在2017这个目录，如果不存在则新建2017目录，新建的2017目录下用来更新的所有的股票的数据
	 *        2.如果2017目录存在，获取该路径下的所有文件的文件名，判断文件名的最大值，获取当前的日期，判断有多少天的数据缺失，然后生成缺失天数的所有的股票数据
	 */
	private String filePath;
	private int year;
	

	public updateData(String filePath) {
		Calendar now = Calendar.getInstance();  
		this.year=now.get(Calendar.YEAR);
		this.setFilePath(new StringBuffer(filePath).append(String.valueOf(this.year)).append("\\").toString());
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * 判断文件目录是否存在,如果不存在则进行新建目录
	 * @param filePath
	 * @return
	 */
	public static boolean isDirectoryExists(String filePath){
		File file=new File(filePath);
		if(file.exists()){
			if(file.isDirectory()){
				return true;
			}
			
		}else{
			System.out.println("File Directory not exists....");
			file.mkdir();
			System.out.println("File Directory created success!....");
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExists(String fileName){
		File file=new File(fileName);
		if(file.exists()){
			return true;
		}else{
			return false;
		}
		
	}
	
	 /**
	  * 获取当某个路径下所有的文件名
	  * @param path
	  * @return
	  */
	 public static String [] getFileName(String path)
	 {
	        File file = new File(path);
	        String [] fileName = file.list();
	        return fileName;
	 }
	 
	  /**
	  * 获取最大的文件名
	  * @param fileNames
	  * @return
	  */
	 public static int maxFileName(String [] fileNames){
		 int max=-1;
		 //如果某个目录下没有文件，则返回-1;
		 if(fileNames.length==0) return max;
		 
		 for(String str:fileNames){
			 int date=Integer.valueOf(str.substring(0, 8));
			 if(date>=max){
				 max=date;
			 }
		 }
		 return max;
	 }
	 
	 /**
	  * 产生需要写入到文件的数据
	  * @param date
	  * @param stockCodeList
	  * @return
	  */
	 public static Collection<String[]> generateUpdateDataToWrtie(int date,List<String>stockCodeList){
		 Collection<String[]> data = new ArrayList<String[]>();
		 //每一行有70列的数据
		 for(String element:stockCodeList){
			 String [] line=new String[70];
			 line[0]=element;
			 line[1]=String.valueOf(date);
			 for(int i=2;i<line.length;i++){
				 line[i]=generateDouble();
			 }
			 data.add(line);
		 }
		 return data;
	 }
	 
	    /**
		* 随机生成一个double的数字
		* @return
		*/
		public static String generateDouble(){
			Random random=new Random();
			double num=random.nextDouble();
			return String.valueOf(num);
		}
		
		
	  /**
	  * 更新数据到文件
	  * @param startDate
	  * @param endDate
	  */
	 public static void updateDataToFile(String filePath,int startDate,int endDate){
		 mockData.readStockCodeFromExcel(Context.STOCK_FILE_PATH);
		 for(int date=startDate;date<=endDate;date++){
			    StringBuffer sb=new StringBuffer();
				sb.append(filePath).append(date).append(".csv");
				File file=new File(sb.toString());
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			  Collection<String[]> data=generateUpdateDataToWrtie(date,mockData.stockCodeList);
			  CsvWriter csvWriter=new CsvWriter();
		        try {
					csvWriter.write(file, StandardCharsets.UTF_8, data);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
	 }
	 
	   /** 
	     * 获取某年第一天日期 
	     * @param year 年份 
	     * @return Date 
	     */  
	    public static Date getYearFirst(int year){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.clear();  
	        calendar.set(Calendar.YEAR, year);  
	        Date currYearFirst = calendar.getTime();  
	        return currYearFirst;  
	    } 
	    
	    /**
	    *更新数据
	    * @param filePath
	    */
	   public void startUpdateData(String filePath){
		   
		   if(isDirectoryExists(filePath)){
	    	Date d = new Date();  
	    	DateFormat df = new SimpleDateFormat("yyyyMMdd");  
	    	int date=Integer.valueOf(df.format(d));
	    	
            String [] fileNames=getFileName(filePath);
            int maxDate=maxFileName(fileNames);
           
            //表示该目录下没有文件,则从当前年份的第一天开始更新数据
            if(maxDate==-1) {
            	maxDate=Integer.valueOf(df.format(getYearFirst(year)));
            }
           
            //表示当前的数据已经是最新的数据了
   	        if(maxDate+1>date){
   	    	System.out.println("data is up to date....");
   	    	return ;
   	      }
   	        
   	      System.out.println("data is updating....");
   	      long start = System.currentTimeMillis();
   	      updateDataToFile(filePath, maxDate+1, date);
   	      long end = System.currentTimeMillis();
		  System.out.println("download  file time spend:"+(end - start));
   	     
	    }
		   
	   }
	    
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		updateData ud=new updateData(Context.FILE_DIRECTORY);
		ud.startUpdateData(ud.getFilePath());
		long end = System.currentTimeMillis();
		System.out.println("update  file time spend:"+(end - start));
		System.out.println("update data success!");
	}

	
    
}
