package com.mayforever.network.tcp;

import java.io.IOException;
import java.net.Socket;

public interface ServerListener {
	public void acceptSocket(Socket socket);
	public void socketError(IOException e);
}
