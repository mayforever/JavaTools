package com.mayforever.network.newtcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class TCPClient implements CompletionHandler<Integer, AsynchronousSocketChannel>{
	private AsynchronousSocketChannel socket_= null;
	private ClientListener listener_ = null;
	private ByteBuffer recbuf ;
	private ByteBuffer sendbuf ;
	private boolean isRead ;
	private int alloc_= 2048;
	
	public TCPClient(AsynchronousSocketChannel socket){
		this.socket_=socket;
		this.sendbuf = ByteBuffer.allocate(alloc_);
		this.recbuf = ByteBuffer.allocate(alloc_);
		this.isRead = true;
		// lock = new Object();
	}
	
	public TCPClient(String Ip,int port){
		// this.socket_=socket;
		SocketAddress serverAddr = new InetSocketAddress(Ip, port);
		
		try {
			this.socket_ = AsynchronousSocketChannel.open();
			this.socket_.connect(serverAddr).get();
			this.sendbuf = ByteBuffer.allocate(alloc_);
			this.recbuf = ByteBuffer.allocate(alloc_);
			this.isRead = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			this.listener_.socketError(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			this.listener_.socketError(e);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			this.listener_.socketError(e);
		}
	}
	
	public void addListener(ClientListener listener){
		this.listener_=listener;
		try {
			socket_.read(recbuf, socket_, this);
		}catch(Exception e) {
			this.listener_.socketError(e);
		}
		
		// threadClient.start();
	}
	public void sendPacket(byte[] data) throws IOException{
		// socket_.shutdownInput();
		sendbuf = ByteBuffer.wrap(data);
		this.isRead = false;
		try{
			this.socket_.write(sendbuf, this.socket_, this);
		}catch(Exception e){
			this.listener_.socketError(e);
		}
		// this.threadClient.start();
	}
	
	public void completed(Integer result, AsynchronousSocketChannel attachment) {
		// TODO Auto-generated method stub
		try {
			if(isRead){
				if(result!=0){
					recbuf.flip();
					int limits = recbuf.limit();
					byte[] recievedData = new byte[limits];
					// System.arraycopy(buf.array(), 0, recievedData, 0, result);
					recbuf.get(recievedData, 0, limits);
					this.listener_.packetData(recievedData);
					// System.out.println(limits);
					// this.isRead = false;
					
					this.recbuf = ByteBuffer.allocate(alloc_);
					socket_.read(recbuf, socket_, this);
					
				}
			}else{
				// buf.clear();
				// attachment.shutdownInput();
				this.sendbuf = ByteBuffer.allocate(alloc_);
				this.isRead = true;
				
				// socket_.read(buf, socket_, this);
			}
		}catch(Exception e) {
			this.listener_.socketError(e);
		}
			
		
		
	}

	public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
		listener_.socketError((Exception)exc);
	}
	
	public void setAllocationPerBytes(int alloc){
		this.alloc_ = alloc;
	}
}
