package com.mayforever.network.newtcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class TCPServer implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>{
	private ServerListener listener_=null;
	private AsynchronousServerSocketChannel asynchronousServerSocketChannel=null;
	private int port_=0;
	private String host_="";
	
	public TCPServer(int port, String host){
		this.port_=port;
		this.host_=host;
		
	}
	
	public void addListener(ServerListener listener){
		this.listener_=listener;
		try {
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel
			        .open();
			InetSocketAddress sAddr = new InetSocketAddress(this.host_, this.port_);
			asynchronousServerSocketChannel.bind(sAddr);
			// System.out.format("Server is listening at %s%n", sAddr);
		} catch (IOException e1) {
			this.listener_.socketError(e1);
		}
	
		asynchronousServerSocketChannel.accept(this.asynchronousServerSocketChannel, this);
	}
	
	public void stopListenning(){
		try {
			this.asynchronousServerSocketChannel.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// ea.printStackTrace();
			this.listener_.socketError(e);
		}
	}

	public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
		// TODO Auto-generated method stub
		listener_.acceptSocket(result);
		attachment.accept(attachment, this);
		// System.out.println("Socket Connected");
	}

	public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
		// TODO Auto-generated method stub
		// System.out.println("Socket Failed");
		this.listener_.socketError((Exception)exc);
	}
}
