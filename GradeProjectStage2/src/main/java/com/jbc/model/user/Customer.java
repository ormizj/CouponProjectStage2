package com.jbc.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.jbc.model.Coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code Customer} {@code Entity} that is using the {@link User}
 * {@code Entity}, and the {@link Coupon} {@code Entity}.
 * <p>
 * This {@code Entity} used for the creating the customers in the system.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see user#User
 * @see model#Coupon
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends User {

	/* attributes */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "customersVsCoupons", joinColumns = @JoinColumn(name = "customerId"), inverseJoinColumns = @JoinColumn(name = "couponId"))
	private List<Coupon> coupons;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;

	/* constructor */
	public Customer(String firstName, String lastName, String email, String password) {
		coupons = new ArrayList<>();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	/* toString */
	@Override
	public String toString() {
		String string = "Customer(id=" + id + ", first name=" + firstName + ", last name=" + lastName + ", email="
				+ email + ", password=" + password;
		if (coupons.isEmpty())
			return string + ", coupons=None)";
		return string + ", coupons=" + coupons + ")";
	}

}
