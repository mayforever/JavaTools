package com.mayforever.network.newtcp;

public interface ClientListener {
	public void packetData(byte[] data);
	public void socketError(Exception e);
}
