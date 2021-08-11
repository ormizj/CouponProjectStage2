package com.jbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.jbc.system.Test;

/**
 * {@code class} that runs the {@code testAll} method in the {@link Test}
 * {@code Component}.
 * <p>
 * <li>Starts the <code>CouponExpirationDailyJob</code> at the start of the
 * system and stops it at the termination of the system.</li>
 * <p>
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see system#Test
 */
@SpringBootApplication
public class GradeProjectStage2Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(GradeProjectStage2Application.class, args);

		context.getBean(Test.class).testAll();	

		((ConfigurableApplicationContext) context).close();
	}

}
