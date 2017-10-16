package com.mayforever.tools;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Date;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			// Date date = new Date();
			// System.out.println(date.getTime());
			double sampleDouble = 1.2541654646d;
			byte[] datebytes = BitConverter.doubleToBytes(sampleDouble, ByteOrder.BIG_ENDIAN);
			System.out.println(Arrays.toString(datebytes));
			
			// System.out.println(BitConverter.bytesToLong(datebytes,0,ByteOrder.BIG_ENDIAN));
			double dateLong = BitConverter.bytesToDouble(datebytes, 0, ByteOrder.BIG_ENDIAN);
			System.out.println(dateLong);
			
//			Date date = new Date();
//			System.out.println(date.getTime());
//			byte[] datebytes = BitConverter.longToBytes(date.getTime());
//			System.out.println(Arrays.toString(datebytes));
//			
//			// System.out.println(BitConverter.bytesToLong(datebytes,0,ByteOrder.BIG_ENDIAN));
//			long dateLong = BitConverter.bytesToLong(datebytes);
//			System.out.println(dateLong);
	}

}
