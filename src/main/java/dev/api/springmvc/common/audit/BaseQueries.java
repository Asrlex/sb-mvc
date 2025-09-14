package dev.api.springmvc.common.audit;

public class BaseQueries {
	public static final String FIND_ALL_INCLUDE_DELETED = "SELECT * FROM #{#entityName}";
	public static final String FIND_BY_ID_INCLUDE_DELETED = "SELECT * FROM #{#entityName} WHERE id = :id";
	public static final String RESTORE_BY_ID = "UPDATE #{#entityName} SET deleted_at = NULL, deleted_by = NULL WHERE id = :id";
}
