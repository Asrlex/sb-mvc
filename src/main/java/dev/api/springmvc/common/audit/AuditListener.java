package dev.api.springmvc.common.audit;

import dev.api.springmvc.common.entities.StandardParameters;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class AuditListener {
	@PrePersist
	public void setInitialFields(AuditableEntity entity) {
		Instant now = Instant.now();
		entity.setCreatedAt(now);
		entity.setUpdatedAt(now);
		entity.setCreatedBy(entity.getCreatedBy());
		entity.setUpdatedBy(entity.getCreatedBy());
	}

	@PreUpdate
	public void setUpdateFields(AuditableEntity entity) {
		entity.setUpdatedAt(Instant.now());
		entity.setUpdatedBy(entity.getUpdatedBy().orElse(StandardParameters.SYSTEM_USER));
	}
}
