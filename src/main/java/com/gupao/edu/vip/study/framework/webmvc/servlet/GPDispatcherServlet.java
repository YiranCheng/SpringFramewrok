package com.gupao.edu.vip.study.framework.webmvc.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yiran
 */
public class GPDispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException{

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
