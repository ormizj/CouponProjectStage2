package com.jbc.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code User} {@code Entity} that is used by the {@link Company}
 * {@code Entity}, and the {@link Customer} {@code Entity}.
 * <p>
 * This {@code Entity} is also used for the creating the admins of the system.
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
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "admins")
public class User {

	/* attributes */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	protected long id;

	@Column(nullable = false)
	protected String email;
	@Column(nullable = false)
	protected String password;

	/* constructor */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
