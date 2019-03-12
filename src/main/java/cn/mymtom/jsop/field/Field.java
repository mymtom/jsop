/*
 * Copyright (c) 2019 mymtom
 *
 */

package cn.mymtom.jsop.field;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Field implements Cloneable {
	
	protected String name;
	protected int length;
	protected Object value;
	protected String comment;
	protected List<String> names;
	protected List<Field> fields;
	protected Charset charset;

	public void read(InputStream in) throws IOException {
		readValue(in);
		readFields(in);
	}

	public void write(OutputStream out) throws IOException {
		writeValue(out);
		writeFields(out);
	}

	abstract protected void readValue(InputStream in) throws IOException;
	abstract protected void writeValue(OutputStream out) throws IOException;

	void readFields(InputStream in) throws IOException {
		if (null == fields)
			return;
		for (Field field : fields) {
			field.read(in);
		}
	}

	void writeFields(OutputStream out) throws IOException {
		if (null == fields)
			return;
		for (Field field : fields) {
			field.write(out);
		}
	}

	public byte[] pack() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		write(out);
		return out.toByteArray();
	}

	public Field unpack(byte[] buf) throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(buf);
		read(in);
		return this;
	}

	public Map<String, Object> getValues() {

		Map<String, Object> values = new HashMap<String, Object>();
		if (null != value)
			values.put(name, value.toString());

		if (null != fields) {
			for (Field field : fields) {
				values.putAll(field.getValues());
			}
		}

		return values;
	}


	public void setValues(Map<String, Object> values) {
		if (values.containsKey(name)) {
			value = values.get(name);
			return;
		}

		if (null != fields) {
			for (Field field : fields) {
				field.setValues(values);
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		builder.append(" [name=");
		builder.append(name);
		builder.append(", length=");
		builder.append(length);
		if (value != null) {
			builder.append(", value=");
			builder.append(value);
		}
		builder.append(", comment=");
		builder.append(comment);
		if (names != null && names.size() > 0) {
			builder.append(", subNames=");
			builder.append(names);
		}
		if (fields != null && fields.size() > 0) {
			builder.append(", subFields=");
			builder.append(fields);
		}
		builder.append("]");
		return builder.toString();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}


}
