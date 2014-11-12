package io.sample.memcached;

import java.util.Date;

public interface CommonMemcached {

	public boolean setMemcached(String key, Object value);
	public boolean setMemcached(String key, Object value, Date expiry);
	public boolean deleteMemcached(String key);
	public Object getMemcached(String key);
	public void shutdown();

}