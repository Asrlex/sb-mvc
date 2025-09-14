package dev.api.springmvc.common.audit;

public class BaseQueries {
	public static final String FIND_ALL_INCLUDE_DELETED = "SELECT e FROM #{#entityName} e";
	public static final String FIND_BY_ID_INCLUDE_DELETED = "SELECT e FROM #{#entityName} e WHERE e.id = :id";
	public static final String RESTORE_BY_ID = "UPDATE #{#entityName} e SET e.deleted_at = NULL, e.deleted_by = NULL WHERE e.id = :id";
}
