package com.jbc.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbc.exception.companyException.CompanyAlreadyExistsException;
import com.jbc.exception.companyException.CompanyDuplicateValueException;
import com.jbc.exception.companyException.CompanyIsEmptyException;
import com.jbc.exception.companyException.CompanyIsNullException;
import com.jbc.exception.companyException.CompanyNotFoundException;
import com.jbc.exception.companyException.CompanyNullValueException;
import com.jbc.exception.couponException.CouponIsEmptyException;
import com.jbc.exception.customerException.CustomerAlreadyExistsException;
import com.jbc.exception.customerException.CustomerDuplicateValueException;
import com.jbc.exception.customerException.CustomerIsEmptyException;
import com.jbc.exception.customerException.CustomerIsNullException;
import com.jbc.exception.customerException.CustomerNotFoundException;
import com.jbc.exception.customerException.CustomerNullValueException;
import com.jbc.exception.loggerException.LoggerIsEmptyException;
import com.jbc.model.Coupon;
import com.jbc.model.Logger;
import com.jbc.model.user.Company;
import com.jbc.model.user.Customer;
import com.jbc.model.user.User;
import com.jbc.repository.CouponRepository;
import com.jbc.repository.LoggerRepository;
import com.jbc.repository.user.AdminRepository;
import com.jbc.repository.user.CompanyRepository;
import com.jbc.repository.user.CustomerRepository;
import com.jbc.service.user.ifc.AdminServiceIfc;
import com.jbc.util.exceptionUtil.ExceptionUtil;
import com.jbc.util.generalUtil.StringClass;
import com.jbc.util.modelUtil.ModelEntityUtil;
import com.jbc.util.serviceUtil.AdminUtil;
import com.jbc.util.serviceUtil.ModelActionUtil;
import com.jbc.util.serviceUtil.UserTypeUtil;
import com.jbc.util.serviceUtil.loggerUtil.AdminLoggerAttributer;
import com.jbc.util.serviceUtil.loggerUtil.CompanyLoggerAttributer;
import com.jbc.util.serviceUtil.loggerUtil.CustomerLoggerAttributer;
import com.jbc.util.serviceUtil.validationUtil.CompanyValidation;
import com.jbc.util.serviceUtil.validationUtil.CustomerValidation;

/**
 * {@code Service} that {@code implements} multiple {@code interface}s to ensure
 * the business logic of the system, related to the {@code Entity}s of the
 * system before executing to the data-base.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see ifc#AdminServiceIFC
 * @see validationUtil#CompanyValidation
 * @see validationUtil#CustomerValidation
 * @see attributeUtil#AdminLoggerAttributer
 * @see attributeUtil#CompanyLoggerAttributer
 * @see attributeUtil#CustomerLoggerAttributer
 * @see attributeUtil#CouponLoggerAttributer
 * @see factory#InitializingBean
 * @see user#AdminRepository
 * @see user#CompanyRepository
 * @see user#CustomerRepository
 * @see repository#CouponRepository
 * @see repository#LoggerRepository
 */
