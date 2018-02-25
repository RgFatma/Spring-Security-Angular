package org.sig.service;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;

public interface AccountService {

	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole user);
	public void addRoleToUser(String username,String roleName);
	public AppUser findUserByUserName(String username);
}
