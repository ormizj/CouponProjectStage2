package com.jbc.service;

import com.jbc.exception.companyException.CompanyNotFoundException;
import com.jbc.exception.customerException.CustomerNotFoundException;
import com.jbc.exception.loginException.InvalidLoginException;
import com.jbc.exception.loginException.NullLoginException;
import com.jbc.model.user.User;
import com.jbc.util.serviceUtil.UserTypeUtil;

/**
 * {@code interface} that contains the methods used by the
 * {@link com.jbc.service.LoginManagerService} {@code Service} to ensure the
 * business logic of the system, related to the login system.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see user#User
 * @see service#LoginManagerService
 */
public interface LoginManagerServiceIfc {
	
	/**
	 * Attempts to login as a user based on the
	 * {@link com.jbc.util.serviceUtil.UserTypeUtil} type, {@code email} and
	 * {@code password}, if the login is successful returns an {@code Entity} based
	 * on the {@code userType}.
	 * 
	 * @param email
	 * @param password
	 * @param userType
	 * @return
	 * @throws InvalidLoginException     if the {@code Entity} {@code email} or
	 *                                   {@code password} is incorrect.
	 * @throws NullLoginException        if any of the parameters value are
	 *                                   {@code null}.
	 * @throws CustomerNotFoundException does not get thrown.
	 * @throws CompanyNotFoundException  does not get thrown.
	 */
	public User login(String email, String password, UserTypeUtil userType)
			throws InvalidLoginException, NullLoginException, CustomerNotFoundException, CompanyNotFoundException;

}
