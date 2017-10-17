package com.mayforever.network.newtcp;

import java.nio.channels.AsynchronousSocketChannel;

public class TCPSampleServer implements com.mayforever.network.newtcp.ServerListener{
	
	public TCPServer tcpServer = null;
	public TCPSampleServer(){
		tcpServer = new TCPServer(13500, "localhost");
		tcpServer.addListener(this);
		
		while(true){
			
			try {
				java.lang.Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] arg){
		new TCPSampleServer();
	}
	public void acceptSocket(AsynchronousSocketChannel socket) {
		// TODO Auto-generated method stub
		// TCPClient tcpClient= new TCPClient(socket);
		new TCPSampleClient(socket);
	}
	public void socketError(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
