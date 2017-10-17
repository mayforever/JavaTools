package com.mayforever.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import com.mayforever.network.udp.ServerListener;

public class UDPServer implements Runnable{
	private DatagramSocket serverSocket = null;
	private ServerListener listener_ = null;
	private Thread thread=null;
	
	public UDPServer(int port){
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			this.listener_.errorDatagram(e);
		}
		thread=new Thread(this);
	}
	
	public void addListener(ServerListener listener){
		this.listener_=listener;
		this.thread.start();
	}
	
	public void sendPacket(byte[] packetData,InetSocketAddress endpoint) throws IOException{
		DatagramPacket sendPacket=new DatagramPacket(packetData,packetData.length,endpoint.getAddress(),endpoint.getPort());
		serverSocket.send(sendPacket);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(this.listener_!=null){
				
				byte[] receiveData=new byte[1024];
				DatagramPacket recievePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					serverSocket.receive(recievePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					this.listener_.errorDatagram(e);
				}
				InetSocketAddress endpoint=new InetSocketAddress(recievePacket.getAddress(), recievePacket.getPort());
				byte[] tData=new byte[recievePacket.getLength()];
				byte[] dataRecieve=recievePacket.getData();
				System.arraycopy(dataRecieve, 0, tData, 0, tData.length);
				this.listener_.recievePacket(tData, endpoint);
			}
		}
	}
}
