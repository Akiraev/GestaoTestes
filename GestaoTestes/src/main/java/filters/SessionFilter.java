package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.SessionContextFactory;

@WebFilter("/*")
public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String uri = ((HttpServletRequest) req).getRequestURI();
		String contextPath = ((HttpServletRequest) req).getContextPath();

		if (!SessionContextFactory.isSessionOpen() && !uri.equals(contextPath + "/faces/login.xhtml")) {
			((HttpServletResponse) res).sendRedirect(contextPath + "/faces/login.xhtml");

		} else {
			chain.doFilter(req, res);

		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
