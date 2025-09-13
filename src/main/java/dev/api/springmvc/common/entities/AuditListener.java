package dev.api.springmvc.common.entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class AuditListener {
	@PrePersist
	public void setCreatedAt(Auditable entity) {
		Instant now = Instant.now();
		entity.setCreatedAt(now);
		entity.setUpdatedAt(now);
		entity.setUpdatedBy(entity.getCreatedBy().orElse("system"));
		entity.setCreatedBy(entity.getCreatedBy().orElse("system"));
	}

	@PreUpdate
	public void setUpdatedAt(Auditable entity) {
		entity.setUpdatedAt(Instant.now());
		entity.setUpdatedBy(entity.getUpdatedBy().orElse("system"));
	}
}
