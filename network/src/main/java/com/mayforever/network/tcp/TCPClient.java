package com.mayforever.network.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPClient implements Runnable{
	private Socket socket_=null;
	private ClientListener listener_=null;
	private Thread threadClient=null;
	public TCPClient(Socket socket){
		this.socket_=socket;
		threadClient=new Thread(this);
	}
	
	public void addListener(ClientListener listener){
		this.listener_=listener;
		threadClient.start();
	}
	public void sendPacket(byte[] data) throws IOException{
		DataOutputStream outToClient = null;
		try {
			outToClient = new DataOutputStream(socket_.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outToClient.write(data);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader inFromServer=null;
		try {
			inFromServer =
			           new BufferedReader(new InputStreamReader(socket_.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Byte> list = new ArrayList<Byte>();
		while(true){
			try {
				int b=inFromServer.read();
				if (b==-1){
					this.listener_.socketDisconnect();
					break;
				}
				list.add((byte)b);
				while(!inFromServer.ready()){
					if(list.size()!=0){
						byte[] byteArray=new byte[list.size()];
						for(int i=0;i<list.size();i++){
							byteArray[i]=list.get(i);
						}
						this.listener_.packetData(byteArray);
						list.clear();
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.listener_.socketDisconnect();
				e.printStackTrace();
				break;
			}
		}
	}
}
