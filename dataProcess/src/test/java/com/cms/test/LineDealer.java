package com.cms.test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
/**  
* 创建时间：2017年5月15日 上午10:04:48  
* @author wwh 
* @since JDK 1.8
* 文件名称：LineDealer.java  
*/
public class LineDealer  implements Runnable{
    private final BlockingQueue<String> sharedQueue;
	private final Line line;
	
	
	public LineDealer(BlockingQueue<String> sharedQueue, Line line){
		this.sharedQueue = sharedQueue;
		this.line = line;
	}
	
	public void deal() throws Exception{
		System.out.println("line deal Begainning....");
		long start = System.currentTimeMillis();
//		Map<String,String> result=new HashMap<String,String>();
		List<String> result=new ArrayList<>();
		//000423.XSHE 20060501
		String query="000423.XSHE";
//		int count=0;
		while(line.i == 0 || !sharedQueue.isEmpty()){
		//	System.out.println("deal sharedQueus:"+sharedQueue.size());
			String first=sharedQueue.take();
			//if(first.)
			if(first.startsWith(query)){
				result.add(first);
			}
			System.out.println("processing......");
//			count++;
//			System.out.println("first line data in file:"+first);
//			sharedQueue.clear();
		}
//		System.out.println("deal count:"+count);
		long end = System.currentTimeMillis();
		System.out.println("deal time: " + (end - start));
		System.out.println("line deal Ending....");
		
		System.out.println("Print result data to Screen.....");
		System.out.println(result.size());
		System.out.println(result.get(0));
		/*for(String ele:result){
			System.out.println(ele);
		}*/
	}
	
	@Override
	public void run() {
		try {
			deal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
