package com.cms.test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**  
* 创建时间：2017年5月11日 下午3:24:14  
* @author wwh 
* @since JDK 1.8
* 文件名称：dateTest.java  
*/
public class dateTest {
	//月份从0开始
	public static void main(String[] args) {
	/*	  int yearStart=2005;
		  int yearEnd=2016;
		  for(int i=yearStart;i<=yearEnd;i++){
			  //每年新建一个文件夹       
			  //向文件中写入这一年的数据
			  //遍历该年的每一天 写入数据
			  //每天写入的数据是3462只股票的数据信息，以及每只股票的数据信息有70个字段核心字段是股票代码和日期
			  //股票代码需要先从文件读到内存.然后编年每年每天的时候，写入每只股票的数据
		  }*/
		 Calendar start = Calendar.getInstance();  
		    start.set(2014, 0, 11);  
		    Long startTIme = start.getTimeInMillis();  
		  
		    Calendar end = Calendar.getInstance();  
		    end.set(2014, 0, 30);  
		    Long endTime = end.getTimeInMillis();  	
		  
		    Long oneDay = 1000 * 60 * 60 * 24l;  
		  
		    Long time = startTIme;  
		    while (time <= endTime) {  
		        Date d = new Date(time);  
		        DateFormat df = new SimpleDateFormat("yyyyMMdd");  
		        System.out.println(df.format(d));  
		        time += oneDay;  
	}

}
}
