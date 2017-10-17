package com.mayforever.network.tcp;

public interface ClientListener {
	public void packetData(byte[] data);
	public void socketDisconnect();
}
