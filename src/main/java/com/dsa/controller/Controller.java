package com.dsa.controller;

import com.dsa.service.command.ActionCommand;
import com.dsa.service.ActionFactory;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet({"/api/v1/*"})
//@WebServlet(name="MainServlet", displayName="Main servlet", urlPatterns={"/", "/index", "/controller"})
public class Controller extends HttpServlet {
  private static final Logger log = Logger.getLogger(Controller.class);
  private static final String PATH_PAGE_INDEX;
  private static final String NULL_PAGE_MESSAGE;

  static{
    PATH_PAGE_INDEX=ConfigManager.getProperty("path.page.index");
    NULL_PAGE_MESSAGE=MessageManager.getProperty("message.nullPage");
    // initialization of DbCreator
    try{
      Class.forName("com.dsa.dao.services.DbCreator");
    }catch(ClassNotFoundException e){
      log.error("Fail to initialize DbCreator: "+e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String page = null;
    ProxyRequest proxyRequest= new ProxyRequest(request);
    ActionCommand command = ActionFactory.defineCommand(proxyRequest);
    page=command.execute(proxyRequest);
    if(page != null){
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
      dispatcher.forward(request, response);
    }else{
      page = PATH_PAGE_INDEX;
      request.getSession().setAttribute("nullPage", NULL_PAGE_MESSAGE);
      response.sendRedirect(request.getContextPath() + page);
    }
  }
}
