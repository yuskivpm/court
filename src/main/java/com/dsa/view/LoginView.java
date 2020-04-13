package com.dsa.view;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.UserDao;
import com.dsa.model.User;

import java.util.List;

public class LoginView {
  public void setAttributes(ProxyRequest request, User user){
    switch (user.getRole()){
      case ADMIN:
//        todo: LoginView.setAttributes.ADMIN

//        List<User> users = new UserDao().getAll();
//        request.setAttribute("");
// edit main tables
// // regions
// // courts
        break;
      case JUDGE:
//        todo: LoginView.setAttributes.JUDGE
// see new Sue
// // initiate Lawsuit
// see own Lawsuits
// // make Verdict (accept / decline)

        break;
      case ATTORNEY:
//        todo: LoginView.setAttributes.ATTORNEY
// initiate new Sue
// see own Lawsuits and its Verdicts
// see all public Verdicts
        break;
      default:
//        todo: LoginView.setAttributes.GUEST
//
    }
  }
}
