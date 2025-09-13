package dev.api.springmvc.common.filter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter for logging web requests and responses.
 */
@Component
@Order(1)
public class RequestLoggingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

	/**
	 * Logs request and response details including method, URI, status, lengths, and processing time.
	 * Also assigns a unique request ID for tracing.
	 * @param request - the HTTP request
	 * @param response - the HTTP response
	 * @param chain - the filter chain
	 * @throws ServletException - if a servlet error occurs
	 * @throws IOException - if an I/O error occurs
	 */
	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String requestId = UUID.randomUUID().toString();
		MDC.put("requestId", requestId);
		response.addHeader("X-Request-Id", requestId);

		ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);

		long start = System.currentTimeMillis();
		try {
			chain.doFilter(req, res);
		} finally {
			long ms = System.currentTimeMillis() - start;
			byte[] reqBody = req.getContentAsByteArray();
			byte[] resBody = res.getContentAsByteArray();

			String paddedUri = String.format("%-20s", request.getRequestURI());
			log.info("===REQ=== [{}] {} {}", requestId, request.getMethod(), paddedUri);
			log.info(
					"===RES=== [{}] [{}] len={}B in {}ms",
					requestId,
					res.getStatus(),
					resBody.length,
					ms
			);

			res.copyBodyToResponse();

			MDC.clear();
		}
	}
}