@Service
public class AdminService implements AdminServiceIfc, CompanyValidation, CustomerValidation, AdminLoggerAttributer,
		CompanyLoggerAttributer, CustomerLoggerAttributer, InitializingBean {

	/* attributes */
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private CompanyRepository companyRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CouponRepository couponRepo;
	@Autowired
	private LoggerRepository loggerRepo;

	@Override
	public Optional<User> login(String email, String password) {
		Optional<User> optionAdmin = adminRepo.findByEmailIgnoreCaseAndPassword(email, password);
		if (!optionAdmin.isPresent())
			return optionAdmin;
		User loggedAdmin = optionAdmin.get();
		loggerRepo.save(new Logger(loggedAdmin.getId(), UserTypeUtil.ADMIN, loggedAdmin.getId(), ModelEntityUtil.ADMIN,
				ModelActionUtil.LOG_IN, attributeAdmin(loggedAdmin), "-"));
		return optionAdmin;
	}

	@Override
	public Company createCompany(long adminId, Company createdCompany) throws CompanyNullValueException,
			CompanyIsNullException, CompanyDuplicateValueException, CompanyAlreadyExistsException {
		checkCompany(createdCompany);
		if (companyRepo.existsById(createdCompany.getId())) {
			throw new CompanyAlreadyExistsException(createdCompany.getId());
		}
		createdCompany = companyRepo.save(createdCompany);
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, createdCompany.getId(), ModelEntityUtil.COMPANY,
				ModelActionUtil.CREATE, "-", attributeCompany(createdCompany)));
		return createdCompany;
	}

	@Override
	public Company getCompany(long companyId) throws CompanyNotFoundException {
		Optional<Company> companyOption = companyRepo.findById(companyId);
		if (companyOption.isPresent())
			return companyOption.get();
		throw new CompanyNotFoundException(companyId);
	}

	@Override
	public Company getCompany(String email) throws CompanyNotFoundException {
		Optional<Company> company = companyRepo.findByEmailIgnoreCase(email);
		if (company.isPresent())
			return company.get();
		throw new CompanyNotFoundException(email);
	}

	@Override
	public Company updateCompany(long adminId, Company updatedCompany) throws CompanyNullValueException,
			CompanyIsNullException, CompanyDuplicateValueException, CompanyNotFoundException {
		checkCompany(updatedCompany);
		Optional<Company> optionCompany = companyRepo.findById(updatedCompany.getId());
		if (!optionCompany.isPresent()) {
			throw new CompanyNotFoundException(updatedCompany.getId());
		}
		Company oldCompany = optionCompany.get();
		updatedCompany = companyRepo.save(updatedCompany);
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, updatedCompany.getId(), ModelEntityUtil.COMPANY,
				ModelActionUtil.UPDATE, attributeCompany(oldCompany), attributeCompany(updatedCompany)));
		return updatedCompany;
	}

	@Override
	public void deleteCompany(long adminId, long companyId) throws CompanyNotFoundException {
		Optional<Company> optionCompany = companyRepo.findById(companyId);
		if (!optionCompany.isPresent()) {
			throw new CompanyNotFoundException(companyId);
		}
		Company deletedCompany = optionCompany.get();
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, deletedCompany.getId(), ModelEntityUtil.COMPANY,
				ModelActionUtil.DELETE, attributeCompany(deletedCompany), "-"));
		companyDeleter(companyId);
	}

	@Override
	public List<Company> getCompanies() throws CompanyIsEmptyException {
		List<Company> companies = companyRepo.findAll();
		if (companies.isEmpty())
			throw new CompanyIsEmptyException();
		return companies;
	}

	@Override
	public Customer addCustomer(long adminId, Customer createdCustomer) throws CustomerDuplicateValueException,
			CustomerIsNullException, CustomerNullValueException, CustomerAlreadyExistsException {
		checkCustomer(createdCustomer);
		if (customerRepo.existsById(createdCustomer.getId())) {
			throw new CustomerAlreadyExistsException(createdCustomer.getId());
		}
		createdCustomer = customerRepo.save(createdCustomer);
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, createdCustomer.getId(), ModelEntityUtil.CUSTOMER,
				ModelActionUtil.CREATE, "-", attributeCustomer(createdCustomer)));
		return createdCustomer;
	}

	@Override
	public List<Customer> getCustomers() throws CustomerIsEmptyException {
		List<Customer> customers = customerRepo.findAll();
		if (customers.isEmpty())
			throw new CustomerIsEmptyException();
		return customers;
	}

	@Override
	public Customer getCustomer(long customerId) throws CustomerNotFoundException {
		Optional<Customer> customerOption = customerRepo.findById(customerId);
		if (customerOption.isPresent())
			return customerOption.get();
		throw new CustomerNotFoundException(customerId);
	}

	@Override
	public Customer getCustomer(String email) throws CustomerNotFoundException {
		Optional<Customer> customer = customerRepo.findByEmailIgnoreCase(email);
		if (customer.isPresent())
			return customer.get();
		throw new CustomerNotFoundException(email);
	}

	@Override
	public Customer updateCustomer(long adminId, Customer updatedCustomer) throws CustomerDuplicateValueException,
			CustomerIsNullException, CustomerNullValueException, CustomerNotFoundException {
		checkCustomer(updatedCustomer);
		Optional<Customer> optionCustomer = customerRepo.findById(updatedCustomer.getId());
		if (!optionCustomer.isPresent())
			throw new CustomerNotFoundException(updatedCustomer.getId());
		Customer oldCustomer = optionCustomer.get();
		updatedCustomer = customerRepo.save(updatedCustomer);
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, updatedCustomer.getId(), ModelEntityUtil.CUSTOMER,
				ModelActionUtil.UPDATE, attributeCustomer(oldCustomer), attributeCustomer(updatedCustomer)));
		return updatedCustomer;
	}

	@Override
	public void deleteCustomer(long adminId, long customerId) throws CustomerNotFoundException {
		Optional<Customer> optionCustomer = customerRepo.findById(customerId);
		if (!optionCustomer.isPresent())
			throw new CustomerNotFoundException(customerId);
		Customer removedCustomer = optionCustomer.get();
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, removedCustomer.getId(), ModelEntityUtil.CUSTOMER,
				ModelActionUtil.DELETE, attributeCustomer(removedCustomer), "-"));
		removedCustomer.setCoupons(null);
		customerRepo.save(removedCustomer);
		customerRepo.delete(removedCustomer);
	}

	@Override
	public List<Coupon> getCoupons() throws CouponIsEmptyException {
		List<Coupon> coupons = couponRepo.findAll();
		if (coupons.isEmpty())
			throw new CouponIsEmptyException();
		return coupons;
	}

	@Override
	public List<Logger> getLogs() throws LoggerIsEmptyException {
		List<Logger> logs = loggerRepo.findAll();
		if (logs.isEmpty())
			throw new LoggerIsEmptyException();
		return logs;
	}

	@Override
	public void clearLogs(long adminId) throws LoggerIsEmptyException {
		List<Logger> logs = loggerRepo.findAll();
		if (logs.isEmpty())
			throw new LoggerIsEmptyException();
		loggerRepo.deleteAll();
		loggerRepo.save(new Logger(adminId, UserTypeUtil.ADMIN, 0, ModelEntityUtil.LOGGER, ModelActionUtil.DELETE,
				"Cleared all of the logs", "-"));
	}

	/**
	 * Helper method, checks if a {@code Company} {@code Entity} with the same
	 * {@code name} or {@code email} already exists in the system.
	 * <p>
	 * <li>Also uses the {@code companyNullValidation} method to ensure there are no
	 * nulls in the {@code Entity}.</li>
	 * <p>
	 * 
	 * @param companies
	 * @param company
	 * @throws CompanyNullValueException      if the {@code Company} {@code Entity}
	 *                                        has any {@code null} values in it.
	 * @throws CompanyDuplicateValueException if a {@code Company} {@code Entity}
	 *                                        with the same {@code name} or
	 *                                        {@code email} already exists in the
	 *                                        system.
	 * @throws CompanyIsNullException         if the {@code Company} value is
	 *                                        {@code null}.
	 * @see exceptionUtil#ExceptionUtil
	 * @see #companyNullValidation(Company)
	 */
	private void checkCompany(Company company)
			throws CompanyNullValueException, CompanyIsNullException, CompanyDuplicateValueException {
		companyNullValidation(company);
		Optional<Company> companyName = companyRepo.findByIdNotAndNameIgnoreCase(company.getId(), company.getName());
		Optional<Company> companyEmail = companyRepo.findByIdNotAndEmailIgnoreCase(company.getId(), company.getEmail());
		if (companyName.isPresent() || companyEmail.isPresent()) {
			CompanyDuplicateValueException exception = new CompanyDuplicateValueException();
			if (companyName.isPresent())
				exception.addDuplicate(ExceptionUtil.NAME, company.getName());
			if (companyEmail.isPresent())
				exception.addDuplicate(ExceptionUtil.EMAIL, company.getEmail());
			throw exception;
		}
	}

	/**
	 * Helper method, for deleting a {@code Company} {@code Entity}, calls the
	 * {@code deleteCompanySynchronizer} method, to ensure synchronization with the
	 * {@code Company} {@code Entity} coupons, to avoid errors in the system.
	 * 
	 * @param companyId
	 * @see #deleteCompany(long)
	 * @see #companyDeleterSynchronizer(List, int, long)
	 */
	private void companyDeleter(long companyId) {
		List<Coupon> coupons = couponRepo.findByCompanyId(companyId);
		if (coupons.isEmpty())
			companyRepo.deleteById(companyId);
		else
			companyDeleterSynchronizer(coupons, coupons.size(), companyId);
	};

	/**
	 * Helper method, that ensures the synchronization of the coupons when deleting
	 * a {@code Company} {@code Entity}.
	 * <p>
	 * <li>should not be called directly, but through the {@code deleteCompany}
	 * Lambda Expression to avoid exceptions.</li>
	 * <p>
	 * 
	 * @param coupons
	 * @param size
	 * @param companyId
	 * @see generalUtil#StringClass
	 */
	private void companyDeleterSynchronizer(List<Coupon> coupons, int size, long companyId) {
		synchronized (StringClass.COUPON_ID_SYNC + coupons.get(size - 1).getId()) {
			if (--size != 0) {
				companyDeleterSynchronizer(coupons, size, companyId);
				return;
			}
			couponRepo.findByCompanyId(companyId).stream().forEach(couponStream -> {
				couponStream.setCustomers(null);
				couponRepo.save(couponStream);
			});
			companyRepo.deleteById(companyId);
		}
	}

	/**
	 * Helper method, checks if a {@code Customer} {@code Entity} with the same
	 * {@code email} already exists in the system.
	 * 
	 * @param customers
	 * @param customer
	 * @throws CustomerDuplicateValueException if a {@code Customer} {@code Entity}
	 *                                         with the same {@code email} already
	 *                                         exists in the system.
	 * @throws CustomerNullValueException      if the {@code Customer}
	 *                                         {@code Entity} has any {@code null}
	 *                                         values in it.
	 * @throws CustomerIsNullException         if the {@code Customer} value is
	 *                                         {@code null}.
	 * @see #customerNullValidation(Customer)
	 */
	private void checkCustomer(Customer customer)
			throws CustomerIsNullException, CustomerNullValueException, CustomerDuplicateValueException {
		customerNullValidation(customer);
		if (customerRepo.findByIdNotAndEmailIgnoreCase(customer.getId(), customer.getEmail()).isPresent())
			throw new CustomerDuplicateValueException(customer.getEmail());
	};

	/**
	 * Ensures that there is an existing {@code User} {@code Entity} of an admin
	 * type , at the server startup, uses the default admin {@code email} and
	 * {@code password} if there isn't an admin {@code Entity} currently in the
	 * data-base.
	 * 
	 * @see serviceUtil#AdminUtil
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (adminRepo.findAll().isEmpty()) {
			adminRepo.save(new User(AdminUtil.ADMIN_USER.toString(), AdminUtil.ADMIN_PASS.toString()));
		}
	}

}
