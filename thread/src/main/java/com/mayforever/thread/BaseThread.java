package com.mayforever.thread;

public abstract class BaseThread implements Runnable{
	private java.lang.Thread thread = null;
	private int serviceState = com.mayforever.thread.state.ServiceState.STOP;
	
	protected BaseThread(){
		this.thread = new java.lang.Thread(this);
	}
	
	protected BaseThread(String name){
		this.thread = name != null ? new java.lang.Thread(this, name) :  new java.lang.Thread(this);
		
	}

	protected java.lang.Thread getThread() {
		return thread;
	}
	
	
	public int getServiceState() {
		return serviceState;
	}

	protected void startThread(){
		this.serviceState = com.mayforever.thread.state.ServiceState.RUNNING;
		this.thread.start();
	}
	
	protected void stopThread(){
		this.serviceState = com.mayforever.thread.state.ServiceState.STOP;
	}
}
