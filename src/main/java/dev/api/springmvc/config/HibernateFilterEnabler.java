package dev.api.springmvc.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HibernateFilterEnabler {
	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	@Transactional
	public void enableFilters() {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedFilter");
		filter.setParameter("isDeleted", false);
	}
}
