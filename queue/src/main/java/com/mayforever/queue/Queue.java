package com.mayforever.queue;

public class Queue<T> {
	private Object lock=new Object();
	private java.util.Queue<T> queue=new java.util.ArrayDeque<T>();
	private long waitMilli_=0;
	
	public Queue(){
		this.waitMilli_=5000;
	}

	public Queue(long waitMilli){
		if (waitMilli <= 0){
			this.waitMilli_=5000;
		}else{
			this.waitMilli_=waitMilli;
		}			
	}
	
	public void add(T data){
		synchronized (lock){
			queue.add(data);
			lock.notifyAll();
		}
	}
	
	public T get() throws InterruptedException{
		T t=null;
		synchronized (lock){
			t=queue.poll();
			if (t!=null){
				// return t;
			}else{
				if(waitMilli_!=0){
					lock.wait(waitMilli_);;
				}
			}
		}
		return t;
	}
}
