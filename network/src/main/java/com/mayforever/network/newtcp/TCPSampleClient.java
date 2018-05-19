package com.mayforever.network.newtcp;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Arrays;

public class TCPSampleClient implements com.mayforever.network.newtcp.ClientListener{
	
	// private AsynchronousSocketChannel socket = null;
	private com.mayforever.network.newtcp.TCPClient tcpClient = null;
	public TCPSampleClient(AsynchronousSocketChannel socket){
		// this.socket = socket;
		tcpClient = new com.mayforever.network.newtcp.TCPClient(socket);
		tcpClient.addListener(this);
	}
	
	public void packetData(byte[] data) {
		// TODO Auto-generated method stub
		System.out.println(Arrays.toString(data));
		try {
			tcpClient.sendPacket(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void socketError(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
