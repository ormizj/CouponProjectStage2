package com.jbc.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jbc.model.Coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code Company} {@code Entity} that is using the {@link User} {@code Entity},
 * and the {@link Coupon} Entity}.
 * <p>
 * This {@code Entity} used for the creating the companies in the system.
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
@Table(name = "companies")
public class Company extends User {

	/* attributes */
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Coupon> coupons;

	@Column(nullable = false)
	private String name;

	/* constructor */
	public Company(String name, String email, String password) {
		coupons = new ArrayList<Coupon>();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	/* toString */
	@Override
	public String toString() {
		String string = "Company(id=" + id + ", name=" + name + ", email=" + email + ", password=" + password;
		if (coupons.isEmpty())
			return string + ", coupons=None)";
		return string + ", coupons=" + coupons + ")";
	}

}
