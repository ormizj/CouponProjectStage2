package com.jbc.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jbc.model.user.Company;
import com.jbc.model.user.Customer;
import com.jbc.util.modelUtil.CategoryUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code Coupon} {@code Entity} that is used by the {@link Company}
 * {@code Entity}, and the {@link Customer} {@code Entity}.
 * <p>
 * This {@code Entity} used for the creating the coupons in the system.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see user#Company
 * @see user#Customer
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table (name = "coupons")
public class Coupon {

	/* attributes */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoryUtil category;

	@ManyToOne
	private Company company;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "customersVsCoupons", joinColumns = @JoinColumn(name = "couponId"), inverseJoinColumns = @JoinColumn(name = "customerId"))
	private List<Customer> customers;

	@Column(nullable = false)
	private Date startDate;
	@Column(nullable = false)
	private Date endDate;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	private int amount;
	private double price;
	@Column(nullable = false)
	private String image;

	/* constructor */
	public Coupon(CategoryUtil category, String title, String description, Date startDate, Date endDate, int amount,
			double price, String image) {
		customers = new ArrayList<>();
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	/* toString */
	@Override
	public String toString() {
		return "Coupon(id=" + id + ", category=" + category + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", title=" + title + ", description=" + description + ", amount=" + amount + ", price=" + price
				+ ", image=" + image + ", companyId=" + company.getId() + ")";
	}

}
