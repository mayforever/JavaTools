package com.mayforever.tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BitConverter {
	
	public static byte[] shortToBytes(short valueInShort,ByteOrder byteOrder){
		byte[] tBytes=new byte[2];
		if(byteOrder==ByteOrder.BIG_ENDIAN){
			tBytes[0]=(byte)(valueInShort & 0xff);
			tBytes[1]=(byte) ((valueInShort >> 8) & 0xff);
		}else{
			tBytes[7]=(byte)(valueInShort & 0xff);
			tBytes[6]=(byte) ((valueInShort >> 8) & 0xff);
		}
		return tBytes;
	}
	
	public static byte[] intToBytes(int valueInInt,ByteOrder byteOrder){
		byte[] tBytes=new byte[4];
		if(byteOrder==ByteOrder.BIG_ENDIAN){
			tBytes[0]=(byte)(valueInInt & 0xff);
			tBytes[1]=(byte) ((valueInInt >> 8) & 0xff);
			tBytes[2]=(byte) ((valueInInt >> 16) & 0xff);
			tBytes[3]=(byte) ((valueInInt >> 24) & 0xff);
		}else{
			tBytes[3]=(byte)(valueInInt & 0xff);
			tBytes[2]=(byte) ((valueInInt >> 8) & 0xff);
			tBytes[1]=(byte) ((valueInInt >> 16) & 0xff);
			tBytes[0]=(byte) ((valueInInt >> 24) & 0xff);
		}
		return tBytes;
	}

	
	public static byte[] longToBytes(long valueInLong,ByteOrder byteOrder){
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.order(byteOrder);
        buffer.putLong(0, valueInLong);
        
        return buffer.array();
	}
	
	public static byte[] floatToBytes(float valueInFloat,ByteOrder byteOrder){
		byte[] tBytes=new byte[4];
		int intBits=Float.floatToIntBits(valueInFloat);
		if(byteOrder==ByteOrder.BIG_ENDIAN){
			tBytes[0]=(byte)(intBits & 0xff);
			tBytes[1]=(byte) ((intBits >> 8) & 0xff);
			tBytes[2]=(byte) ((intBits >> 16) & 0xff);
			tBytes[3]=(byte) ((intBits >> 24) & 0xff);
		}else{
			tBytes[3]=(byte)(intBits & 0xff);
			tBytes[2]=(byte) ((intBits >> 8) & 0xff);
			tBytes[1]=(byte) ((intBits >> 16) & 0xff);
			tBytes[0]=(byte) ((intBits >> 24) & 0xff);
		}
		return tBytes;
	}
	

	public static byte[] doubleToBytes(double valueInLong,ByteOrder byteOrder){
		ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
		buffer.order(byteOrder);
        buffer.putDouble(0, valueInLong);
        
        return buffer.array();
	}
	
	public static short bytesToShort(byte[] byteArray,int offset,ByteOrder byteOrder){
		if(byteOrder==ByteOrder.BIG_ENDIAN){
			return 	(short) (((byteArray[offset+1] & 0xff) << 8) |
						(byteArray[offset] & 0xff));
		}else{
			return 		(short) (((byteArray[offset] & 0xff) << 8) |
					(byteArray[offset] & 0xff));
		}
	}
	
	public static int bytesToInt(byte[] byteArray,int offset,ByteOrder byteOrder){
		if(byteOrder==ByteOrder.BIG_ENDIAN){
			return 	(int) (((byteArray[offset+3] & 0xff) << 24) |
					 		((byteArray[offset+2] & 0xff) << 16) |
							 ((byteArray[offset+1] & 0xff) << 8) |
							 (byteArray[offset] & 0xff));
		}else{
			return 	(int) (((byteArray[offset] & 0xff) << 24) |
			 			((byteArray[offset+1] & 0xff) << 16) |
			 			((byteArray[offset+2] & 0xff) << 8) |
			 			(byteArray[offset+3] & 0xff));
		}
	}

	public static long bytesToLong(byte[] byteArray,int offset,ByteOrder byteOrder){
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(byteArray, offset, 8);
        buffer.order(byteOrder);
        buffer.flip();//need flip 
        return buffer.getLong();
	}

	
	public static float bytesToFloat(byte[] byteArray, int offset, ByteOrder byteOrder){
		int intBits=0;
		if(byteOrder==ByteOrder.BIG_ENDIAN){
			intBits=(int) (((byteArray[offset+3] & 0xff) << 24) |
					((byteArray[offset+2] & 0xff) << 16) |
					((byteArray[offset+1] & 0xff) << 8) |
					(byteArray[offset] & 0xff));
		}else{
			intBits=(int) (((byteArray[offset] & 0xff) << 24) |
					((byteArray[offset+1] & 0xff) << 16) |
					((byteArray[offset+2] & 0xff) << 8) |
					(byteArray[offset+3] & 0xff));
		}
		return Float.intBitsToFloat(intBits);
	}
	
	
	public static double bytesToDouble(byte[] byteArray,int offset,ByteOrder byteOrder){
		ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.put(byteArray, offset, 8);
        buffer.order(byteOrder);
        buffer.flip();//need flip 
        return buffer.getDouble();
	}

}
