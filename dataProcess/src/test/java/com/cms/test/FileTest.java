package com.cms.test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;

import com.cms.writer.CsvWriter;

public class FileTest {
	public static void main(String[] args) {
		//存储文件数据的路径
		String filePath="D:\\stockData\\";
		//文件名,按照年份来进行命名
		String fileName="2016.csv";
		
		File file=new File(filePath+fileName);
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		CsvWriter csvWriter=new CsvWriter();
		final Collection<String[]> data = new ArrayList<String[]>();
		//构造一个String 数组用来存储70列的字段的信息
	    String [] strArray=new String[70];
	    
	    strArray[0]=readFromExcel();
	    strArray[1]="20160101";
	    
		for(int i=2;i<strArray.length;i++){
			strArray[i]=String.valueOf(generateDouble());
		}
		
		//第一列是股票代码需要从excel文件中读取然后拼接而成
		
 		data.add(new String[] { "StockCode", "dateTime" });
		data.add(new String[] { "00001.XSHE", "20160101","0.256984555" });
		data.add(new String[] { "00002.XSHE", "20160101","0.235555555" });
		try {
			csvWriter.write(file, StandardCharsets.UTF_8, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       System.out.println(file.getAbsolutePath());
	   System.out.println("success!");
		
		
	}
	
	public static String  readFromExcel(){
		//从文件中读取指定的文件的数据 一次性将所有的股票代码读入到内存中 不需要每次都去读文件
		return null;
	}
	/**
	 *测试文件的写入
	 */
    @Test
	public  void writeFile(){
		File file=new File("text.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		char [] buf={'a','b','c'};
		FileWriter fw = null;
		try {
			fw = new FileWriter(file,true);
			fw.write(buf,0,buf.length);
			fw.write("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	   try {
		fw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}
	 /**
	 * 随机生成一个double的数字
	 * @return
	 */
	public static double generateDouble(){
		Random random=new Random(10);
		double num=random.nextDouble();
		return num;
	}
	
	@Test
	public void charTest(){
		/*String line="000008.XSHE,20150104,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272,;0.7304302967434272,0.7304302967434272,0.7304302967434272,0.7304302967434272";
		String query="000008.XSHE,20150104";
		System.out.println(line.substring(12, 20));*/
		
		String s1="20050104";
		String s2="20185704";
		String s3="20070506";
		System.out.println(Integer.valueOf(s1)>=Integer.valueOf(s2));
		System.out.println(Integer.valueOf(s2)>=Integer.valueOf(s3));
	
	/*	System.out.println(line.startsWith(query));
		System.out.println(query.substring(0, 4));
		String startDate="20150104";
		int startYear=Integer.valueOf(startDate.substring(0,4));
		int startMonth=Integer.valueOf(startDate.substring(4,6));
		int startDay=Integer.valueOf(startDate.substring(6));
		System.out.println(startYear+":"+startMonth+":"+startDay);*/
	}

}
