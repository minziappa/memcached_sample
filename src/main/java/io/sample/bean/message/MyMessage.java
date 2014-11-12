package io.sample.bean.message;

import org.msgpack.annotation.Message;

@Message // Annotation
public class MyMessage {

    // public fields are serialized.
	public String cmd;
    public String name;
    public int version;

}
