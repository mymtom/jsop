/*
 * Copyright (c) 2019 mymtom
 *
 */

package cn.mymtom.jsop.field;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class StringFieldTest {

	@Test
	public void testRead() throws Exception {
		byte[] buf = new byte[4];
		int pos = 0;
		buf[pos++] = (byte) '9';
		buf[pos++] = (byte) '3';
		buf[pos++] = (byte) '2';
		buf[pos++] = (byte) '8';

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);
		
		StringField field = new StringField();
		field.setLength(len);
		ByteArrayInputStream in = new ByteArrayInputStream(buf0);
		field.read(in);

		String value = (String) field.getValue();
		assertThat(value, equalTo("9328"));
	}

	@Test
	public void testReadWithPaddingZero() throws Exception {
		byte[] buf = new byte[1024];
		int pos = 0;
		buf[pos++] = (byte) '9';
		buf[pos++] = (byte) '3';
		buf[pos++] = (byte) '\0';
		buf[pos++] = (byte) '\0';

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);
		
		StringField field = new StringField();
		field.setLength(len);
		ByteArrayInputStream in = new ByteArrayInputStream(buf0);
		field.read(in);

		String value = (String) field.getValue();
		assertThat(value, equalTo("93"));
	}

	@Test
	public void testWrite() throws Exception {
		byte[] buf = new byte[1024];
		int pos = 0;
		buf[pos++] = (byte) '9';
		buf[pos++] = (byte) '3';
		buf[pos++] = (byte) '2';
		buf[pos++] = (byte) '8';

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);

		StringField field = new StringField();
		field.setLength(len);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		field.setValue("9328");

		field.write(out);
		byte[] buf1 = out.toByteArray();

		assertThat(buf1, equalTo(buf0));
	}

	@Test
	public void testWriteWithPaddingZero() throws Exception {
		byte[] buf = new byte[1024];
		int pos = 0;
		buf[pos++] = (byte) '9';
		buf[pos++] = (byte) '3';
		buf[pos++] = (byte) '\0';
		buf[pos++] = (byte) '\0';

		int len = pos;
		byte[] buf0 = new byte[len];
		System.arraycopy(buf, 0, buf0, 0, len);

		StringField field = new StringField();
		field.setLength(len);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		field.setValue("93");

		field.write(out);
		byte[] buf1 = out.toByteArray();
		
		assertThat(buf1, equalTo(buf0));
	}

}
