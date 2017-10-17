package com.mayforever.network.udp;

import java.net.InetSocketAddress;

public interface ServerListener {
	public void recievePacket(byte[] packetData,InetSocketAddress endpoint);
	public void errorDatagram(Exception e);
}
