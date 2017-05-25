package com.cms.test;
/**  
* 创建时间：2017年5月15日 上午10:04:31  
* @author wwh 
* @since JDK 1.8
* 文件名称：Line.java  
*/
public class Line {
    public int i = 0;
    
	public synchronized void add(){
		i++;
	}

}
