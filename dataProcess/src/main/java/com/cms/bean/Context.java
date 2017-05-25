package com.cms.bean;
/**  
* 创建时间：2017年5月22日 下午3:28:34  
* @author wwh 
* @since JDK 1.8
* 文件名称：Context.java  
* 用来存放程序运行所需要的文件路径以及相关的一些常量的信息
*/
public class Context {
	
	//默认的数据文件存放路径
	public final static String FILE_DIRECTORY="D:\\stockData\\";
	
	//全A股票的信息
	public final static String STOCK_FILE_PATH="D:\\stockData\\symbol.xls";
	
	//查询结果写入到的文件名
	public final static String QUERY_RESULT_FILE="D:\\out.txt";

}
