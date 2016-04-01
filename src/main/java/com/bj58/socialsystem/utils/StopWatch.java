package com.bj58.socialsystem.utils;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月23日
 */
public class StopWatch {
	
	private final String id;
	
	private boolean keepTaskList = true;
	
	private final List<TaskInfo> taskList = new LinkedList<TaskInfo>();
	
	private long startTimeMillis;
	
	private boolean running;
	
	private String currentTaskName;
	
	private TaskInfo lastTaskInfo;
	
	private int taskCount;
	
	private long totalTimeMillis;
	
	public StopWatch() {
		this.id = "";
	}
	
	public StopWatch(String id) {
		this.id = id;
	}
	
	public void setKeepTaskList(boolean keepTaskList) {
		this.keepTaskList = keepTaskList;
	}
	
	public void start() {
		start("");
	}
	
	public void start(String taskName) {
		if(this.running) {
			throw new IllegalStateException("Can't start StopWatch: it's already running");
		}
		this.startTimeMillis = System.currentTimeMillis();
		this.running = true;
		this.currentTaskName = taskName;
	}
	
	public void stop() {
		if(!this.running) {
			throw new IllegalStateException("Can't stop StopWatch: it's not running"); 
		}
		
		long lastTime = System.currentTimeMillis() - this.startTimeMillis;
		this.totalTimeMillis += lastTime;
		this.lastTaskInfo = new TaskInfo(this.currentTaskName, lastTime);
		if(this.keepTaskList) {
			this.taskList.add(lastTaskInfo);
		}
		++this.taskCount;
		this.running = false;
		this.currentTaskName = null;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public long getLastTaskTimeMillis() {
		if(this.lastTaskInfo == null) {
			throw new IllegalStateException("No tasks run: can't get last task interval"); 
		}
		return this.lastTaskInfo.getTimeMillis();
	}
	
	public String getLastTaskName() {
		if(this.lastTaskInfo == null) {
			 throw new IllegalStateException("No tasks run: can't get last task name");  
		}
		return this.lastTaskInfo.getTaskName();
	}
	
	public TaskInfo getLastTaskInfo() {
		if(this.lastTaskInfo == null) {
			 throw new IllegalStateException("No tasks run: can't get last task info");
		}
		return this.lastTaskInfo;
	}
	
	public long getTotalTimeMillis() {
		return this.totalTimeMillis;
	}
	
	public double getTotalTimeSeconds() {
		return this.totalTimeMillis / 1000.0;
	}
	
	public int getTaskCount() {
		return this.taskCount;
	}
	
	public TaskInfo[] getTaskInfo() {
		if(!this.keepTaskList) {
			throw new UnsupportedOperationException("Task info is not being kept!");
		}
		return this.taskList.toArray(new TaskInfo[this.taskList.size()]);
	}
	
	public String shortSummary() {
		return "StopWatch '" + this.id + "': running time = " + getTotalTimeMillis() + "ms";
	}
	
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder(shortSummary());
		sb.append("\n");
		if(!this.keepTaskList) {
			sb.append("No task info kept");
		} else {
			sb.append("-----------------------------------------\n");  
            sb.append("ms\t%\tTask name\n");  
            sb.append("-----------------------------------------\n");  
            NumberFormat nf = NumberFormat.getNumberInstance();  
            nf.setMinimumIntegerDigits(5);  
            nf.setGroupingUsed(false);  
            NumberFormat pf = NumberFormat.getPercentInstance();  
            pf.setMinimumIntegerDigits(2);  
            pf.setGroupingUsed(false);  
            for (TaskInfo task : getTaskInfo()) {  
                sb.append(task.getTimeMillis()).append("\t");  
                sb.append(pf.format(task.getTimeSeconds() / getTotalTimeSeconds())).append("\t");  
                sb.append(task.getTaskName()).append("\n");  
            }  
            sb.append("-----------------------------------------"); 
		}
		
		return sb.toString();
	}
	
	@Override  
    public String toString() {  
        StringBuilder sb = new StringBuilder(shortSummary());  
        if (this.keepTaskList) {  
            for (TaskInfo task : getTaskInfo()) {  
                sb.append("; [").append(task.getTaskName()).append("] took ").append(task.getTimeMillis());  
                long percent = Math.round((100.0 * task.getTimeSeconds()) / getTotalTimeSeconds());  
                sb.append(" = ").append(percent).append("%");  
            }  
        } else {  
            sb.append("; no task info kept");  
        }  
        return sb.toString();  
    }  
	
	public static final class TaskInfo {
		private final String taskName;
		
		private final long timeMillis;
		
		TaskInfo(String taskName, long timeMillis) {
			this.taskName = taskName;
			this.timeMillis = timeMillis;
		}

		public String getTaskName() {
			return taskName;
		}

		public long getTimeMillis() {
			return timeMillis;
		}
		
		public double getTimeSeconds() {
			return this.timeMillis / 1000.0;
		}
	}
}
