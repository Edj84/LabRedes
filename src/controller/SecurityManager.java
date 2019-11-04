package controller;

public class SecurityManager {
	
	public SecurityManager() {
		
	}

	public boolean check(String CRC, String message) {
		int crcCalculado = crc16(message);
		if(String.valueOf(crcCalculado) == CRC){
			return true;
		}
		return false;
	}

	public static int crc16(String message) {
	int crc = 0xFFFF;
	byte[] buffer = message.getBytes();
	for (int j = 0; j < buffer.length ; j++) {
		crc = ((crc  >>> 8) | (crc  << 8) )& 0xffff;
		crc ^= (buffer[j] & 0xff);//byte to int, trunc sign
		crc ^= ((crc & 0xff) >> 4);
		crc ^= (crc << 12) & 0xffff;
		crc ^= ((crc & 0xFF) << 5) & 0xffff;
	}
	crc &= 0xffff;
	return crc;
	}

	public String generateCrcError (String CRC) {
		int crcInt =  Integer.parseInt(CRC);
		crcInt++;
		return String.valueOf(crcInt);
	}

}
