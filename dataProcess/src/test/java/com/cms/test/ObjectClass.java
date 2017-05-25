package com.cms.test;
/**  
* 创建时间：2017年5月24日 上午10:55:53  
* @author wwh 
* @since JDK 1.8
* 文件名称：ObjectClass.java  
*/
public class ObjectClass {
	public static void main(String[] args) {
		 int i=0;
         try {
			i=2+6;
			return ;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			System.out.println(i);
		}
	}

}
