/*
 * Copyright (c) 2019 mymtom
 *
 */

package cn.mymtom.jsop.field;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ShortFieldTest {

	@Test
	public void testRead()  throws Exception {
		byte[] buf = new byte[2];
		int pos = 0;
		buf[pos++] = (byte) 0x12;
		buf[pos++] = (byte) 0x34;

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);
		
		ShortField field = new ShortField();
		ByteArrayInputStream in = new ByteArrayInputStream(buf0);
		field.read(in);
		int value = (Integer) field.getValue();
		assertThat(value, equalTo(0x1234));
	}

	@Test
	public void testWrite()  throws Exception {
		byte[] buf = new byte[2];
		int pos = 0;
		buf[pos++] = (byte) 0x12;
		buf[pos++] = (byte) 0x34;

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);

		ShortField field = new ShortField();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		field.setValue(0x1234);

		field.write(out);
		byte[] buf1 = out.toByteArray();
		assertThat(buf1, equalTo(buf0));
	}

	@Test
	public void testWriteStringValue()  throws Exception {
		byte[] buf = new byte[2];
		int pos = 0;
		buf[pos++] = (byte) 0x12;
		buf[pos++] = (byte) 0x34;

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);

		ShortField field = new ShortField();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		field.setValue(String.format("%d", 0x1234));

		field.write(out);
		byte[] buf1 = out.toByteArray();
		assertThat(buf1, equalTo(buf0));
	}
}
