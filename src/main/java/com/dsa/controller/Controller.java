package com.dsa.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.dsa.service.command.ActionCommand;
import com.dsa.service.ActionFactory;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

@WebServlet({"/controller"})
//@WebServlet(name="MainServlet", displayName="Main servlet", urlPatterns={"/", "/index", "/controller"})
public class Controller extends HttpServlet {

  static{
//    create in-memory db
    try{
      Class.forName("com.dsa.dao.services.DbCreator");
    }catch(ClassNotFoundException e){
//      log.error(e);
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
    ActionFactory client = new ActionFactory();
    ActionCommand command = client.defineCommand(proxyRequest);
    page=command.execute(proxyRequest);
    if(page != null){
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
      dispatcher.forward(request, response);
    }else{
      page = ConfigManager.getProperty("path.page.index");
      request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullPage"));
      response.sendRedirect(request.getContextPath() + page);
    }
  }
}
