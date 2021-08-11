package com.jbc.util.exceptionUtil;

/**
 * {@code enum} which contains the {@code errorCode} of every {@link com.jbc.exception.CustomException}.
 * <p>
 * <li>The method {@code toDouble} should be used after specifying which
 * information is wanted.</li>
 * <p>
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see exception#CustomException
 * @see #toString()
 */
public enum ExceptionErrorCodeUtil {

	CompanyAlreadyExistsException(100.001), CompanyDuplicateValueException(100.002), CompanyIsEmptyException(100.003),
	CompanyIsNullException(100.004), CompanyNotFoundException(100.005), CompanyNullValueException(100.006),

	CouponAlreadyExistsException(101.001), CouponDuplicateValueException(101.002),
	CouponExpiredModifyException(101.003), CouponExpiredPurchaseException(101.004), CouponIsEmptyException(101.005),
	CouponIsNullException(101.006), CouponModifyNoStockException(101.007), CouponNegativePriceException(101.008),
	CouponNotFoundException(101.009), CouponNullValueException(101.010), CouponOwnedException(101.011),
	CouponPurchaseNoStockException(101.012),

	CustomerAlreadyExistsException(102.001), CustomerDuplicateValueException(102.002),
	CustomerIsEmptyException(102.003), CustomerIsNullException(102.004), CustomerNotFoundException(102.005),
	CustomerNullValueException(102.006),
	
	LoggerIsEmptyException(103.001),
	
	InvalidLoginException(104.001),NullLoginException(104.002);

	private final double ERROR_CODE;

	ExceptionErrorCodeUtil(double errorCode) {
		ERROR_CODE = errorCode;
	}

	public double toDouble() {
		return ERROR_CODE;
	}

}
