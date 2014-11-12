package io.sample.memcached.impl;

import io.sample.memcached.CommonMemcached;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * Master memcached
 * 
 * @author kim_woongjoon
 * @version 1.1
 * @since 1.1
 */
public class MemcachedMasterFactory implements CommonMemcached {

	final Logger logger = LoggerFactory.getLogger(MemcachedMasterFactory.class);

	private SockIOPool pool = null;

	private String strMemcachedIp = "localhost:11211";
	private int initConn = 5;
	private int minConn = 5;
	private int maxConn = 50;
	private int maintSleep = 30;
	private boolean nagle = true;
	private int socketTO = 1500;
	private int socketConnectTO = 2000;
	private boolean aliveCheck = true;
	private int maxIdle = 10000;
	private int maxBusyTime = 3000;

	/**
	 * initialize Master memcached connection
	 */
	@SuppressWarnings("unused")
	private void init() {

		// Get the White List File.
		try {

			String[] serverlist = { this.strMemcachedIp };
			// initialize the pool for memcache servers
			pool = SockIOPool.getInstance("Master");

			pool.setServers(serverlist);
			pool.setInitConn(initConn);
			pool.setMinConn(minConn);
			pool.setMaxConn(maxConn);
			pool.setMaintSleep(maintSleep);
			pool.setNagle(nagle);
			pool.setSocketTO(socketTO);
			pool.setSocketConnectTO(socketConnectTO);
			pool.setAliveCheck(aliveCheck);
			pool.setMaxIdle(maxIdle);
			pool.setMaxBusyTime(maxBusyTime);
			pool.initialize();

		} catch (Exception e) {
			logger.error("Exception of the error of closeing is:strMemcachedUrl="
					+ strMemcachedIp, e);
		}
	}

	public boolean setMemcached(String key, Object value) {
		MemCachedClient mc = new MemCachedClient("Master");
		return mc.set(key, value);
	}

	public boolean setMemcached(String key, Object value, Date expiry) {
		MemCachedClient mc = new MemCachedClient("Master");
		return mc.set(key, value, expiry);
	}

	public boolean deleteMemcached(String key) {
		MemCachedClient mc = new MemCachedClient("Master");
		return mc.delete(key);
	}

	public Object getMemcached(String key) {
		MemCachedClient mc = new MemCachedClient("Master");
		return mc.get(key);
	}

	public void shutdown() {
		pool.shutDown();
	}

	public String getStrMemcachedIp() {
		return strMemcachedIp;
	}

	public void setStrMemcachedIp(String strMemcachedIp) {
		this.strMemcachedIp = strMemcachedIp;
	}

	public int getInitConn() {
		return initConn;
	}

	public void setInitConn(int initConn) {
		this.initConn = initConn;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public int getMaintSleep() {
		return maintSleep;
	}

	public void setMaintSleep(int maintSleep) {
		this.maintSleep = maintSleep;
	}

	public boolean isNagle() {
		return nagle;
	}

	public void setNagle(boolean nagle) {
		this.nagle = nagle;
	}

	public int getSocketTO() {
		return socketTO;
	}

	public void setSocketTO(int socketTO) {
		this.socketTO = socketTO;
	}

	public int getSocketConnectTO() {
		return socketConnectTO;
	}

	public void setSocketConnectTO(int socketConnectTO) {
		this.socketConnectTO = socketConnectTO;
	}

	public boolean isAliveCheck() {
		return aliveCheck;
	}

	public void setAliveCheck(boolean aliveCheck) {
		this.aliveCheck = aliveCheck;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxBusyTime() {
		return maxBusyTime;
	}

	public void setMaxBusyTime(int maxBusyTime) {
		this.maxBusyTime = maxBusyTime;
	}
}
