package io.sample.service.impl;

import io.sample.memcached.MemcachedAccessManager;
import io.sample.service.TokenService;
import io.sample.utility.TokenUtility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

	final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

	@Autowired
    private Configuration configuration;

	@Autowired
	private MemcachedAccessManager memcachedAccessManager;

 	@Override
	public String saveToken(String ekey, Date expiry) {

		boolean blSet = false;
		String tokenKey = null;

		try {
			tokenKey = TokenUtility.generateToken(ekey);

			String tokenValue = generateTokenValue(tokenKey,configuration.getString("cipher.key"));

			blSet = memcachedAccessManager.set(tokenKey, tokenValue, expiry);

			if(!blSet) {
				logger.warn("set ; key is " + ekey + ", tokenKey is " 
						+ tokenKey + ", tokenValue is " + tokenValue + ", Set is " 
						+ blSet + ", tokenValue-get is " + memcachedAccessManager.get(tokenKey) + ", expiry is " + expiry);				
			}

		} catch (Exception e) {
			logger.error("saveToken Exception error", e);
		}

		return tokenKey;
	}

	public boolean isTokenValid(String ekey, String fishingToken) {

		Object object = null;

		try {
			object = memcachedAccessManager.get(ekey);

			//Check if a ip was set
			if(object == null) {
				logger.error("fishingToken is null, key is " + ekey + ", fishingToken is " + fishingToken);
				return false;
			}

			if (fishingToken == null || !fishingToken.trim().equals((String)object)) {
				logger.error("fishingToken is null, key is " + ekey + ", fishingToken is " + fishingToken);
				return false;
			}

			if(!memcachedAccessManager.delete(ekey)) {
				logger.error("Delete key is fail in Pc. key is " + ekey);
			}

		} catch (Exception e) {
			logger.error("isTokenValid Exception. ekey=" + ekey + ", fishingToken=" + fishingToken, e);
		}

		return true;
	}

	private String generateTokenValue(
			String tokenKey,
			String commonKey) {

        try {
            byte tokenKeyByte[] = tokenKey.getBytes();
            byte commonKeyByte[] = commonKey.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            md.update(tokenKeyByte);
            md.update(commonKeyByte);

            return toHex(md.digest());
        } catch (NoSuchAlgorithmException e){
            return null;
        }
    }

    private static String toHex(byte buffer[]) {

    	StringBuilder sb = new StringBuilder(buffer.length * 2);

    	for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }

        return sb.toString();
    }

}
