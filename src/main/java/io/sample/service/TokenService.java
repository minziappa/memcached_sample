package io.sample.service;

import java.util.Date;

public interface TokenService {

	public boolean isTokenValid(String ekey, String fishingToken);

	public String saveToken(String ekey, Date expiry);
}