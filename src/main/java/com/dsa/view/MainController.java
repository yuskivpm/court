package com.dsa.view;

import com.dsa.service.ActionFactory;
import com.dsa.service.command.IActionCommand;
import com.dsa.service.command.RedirectCommand;
import com.dsa.service.crud.CrudExecutor;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

//import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet({"/api/*"})
//@WebServlet(name="MainServlet", displayName="Main servlet", urlPatterns={"/", "/index", "/controller"})
public class MainController extends HttpServlet {

//  private static final Logger log = Logger.getLogger(MainController.class);
  private static final String PATH_PAGE_INDEX;
  private static final String NULL_PAGE_MESSAGE;

  static {
    PATH_PAGE_INDEX = ConfigManager.getProperty("path.page.index");
    NULL_PAGE_MESSAGE = MessageManager.getProperty("message.nullPage");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String page = null;
    ProxyRequest proxyRequest = new ProxyRequest(request, true);
    IActionCommand command = ActionFactory.defineCommand(proxyRequest);
    if (command == null) {// without "command" parameter - crud operation requested
      switch (CrudExecutor.execute(proxyRequest, response)) {
        case EXECUTED:
          return;
        case REDIRECT:
          page = new RedirectCommand().execute(proxyRequest);
          if (page != null) {
            getServletContext().getRequestDispatcher(page).forward(request, response);
            return;
          }
        case FAILED:  // ignore
        default:      // ignore
      }
    } else {// with "command" parameter - forward to specific page
      page = command.execute(proxyRequest);
      if (page != null) {
        getServletContext().getRequestDispatcher(page).forward(request, response);
        return;
      }
    }
    page = PATH_PAGE_INDEX;
    request.getSession().setAttribute("nullPage", NULL_PAGE_MESSAGE);
    response.sendRedirect(request.getContextPath() + page);
  }

}
