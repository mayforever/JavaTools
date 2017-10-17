package com.mayforever.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPClientSWR {
	DatagramSocket clientSocket = null;
	public UDPClientSWR(){
		// DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] sendPacket(byte[] data,InetSocketAddress inetSocketAddress, int waitMilli,int tries){
		DatagramPacket sendPacket=new DatagramPacket(data,data.length,inetSocketAddress.getAddress(),inetSocketAddress.getPort());
		byte[] tData = null;
		try {
			clientSocket.send(sendPacket);
			clientSocket.setSoTimeout((int) waitMilli);
			
			for(int i=0;i<tries;i++){
				 try {
					 	clientSocket.receive(sendPacket);
					 	tData=new byte[sendPacket.getLength()];
					 	byte[] dataRecieve=sendPacket.getData();
						System.arraycopy(dataRecieve, 0, tData, 0, tData.length);
						// this.listener_.recievePacket(tData, endpoint);
					 	break;
		            }
		            catch (SocketTimeoutException e) {
		                // timeout exception.
		                // System.out.println("Timeout reached!!! Resending " + e);
		               // clientSocket.close();
		            }
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return tData;
	}
}
