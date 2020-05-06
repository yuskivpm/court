package com.dsa.view.http;

import com.dsa.controller.Controller;
import com.dsa.controller.ControllerRequest;
import com.dsa.service.Initialization;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.resource.ConfigManager;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Servlet filter for entry
 * Verifies that the user has been authenticated,
 * otherwise redirect ршь to the login page
 * <p>
 * accept all URL patterns
 * <p>
 * Login page - LOGIN_PAGE
 * Login request - CHECK_LOGIN_REQUEST
 * login process page - LOGIN_PROCESS_PAGE
 */

@WebFilter(
    urlPatterns = "/*",
    dispatcherTypes = {DispatcherType.REQUEST}
)
public class MainServletFilter implements Filter {

  private static final Logger LOG = Logger.getLogger(MainServletFilter.class);
  private static final String LOGIN_PAGE;

  static {
    LOGIN_PAGE = ConfigManager.getProperty("path.page.login");
    try {
      Initialization.initialize();
    } catch (Exception e) {
      LOG.error("Fail to initialize some classes in MainServletFilter: " + e);
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    ControllerRequest controllerRequest = HttpToControllerConverter.prepareRequestDataForController(req, false);
    String path = req.getRequestURI();
    if (!path.equals(LOGIN_PAGE) &&
        !controllerRequest.getParameter(Controller.COMMAND).toLowerCase().equals(LoginCommand.path)) {
      // all requests except "/jsp/login.jsp" check for prior login
      String UserSession = LoginCommand.getUserSessionId(controllerRequest);
      if (UserSession.isEmpty()) {
        // unauthorized user - redirect to "/jsp/login.jsp"
        req.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        return;
      }
    }
    // save current user for jsp with redirected forms
    request.setAttribute("curUser", LoginCommand.getSessionUser(controllerRequest));
    chain.doFilter(request, response);
  }

}
