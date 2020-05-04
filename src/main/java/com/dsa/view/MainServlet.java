package com.dsa.view;

//import org.apache.log4j.Logger;

import com.dsa.controller.Controller;
import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

@WebServlet({"/api/*"})
//@WebServlet(name="MainServlet", displayName="Main servlet", urlPatterns={"/", "/index", "/controller"})
public class MainServlet extends HttpServlet {

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

  private void processRequest(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ControllerResponse controllerResponse = Controller.execute(
        HttpToControllerConverter.prepareRequestDataForController(request, true)
    );
    controllerResponse.getAttributes().forEach(request::setAttribute);
    controllerResponse.getSessionAttributes().forEach(request.getSession()::setAttribute);
    switch (controllerResponse.getResponseType()) {
      case FORWARD:
        getServletContext().getRequestDispatcher(controllerResponse.getResponseValue()).forward(request, response);
        break;
      case PLAIN_TEXT:
        PrintWriter out = response.getWriter();
        out.print(controllerResponse.getResponseValue());
        break;
      case INVALIDATE:
        request.setAttribute("user", "");
        request.getSession().invalidate();
      case REDIRECT:
      default:
        response.sendRedirect(request.getContextPath() + controllerResponse.getResponseValue());
        break;
    }
  }

}
