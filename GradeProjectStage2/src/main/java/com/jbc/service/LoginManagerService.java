package com.jbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbc.exception.companyException.CompanyNotFoundException;
import com.jbc.exception.customerException.CustomerNotFoundException;
import com.jbc.exception.loginException.InvalidLoginException;
import com.jbc.exception.loginException.NullLoginException;
import com.jbc.model.user.User;
import com.jbc.service.user.ifc.AdminServiceIfc;
import com.jbc.service.user.ifc.CompanyServiceIfc;
import com.jbc.service.user.ifc.CustomerServiceIfc;
import com.jbc.util.serviceUtil.UserTypeUtil;

/**
 * {@code Service} that {@code implements}
 * {@link com.jbc.service.LoginManagerServiceIfc}, and manages the login
 * system, and retrieving corresponding {@code Entity}s based on the
 * {@code email}, {@code password} and {@code userType} with the help of the
 * {@code Entity} {@code Service}s.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see service#LoginManagerServiceIfc
 * @see ifc#CustomerServiceIFC
 * @see ifc#CompanyServiceIFC
 * @see ifc#AdminServiceIFC
 * @see serviceUtil#UserTypeUtil
 */
@Service
public class LoginManagerService implements LoginManagerServiceIfc {

	/* attributes */
	@Autowired
	private AdminServiceIfc adminServ;
	@Autowired
	private CompanyServiceIfc companyServ;
	@Autowired
	private CustomerServiceIfc customerServ;

	@Override
	public User login(String email, String password, UserTypeUtil userType)
			throws InvalidLoginException, NullLoginException, CustomerNotFoundException, CompanyNotFoundException {
		if (email == null || password == null || userType == null)
			throw new NullLoginException();
		boolean emailExists = false;
		switch (userType) {
		case ADMIN:
			Optional<User> adminOption = adminServ.login(email, password);
			if (adminOption.isPresent())
				return adminOption.get();
			break;
		case COMPANY:
			if (companyServ.login(email, password))
				return adminServ.getCompany(email);
			emailExists = companyServ.companyExists(email);
			break;
		case CUSTOMER:
			if (customerServ.login(email, password))
				return adminServ.getCustomer(email);
			emailExists = customerServ.customerExists(email);
			break;
		default:
			break;
		}
		throw new InvalidLoginException(email, emailExists, userType);
	}

}
