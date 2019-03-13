/*
 * Copyright (c) 2019 mymtom
 *
 */

package cn.mymtom.jsop.field;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ShortField extends FixedLengthField {

	ShortField() {
		setValue(0);
		setLength(2);
	}

	@Override
	public void readValue(InputStream in) throws IOException {
		int length = getLength();
		byte[] buffer = new byte[length];
		in.read(buffer, 0, length);
		int value = 0;
		value |= (buffer[0] & 0xFF) <<  8;
		value |= (buffer[1] & 0xFF) <<  0;
		setValue(value);
	}

	@Override
	public void writeValue(OutputStream out) throws IOException {
		int length = getLength();
		byte[] buffer = new byte[length];

		int value;
		Object objValue = this.getValue();
		if (objValue instanceof Integer || objValue instanceof Short) {
			value = (Integer) objValue;
		} else if (objValue instanceof String) {
			String strValue = (String) objValue;
			if (strValue.isEmpty()) {
				value = 0;
			} else { 
				value = Integer.parseInt(strValue);
			}
		} else {
			value = Integer.parseInt(objValue.toString());
		}

		buffer[0] = (byte) ((value >>  8) & 0xFF);
		buffer[1] = (byte) ((value >>  0) & 0xFF);
		out.write(buffer, 0, length);
	}

}
