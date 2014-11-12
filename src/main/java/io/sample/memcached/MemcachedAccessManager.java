package io.sample.memcached;

import io.sample.memcached.impl.MemcachedMasterFactory;
import io.sample.memcached.impl.MemcachedSlaveFactory;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manage to access Memcached.
 * 
 * <pre>
 * Have a function of failover.
 * </pre>
 * 
 * @author kim_woongjoon
 * @version 1.1
 * @since 1.1
 */
public class MemcachedAccessManager {

	final Logger logger = LoggerFactory.getLogger(MemcachedAccessManager.class);

	@Autowired
	private MemcachedMasterFactory memcachedMasterFactory;
	@Autowired
	private MemcachedSlaveFactory memcachedSlaveFactory;

	/**
	 * Set memcached memcachedMaster/memcachedSlave
	 * 
	 * @param key
	 * @param value
	 * @return result status
	 */
	public boolean set(String key, Object value) {
		boolean result = memcachedMasterFactory.setMemcached(key, value);
		if (result) {
			// OK
			logger.debug("memcached master set");

		} else {
			// NG
			result = memcachedSlaveFactory.setMemcached(key, value);
			logger.debug("memcached slave set");

		}

		return result;
	}

	/**
	 * Set memcached memcachedMaster/memcachedSlave
	 * 
	 * @param key
	 * @param value
	 * @param expiry
	 * @return result status
	 */
	public boolean set(String key, Object value, Date expiry) {
		boolean result = memcachedMasterFactory.setMemcached(key, value, expiry);
		if (result) {
			// OK
			logger.debug("memcached master set");
		} else {
			// NG
			result = memcachedSlaveFactory.setMemcached(key, value, expiry);
			logger.debug("memcached slave set");
		}

		return result;
	}

	/**
	 * Set memcached memcachedMaster/memcachedSlave
	 * 
	 * @param key
	 * @param value
	 * @param expiry
	 * @return result status
	 */
	public boolean delete(String key) {
		boolean result = memcachedMasterFactory.deleteMemcached(key);
		if (result) {
			// OK
			logger.debug("memcached master set");
		} else {
			// NG
			result = memcachedSlaveFactory.deleteMemcached(key);
			logger.debug("memcached slave set");
		}

		return result;
	}

	/**
	 * Get memcached memcachedMaster/memcachedSlave
	 * 
	 * @param key
	 * @return value object
	 */
	public Object get(String key) {
		Object result = memcachedMasterFactory.getMemcached(key);
		if (result != null) {
			// OK
			logger.debug("memcached master get");
		} else {
			// NG
			result = memcachedSlaveFactory.getMemcached(key);
			logger.debug("memcached slave get");
		}

		return result;
	}

	/**
	 * Shutdown memcached pool
	 */
	public void shutdown() {
		if (memcachedMasterFactory != null) {
			memcachedMasterFactory.shutdown();
		}

		if (memcachedSlaveFactory != null) {
			memcachedSlaveFactory.shutdown();
		}
	}
}
