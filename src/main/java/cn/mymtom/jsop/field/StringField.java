/*
 * Copyright (c) 2019 mymtom
 *
 */

package cn.mymtom.jsop.field;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class StringField extends FixedLengthField {

	@Override
	protected void readValue(InputStream in) throws IOException {
		int length = this.getLength();
		byte[] buffer = new byte[length];
		in.read(buffer, 0, length);

		// Discard '\0' and bytes followed
		int pos;
		for (pos = 0; pos < length; pos++) {
			if ('\0' == buffer[pos])
				break;
		}
		String value;
		Charset charset = getCharset();
		if (null == charset) {
			value = new String(buffer, 0, pos);
		} else {
			value = new String(buffer, 0, pos, charset);
		}

		this.setValue(value);
	}

	@Override
	protected void writeValue(OutputStream out) throws IOException {
		String value = (String) this.getValue();
		Charset charset = getCharset();
		byte[] buffer;
		if (null == charset) {
			buffer = value.getBytes();
		} else {
			buffer = value.getBytes(charset);
		}
		out.write(buffer);

		// Pad with '\0'
		int pos;
		int length = this.getLength();
		for (pos = buffer.length; pos < length; pos++) {
			out.write(0);
		}
	}

}
