package dev.api.springmvc.common.entities;

public class SearchCriteria {

	public record Filter(String field, SqlParameters.SqlOperator operator, Object value) {}

	public record Sorter(String field, SqlParameters.SqlOrder operator, Object value) {}

	private Filter[] filters;
	private Sorter[] sorters;
	private Integer page;
	private Integer pageSize;

	public SearchCriteria() {
		super();
	}

	public SearchCriteria(Filter[] filters, Sorter[] sorters, Integer page, Integer pageSize) {
		super();
		this.filters = filters;
		this.sorters = sorters;
		this.page = page;
		this.pageSize = pageSize;
	}

	public Filter[] getFilters() {
		return filters;
	}
	public void setFilters(Filter[] filters) {
		this.filters = filters;
	}
	public Sorter[] getSorters() {
		return sorters;
	}
	public void setSorters(Sorter[] sorters) {
		this.sorters = sorters;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
