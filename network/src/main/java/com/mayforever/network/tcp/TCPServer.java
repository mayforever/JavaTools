package com.mayforever.network.tcp;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer implements Runnable{
	private ServerListener listener_=null;
	private ServerSocket serverSocket=null;
	private int port_=0;
	private Thread thread=null;
	
	public TCPServer(int port){
		this.port_=port;
		thread=new Thread(this);
	}
	
	public void addListener(ServerListener listener){
		this.listener_=listener;
		thread.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			serverSocket=new ServerSocket(port_);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener_.socketError(e);
		}
		
		while(true){
			try {
				this.listener_.acceptSocket(serverSocket.accept());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				listener_.socketError(e1);
			}
		}
	}
}
