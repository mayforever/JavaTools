package com.mayforever.network.newtcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

public class TCPClient {
	private AsynchronousSocketChannel socket_= null;
	private ClientListener listener_ = null;
	private ByteBuffer recbuf ;
	private ByteBuffer sendbuf ;
//	private boolean isRead ;
//	private CHForTcpClient chForTcpClient = null;
//	private SendHandler sendHandler = null;
//	private RecieveHandler recieveHandler = null;
	
	private int allocation = 2048;
	
	public TCPClient(AsynchronousSocketChannel socket){
		// constructing
		this.socket_ = socket;
		this.sendbuf = ByteBuffer.allocate(allocation);
		this.recbuf = ByteBuffer.allocate(allocation);
//		this.isRead = true;

//		this.chForTcpClient = new CHForTcpClient();
//		sendHandler = new SendHandler();
//		recieveHandler =new  RecieveHandler();
	}
	
	public TCPClient(String host,int port){
		// constructing by given host and port server
		SocketAddress serverAddr = new InetSocketAddress(host, port);
		
		try {
			// opening client by socket
			this.socket_ = AsynchronousSocketChannel.open();
			
			// connect it to server socket 
			this.socket_.connect(serverAddr).get();
			
			// allocate buffer to ByteBuffer
			this.sendbuf = ByteBuffer.allocate(allocation);
			this.recbuf = ByteBuffer.allocate(allocation);
			
			// setting the value so the client was ready to accept
//			this.isRead = true;
			
//			sendHandler = new SendHandler();
//			recieveHandler =new  RecieveHandler();
		} catch (Exception e) {
			this.listener_.socketError(e);
		}
		
	}
	
	public void addListener(ClientListener listener){
		// start listening to that socket
		// self made listener so it easily to handle
		this.listener_=listener;
		
		// the client was ready to read
		socket_.read(recbuf, socket_, new RecieveHandler());
	}
	
	public void sendPacket(byte[] data) throws IOException{
		
		// insert data to sendBuf
		sendbuf = ByteBuffer.wrap(data);
		
		// turn of read mode so the socket can send
//		this.isRead = false;
		
		try{
		
			// sending data in a socket
//			this.socket_.write(sendbuf,30, TimeUnit.MILLISECONDS, this.socket_, chForTcpClient);
			this.socket_.write(sendbuf, this.socket_, new SendHandler());
			
		}catch(Exception e){
			this.listener_.socketError(e);
		}
	}
	

	
	public void disconnect(){
		try {
			// disconnect client
			this.socket_.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.listener_.socketError(e);
		}
	}
	
	public void setAllocation(int alloc){
		// set the allocation
		this.allocation=alloc;
	}

	private class RecieveHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{

		public void completed(Integer result, AsynchronousSocketChannel attachment) {
			// TODO Auto-generated method stub
			if (result == -1){
				// for server disconnect
				try {
					throw new DisconnectClientError() ;
				} catch (DisconnectClientError e) {
					// TODO Auto-generated catch block
					listener_.socketError(e);
				}
				return;
			}
			if(result!=0){
				// i dont know the meaning why i need to flip
				// but i think it need
				recbuf.flip();
				
				// getting size of data
				int limits = recbuf.limit();
//				System.out.println(limits);
				// declare and initiate data recieve
				byte[] recievedData = new byte[limits];
				
				// filling recieved data by limits
				recbuf.get(recievedData, 0, limits);		
				
				// pass it to our listener
				listener_.packetData(recievedData);
				
				// re allocating recbuf
				recbuf = ByteBuffer.allocate(allocation);
				
				// ready to read again
				socket_.read(recbuf, socket_, new RecieveHandler());
			}
			
			
				
		}

		public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
			listener_.socketError((Exception)exc);
		}
		
		
	}

	
	private class SendHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{

		public void completed(Integer result, AsynchronousSocketChannel attachment) {
			// TODO Auto-generated method stub
			synchronized (attachment) {
				
			
				if (result == -1){
					// for server disconnect
					try {
						throw new DisconnectClientError() ;
					} catch (DisconnectClientError e) {
						// TODO Auto-generated catch block
						listener_.socketError(e);
					}
					return;
				}
				
				if(result!=0){
						// i dont know the meaning why i need to flip
						// but i think it need
						sendbuf = ByteBuffer.allocate(allocation);
						
						// setting ready to read
	//					isRead = true;
				}
//			System.out.println("data send:"+result);
			}
				
		}

		public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
			listener_.socketError((Exception)exc);
		}
		
		
	}
	private class DisconnectClientError extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private DisconnectClientError() {
			try {
				socket_.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				listener_.socketError(e);
			}
		}
	}
}
