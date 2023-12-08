package com.nse.pms.standard.common.service;

public interface CommonAuthService {

	/*
	 * 권한있는 사용자의 접근인지 여부를 확인 
	 * param : userId, url
	 */
	public boolean isValidAccess(String userId, String menuUrl);

}
