package com.jbc.model;

import java.sql.Date;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jbc.util.generalUtil.TimeZoneUtil;
import com.jbc.util.modelUtil.ModelEntityUtil;
import com.jbc.util.serviceUtil.ModelActionUtil;
import com.jbc.util.serviceUtil.UserTypeUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code Logger} {@code Entity} that is used for creating logs in the system,
 * the logs time is based on a timezone.
 * <p>
 * <li>Timezone is based off the {@link com.jbc.util.generalUtil.TimeZoneUtil}
 * {@code enum}.</li>
 * <p>
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see generalUtil#TimeZoneUtil
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "logs")
public class Logger {

	/* attributes */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ModelEntityUtil entity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ModelActionUtil action;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserTypeUtil executiveEntity;

	private long executiveId;
	private long entityId;
	private String oldAttributes;
	private String newAttributes;
	private Date date;
	private LocalTime time;

	/* constructor */
	public Logger(long executiveId, UserTypeUtil executiveEntity, long entityId, ModelEntityUtil entity,
			ModelActionUtil action, String oldAttributes, String newAttributes) {
		this.executiveId = executiveId;
		this.executiveEntity = executiveEntity;
		this.entityId = entityId;
		this.entity = entity;
		this.action = action;
		this.oldAttributes = oldAttributes;
		this.newAttributes = newAttributes;
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of(TimeZoneUtil.ISRAEL.toString()));
		date = Date.valueOf(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		time = LocalTime.parse(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	}

}
