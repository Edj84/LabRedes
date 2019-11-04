package controller;

public class SecurityManager {
	
	public SecurityManager() {
		
	}

	public boolean check(String CRC, String message) {
		int crcCalculado = calculate_crc(message)
		if(String.valueOf(crcCalculado) == CRC){
			return true;
		}
		return false;
	}

	public int calculate_crc(String message) {
		byte[] bytes = message.getBytes();
		int i;
		int crc_value = 0;
		for (int len = 0; len < bytes.length; len++) {
			for (i = 0x80; i != 0; i >>= 1) {
				if ((crc_value & 0x8000) != 0) {
					crc_value = (crc_value << 1) ^ 0x8005;
				} else {
					crc_value = crc_value << 1;
				}
				if ((bytes[len] & i) != 0) {
					crc_value ^= 0x8005;
				}
			}
		}
		return crc_value;
	}

	public String generateCrcError (String CRC) {
		int crcInt =  Integer.parseInt(CRC);
		crcInt++;
		return String.valueOf(crcInt);
	}

}
