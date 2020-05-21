package com.dsa.view.http;

import com.dsa.controller.ControllerRequest;

import com.dsa.controller.IController;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet({"/api/*"})
public class MainServlet extends HttpServlet {

  private static IController controller;

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

  private void processRequest(
      @NotNull HttpServletRequest request,
      HttpServletResponse response
  ) throws ServletException, IOException {
    ControllerRequest controllerRequest = controller.execute(
        HttpToControllerConverter.prepareRequestDataForController(request, true)
    );
    controllerRequest.getAttributes().forEach(request::setAttribute);
    controllerRequest.getSessionAttributes().forEach(request.getSession()::setAttribute);
    switch (controllerRequest.getResponseType()) {
      case FORWARD:
        getServletContext().getRequestDispatcher(controllerRequest.getResponseValue()).forward(request, response);
        break;
      case PLAIN_TEXT:
        response.getWriter().print(controllerRequest.getResponseValue());
        break;
      case INVALIDATE:
        request.setAttribute("user", "");
        request.getSession().invalidate();
      case REDIRECT:
      default:
        response.sendRedirect(request.getContextPath() + controllerRequest.getResponseValue());
        break;
    }
  }

  public static void setController(IController controller){
    MainServlet.controller = controller;
  }

}
