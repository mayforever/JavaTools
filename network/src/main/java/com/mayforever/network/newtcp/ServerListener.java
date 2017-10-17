package com.mayforever.network.newtcp;

import java.nio.channels.AsynchronousSocketChannel;

public interface ServerListener {
	public void acceptSocket(AsynchronousSocketChannel socket);
	public void socketError(Exception e);
}
