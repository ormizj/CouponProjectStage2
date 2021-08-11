package com.jbc.util.systemUtil.couponExpirationDailyJobUtil;

/**
 * {@code enum} which contains infromation related to the
 * {@link com.jbc.system.CouponExpirationDailyJob} {@code class}
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see system#CouponExpirationDailyJob
 */
public enum CouponExpirationDailyJobPathUtil {

	/* attributes */
	CLASS_NAME, CLASS_PATH;

	/* toString */
	@Override
	public String toString() {
		switch (this) {
		case CLASS_NAME:
			return "CouponExpirationDailyJob";
		case CLASS_PATH:
			return "com.jbc.system." + CouponExpirationDailyJobPathUtil.CLASS_NAME.toString();
		default:
			return super.toString();
		}
	}

}
