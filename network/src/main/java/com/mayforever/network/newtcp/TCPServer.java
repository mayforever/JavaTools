package com.mayforever.network.newtcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class TCPServer {
	private ServerListener listener_=null;
	private AsynchronousServerSocketChannel asynchronousServerSocketChannel=null;
	private int port_=0;
	private String host_="";
	private CHForTCPServer chForTcpServer = null;
	
	public TCPServer(int port, String host){
		// kids stop constructing
		this.port_=port;
		if(host != null){
			this.host_=host;
		}else{
			this.host_ = "127.0.0.1";
		}
		chForTcpServer = new CHForTCPServer();
	}

	public void addListener(ServerListener listener){
		
		if (listener != null){
			// server listener initiating
			// this listener is an self create interface
			this.listener_=listener;
			
			try {
			
				// initializing asynchronous server
				asynchronousServerSocketChannel = AsynchronousServerSocketChannel
				        .open();
				
				// declare and initialize inetSocketAddress of the server
				InetSocketAddress sAddr = new InetSocketAddress(this.host_, this.port_);
				
				// bind the inetSocketAddress
				asynchronousServerSocketChannel.bind(sAddr);

				// ready the server to accept
				asynchronousServerSocketChannel.accept(this.asynchronousServerSocketChannel, chForTcpServer);
		
			} catch (IOException e1) {
				this.listener_.socketError(e1);
			}
		}else{
			
			// throws null pointer exception for listener
			this.listener_.socketError(new NullPointerException());
		}	
	}
	public void stopListenning(){
		try {
			
			// closing socket
			this.asynchronousServerSocketChannel.close();
		
		} catch (Exception e) {
			this.listener_.socketError(e);
		}
	}
	
	// Hide the completed/failed
	private class CHForTCPServer implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>{
		public  void completed(AsynchronousSocketChannel client, AsynchronousServerSocketChannel serverSocket) {
			
			// initializing client socket
			listener_.acceptSocket(client);
			
			// ready the server to accept again
			serverSocket.accept(serverSocket, this);
			
		}

		public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
			listener_.socketError((Exception)exc);
		}
	}
}
