package dev.api.springmvc.common.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import java.time.Instant;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditableEntity implements IAuditable<String, Instant> {

	@Column(nullable = false, updatable = false)
	private Instant created_at;

	@Column(nullable = false)
	private String created_by;

	@Column(nullable = false)
	private Instant updated_at;

	@Column(nullable = false)
	private String updated_by;

	@Column
	private Instant deleted_at;

	@Column
	private String deleted_by;

	@Override
	public String getCreatedBy() { return created_by; }
	@Override
	public void setCreatedBy(String created_by) { this.created_by = created_by; }

	@Override
	public Instant getCreatedAt() { return created_at; }
	@Override
	public void setCreatedAt(Instant created_at) { this.created_at = created_at; }

	@Override
	public Optional<String> getUpdatedBy() { return Optional.ofNullable(updated_by); }
	@Override
	public void setUpdatedBy(String updated_by) { this.updated_by = updated_by; }

	@Override
	public Optional<Instant> getUpdatedAt() { return Optional.ofNullable(updated_at); }
	@Override
	public void setUpdatedAt(Instant updated_at) { this.updated_at = updated_at; }

	@Override
	public Optional<String> getDeletedBy() { return Optional.ofNullable(deleted_by); }
	@Override
	public void setDeletedBy(String deleted_by) { this.deleted_by = deleted_by; }

	@Override
	public Optional<Instant> getDeletedAt() { return Optional.ofNullable(deleted_at); }
	@Override
	public void setDeletedAt(Instant deleted_at) { this.deleted_at = deleted_at; }
}

