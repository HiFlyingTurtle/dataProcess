package com.cms.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.cms.bean.Context;
import com.cms.writer.CsvWriter;
/**  
* 创建时间：2017年5月11日 下午4:13:40  
* @author wwh 
* @since JDK 1.8
* 文件名称：mockData.java  
* 用来模拟产生股票的数据
*/
public class mockData {
	public static List<String> stockCodeList=new ArrayList<>();
	
	public static String getValue(HSSFCell hssfCell){
		return  String.valueOf(hssfCell.getStringCellValue());
	}
	
	 /**
	 * 从excel读取股票代码信息
	 * @param filePath
	 */
	public static void readStockCodeFromExcel(final String filePath){
		File file=new File(filePath);
		InputStream is=null;
		try {
			if(!file.exists()){
				throw new Exception("file not found!");
			}
			is=new FileInputStream(file);
			HSSFWorkbook hssfWorkbook=new HSSFWorkbook(is);
			//读取excel文件的每一个sheet
			for(int numSheet=0;numSheet<hssfWorkbook.getNumberOfSheets();numSheet++){
				HSSFSheet hssfSheet=hssfWorkbook.getSheetAt(numSheet);
				if(hssfSheet==null){
					continue;
				}
				//读取每一个sheet的所有的行
				//hssfSheet.getLastRowNum()
				for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
					HSSFRow hssfRow=hssfSheet.getRow(rowNum);
					if(hssfRow==null)
						continue;
					else{
						if(hssfRow.getCell(0)==null)
							continue;
						HSSFCell TICKER_SYMBOL=hssfRow.getCell(2);
						HSSFCell EXCHANGE_CD=hssfRow.getCell(3);
						StringBuffer sb=new StringBuffer();
						sb.append(getValue(TICKER_SYMBOL)).append(".").append(getValue(EXCHANGE_CD));
			//			System.out.println(rowNum+":"+sb.toString());
						stockCodeList.add(sb.toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * 根据给定的年份产生每年的历史数据
	 * @param yearStart
	 * @param yearEnd
	 */
	public  void generateDataToFile(int yearStart,int yearEnd){
		for(int year=yearStart;year<=yearEnd;year++){
				StringBuffer sb=new StringBuffer();
				sb.append(Context.FILE_DIRECTORY).append(year).append(".csv");
				File file=new File(sb.toString());
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			    Calendar start = Calendar.getInstance();  
			    start.set(year, 0, 1);  
			    Long startTIme = start.getTimeInMillis();  
			    
			    Calendar end = Calendar.getInstance();  
			    end.set(year, 11, 29);  
			    Long endTime = end.getTimeInMillis();  	
			    
			    Long oneDay = 1000 * 60 * 60 * 24l;  
			    Long time = startTIme;
			    
			    
			    while (time <= endTime) {  
			        Date d = new Date(time);  
			        DateFormat df = new SimpleDateFormat("yyyyMMdd");  
			        String date=df.format(d);
//			        System.out.println(date);
			        Collection<String[]> data=generateDateToWrtie(date);
			        CsvWriter csvWriter=new CsvWriter();
			        try {
			        	//追加写入数据，写在尾部！
						csvWriter.write(file, StandardCharsets.UTF_8, data);
					} catch (IOException e) {
						e.printStackTrace();
					}
			        time += oneDay;  
		       }
			    
		}
		
	}
	 /**
	 * 用来产生写入每天的3462只股票的数据
	 * @return
	 */
	public static Collection<String[]>  generateDateToWrtie(String date){
		 Collection<String[]> data = new ArrayList<String[]>();
		 //每一行有70列的数据
		 for(String element:stockCodeList){
			 String [] line=new String[70];
			 line[0]=element;
			 line[1]=date;
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
	
	public static void main(String[] args) {
		/*	String filePath="D:\\stockData\\symbol.xls";
			readStockCodeFromExcel(filePath);
			System.out.println(stockCodeList.toString());*/
		    System.out.println("beginning.......");
		    long startTime=System.currentTimeMillis(); 
		    mockData md=new mockData();
		    String filePath="D:\\stockData\\symbol.xls";
		    readStockCodeFromExcel(filePath);
		    md.generateDataToFile(2005, 2016);
		    long endTime=System.currentTimeMillis();
		    long total=(endTime-startTime)/1000;
		    System.out.println("耗时："+total+"秒");
		    System.out.println("success!");
		    
	}

}
