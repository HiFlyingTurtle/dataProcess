package com.cms.service;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cms.bean.Context;
/**  
* 创建时间：2017年5月12日 上午10:53:04  
* @author wwh 
* @since JDK 1.8
* 文件名称：queryData.java  
* 用来查询大文件的数据，按照指定的借口来进行查询;
*/
public class queryData {
	
	/**
	 * key：文件名 
	 * map：文件路径
	 * 实现一个简单的索引，根据日期段可以快速定位到文件的path进行文件的读取
	 */
	 private static final Map<String, String> map;
	 static{
		 map=new HashMap<String, String>();
		 for(int i=2005;i<=2016;i++){
			 //"D:\\stockData\\"
			 map.put(String.valueOf(i), new StringBuffer(Context.FILE_DIRECTORY).append(String.valueOf(i))+".csv");
		 }
	 }
	 
	 
	/**
	 * 根据指定的日期和股票代码查询股票的信息
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public List<String> queryByStockCodeAndDate(String stockCode,String date){
		List<String> result=new ArrayList<>();
		
		if(stockCode==null||date==null||stockCode.equals("")||date.equals("")){
			try {
				throw new Exception("query data is null!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fileName=map.get(date.substring(0,4));
//		System.out.println("fileName:"+fileName);
		String query=new StringBuffer(stockCode).append(",").append(date).toString();
//		System.out.println("query:"+query);
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis),  16* 1024 * 1024);
//			int count=0;
			while(in.ready()){   
//				count++;
				String line = in.readLine();  
				if(line.startsWith(query)){
					result.add(line);
				}
			}
//			System.out.println("line sum"+count);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 根据股票代码以及时间段查询具体的股票的信息
	 * @param stockCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> queryByStockCodeAndDateSection(String stockCode,String startDate,String endDate){
		if(stockCode==null||startDate==null||stockCode.equals("")||startDate.equals("")||endDate==null||endDate.equals("")){
			try {
				throw new Exception("query data is null!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> result=new ArrayList<>();
	    
		int startYear=Integer.valueOf(startDate.substring(0,4));
		int endYear=Integer.valueOf(endDate.substring(0,4));
		
		System.out.println("startYear:"+startYear+"endYear:"+endYear);
		
		for(int year=startYear;year<=endYear;year++){
			String fileName=map.get(String.valueOf(year));
			System.out.println("fileName:"+fileName);
			
			try {
				
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
				BufferedReader in = new BufferedReader(new InputStreamReader(bis),  512* 1024 * 1024);
				
				while(in.ready()){   
					String line = in.readLine();  
					if(line.startsWith(stockCode)){
						String date=line.substring(12, 20);
						if(IsInDate(date, startDate, endDate)){
							result.add(line);
						}
					}
				}
				
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 判断日期是否在某个时间段之内
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean IsInDate(String date,String startDate,String endDate){
		int iDate=Integer.valueOf(date);
		int istarDate=Integer.valueOf(startDate);
		int iendDate=Integer.valueOf(endDate);
		if(iDate>=istarDate&&iDate<=iendDate){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 输出查询到的股票的信息
	 * @param result
	*/
	public  void printQueryData(List<String> result){
		System.out.println("total number of query data: "+result.size());
		for(String el:result){;
			System.out.println(el);
		}
	}
	
	/**
	 * 将查询的结果写入到文件中,追加写入
	 * @param result
	*/
	public  void writeQueryDataToFile(List<String> result){
		FileWriter fw;
		try {
			//"D:\\out.txt"
			fw = new FileWriter(Context.QUERY_RESULT_FILE,true);
			fw.write("Date:"+new Date()+" query result data:"+"\r\n");
			for(String line : result){
				fw.append(line + "\r\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("query start....");
		long start = System.currentTimeMillis();
		queryData qd=new queryData();
//		List<String> result=qd.queryByStockCodeAndDate("000423.XSHE", "20060801");
		List<String> result=qd.queryByStockCodeAndDateSection("000423.XSHE", "20060501", "20060609");
		long end = System.currentTimeMillis();
		System.out.println("read  file time spend:"+(end - start));
		System.out.println("query end....");
		
//		qd.printQueryData(result);
		qd.writeQueryDataToFile(result);
		
	}

	
}
