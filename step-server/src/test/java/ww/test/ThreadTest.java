package ww.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.junit.Test;

import hr.core.util.ThreadMonitoring;
import junit.framework.TestCase;

public class ThreadTest extends TestCase {

	public void test1() throws Exception {
		ExecutorService threadPool=Executors.newFixedThreadPool(20, new ThreadFactory()
		  {
			   private int i=0;
			   public Thread newThread(Runnable r)
			   {
				   return new ThreadMonitoring(r, "----------ThreadTest"+i++);
			   }
		 });
		
		 for(int i=0;i<10;i++){
			 threadPool.execute(new TestRunnable());
		 }
		 Thread.sleep(600000);
		 System.out.println("--");
		 
		 
	}
}

class TestRunnable implements Runnable { 
	private static int t;
    public void run() { 
            System.out.println(Thread.currentThread().getName() + "线程被调用了。"); 
            t++;
            while (true) { 
                    try { 
                    	  long r=(long)(Math.random()*1000*60.0);
                    	  System.out.println("--:"+r);
                          Thread.sleep(r); 
                          break;
                    } catch (InterruptedException e) { 
                            e.printStackTrace(); 
                    } 
            } 
            t--;
            System.out.println(Thread.currentThread().getName() + "线程结束了 "+t); 
    } 
}
