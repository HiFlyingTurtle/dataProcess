package com.cms.test;
/**  
* 创建时间：2017年5月12日 下午4:17:27  
* @author wwh 
* @since JDK 1.8
* 文件名称：ArgsTest.java  
*/
public class ArgsTest {
	public static void main(String[] args) {
		if(args.length==0){
			System.out.println("该方法没有指定任何参数！");
			return ;
		}
		System.out.println("您调用main方法时指定的参数包括：");  
        for (int i = 0; i < args.length; i++) {  
            System.out.println("参数" + (i + 1) + "的值为：" + args[i]);  
        }  
        
		
	}

}
