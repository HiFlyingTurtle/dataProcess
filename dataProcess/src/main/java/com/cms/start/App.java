package com.cms.start;
import java.util.Scanner;
import com.cms.bean.Context;
import com.cms.service.queryData;
import com.cms.service.updateData;
/**  
* 创建时间：2017年5月18日 下午3:43:51  
* @author wwh 
* @since JDK 1.8
* 文件名称：App.java  
* 整个程序的主要入口.....
*/
public class App {
	public static void main(String[] args) {
		
		System.out.println("----Welcome to use System----");
		System.out.println("----Please input a number----:"
				+"\n"+ "(1): Number 0 to exit sysytem."
				+"\n"+ "(2): Number 1 to execute update operation."
				+"\n"+ "(3): Number 2 to execute query operation.");
		
	    Scanner sc=new Scanner(System.in);
	    while(sc.hasNext()){
	    	int chioce=sc.nextInt();
	    	switch (chioce) {
	    	case 0:
	    	   System.out.println("exit system success!");
	    	   System.exit(0);
	    	   break;
			case 1:
				//用来进行增量数据的更新操作
				updateData ud=new updateData(Context.FILE_DIRECTORY);
				ud.startUpdateData(ud.getFilePath());
				System.out.println("update data success!");
				break;
			case 2:
				System.out.println("----please choose query way----"+"\n"+
						"(1): Number 1 query by stockCode and date."+"\n"+
						"(2): Number 2 query by stockCode and date section.");
				int query=sc.nextInt();
					if(query==1){
						System.out.println("please input stock code and query date,use blank space as delimiter");
						String stockCode=sc.next();
						String date=sc.next();
//						System.out.println(stockCode+"--"+date);
						System.out.println("---query begain----");
						long start = System.currentTimeMillis();
						queryData qd=new queryData();
						System.out.println("query result:");
						qd.printQueryData(qd.queryByStockCodeAndDate(stockCode, date));
						long end = System.currentTimeMillis();
						System.out.println("query time spend:"+(end - start));
						System.out.println("query end....");
					}else if(query==2){
						System.out.println("please input stock code and query date section,use blank space as delimiter");
						String stockCode=sc.next();
						String startDate=sc.next();
						String endDate=sc.next();
						System.out.println("---query begain----");
						long start = System.currentTimeMillis();
						queryData qd=new queryData();
						System.out.println("query result:");
						qd.printQueryData(qd.queryByStockCodeAndDateSection(stockCode, startDate, endDate));
						long end = System.currentTimeMillis();
						System.out.println("query time spend:"+(end - start));
						System.out.println("query end....");
					}
				break;
			default:
				System.out.println("input error,please input again!");
				break;
			}
	    }
	    
	    sc.close();
	}

}
