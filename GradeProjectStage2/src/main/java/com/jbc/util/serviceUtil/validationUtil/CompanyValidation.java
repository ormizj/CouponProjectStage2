package com.jbc.util.serviceUtil.validationUtil;

import com.jbc.exception.companyException.CompanyIsNullException;
import com.jbc.exception.companyException.CompanyNullValueException;
import com.jbc.model.user.Company;
import com.jbc.util.exceptionUtil.ExceptionUtil;

/**
 * {@code interface} containing {@code default} methods related to the
 * validation of a {@link com.jbc.model.user.Company} {@code Entity}.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see user#Company
 */
public interface CompanyValidation extends StringModifier {

	/**
	 * Method to validate that a {@link com.jbc.model.user.Company} {@code Entity}
	 * is not {@code null}, and doesn't have any {@code null} values.
	 * 
	 * @param company
	 * @throws CompanyNullValueException if the {@code Company} {@code Entity}
	 *                                   contains any values which are null.
	 * @throws CompanyIsNullException    if the {@code Company} {@code Entity} is
	 *                                   {@code null}.
	 */
	public default void companyNullValidation(Company company)
			throws CompanyNullValueException, CompanyIsNullException {
		if (company == null)
			throw new CompanyIsNullException();
		company.setName(trim(company.getName()));
		company.setEmail(removeSpace(company.getEmail()));
		boolean nameException = company.getName() == null || company.getName().isEmpty();
		boolean emailException = company.getEmail() == null || !isEmail(company.getEmail());
		boolean passwordException = company.getPassword() == null || company.getPassword().isEmpty();
		if (nameException || emailException || passwordException) {
			CompanyNullValueException exception = new CompanyNullValueException();
			if (nameException)
				exception.addNull(ExceptionUtil.NAME);
			if (emailException)
				if (company.getEmail() == null)
					exception.addNull(ExceptionUtil.EMAIL);
				else
					exception.addNull(ExceptionUtil.INVALID_EMAIL_FORMAT);
			if (passwordException)
				exception.addNull(ExceptionUtil.PASSWORD);
			throw exception;
		}
	}

}
