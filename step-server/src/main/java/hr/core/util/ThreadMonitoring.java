package hr.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadMonitoring extends Thread{
	 private static final Object locker=new Object();
	 
	private static final Logger logger = LoggerFactory.getLogger(ThreadMonitoring.class.getName());
	
	private Date runningStart;
	private static Map<String,Integer> currentAcitveThreads=new HashMap<String,Integer>();
	public ThreadMonitoring() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public ThreadMonitoring(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}
	
	private Integer updateCount(String name,int value){
		synchronized(locker){
			Integer currentAcitveThread= currentAcitveThreads.get(name);
			if(currentAcitveThread==null){
				currentAcitveThread=0;
			}
			currentAcitveThread+=value;
			currentAcitveThreads.put(name, currentAcitveThread);
			return currentAcitveThread;
		}
	}

	@Override
	public void run() {
		runningStart=new Date();
		logger.info("--------------------线程数       :"+updateCount(this.getName(),1));
		logger.info("--------------------所有活动线程数:"+updateCount("countThread",1));
		super.run();
		long runtime=(new Date().getTime()-runningStart.getTime());
		logger.info("--------------------线程数      :"+updateCount(this.getName(),-1) +" 用时："+runtime+"ms");
		logger.info("--------------------所有活动线程数:"+updateCount("countThread",-1));
	}

}
