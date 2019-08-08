package com.hbc.chatroom.Controller;


import com.hbc.chatroom.config.FreeMarkerListener;
import com.hbc.chatroom.service.AccountService;
import com.hbc.chatroom.untils.Commutils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Beer
 * @Date: 2019/8/8 15:05
 * @Description:
 */

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private AccountService accountService = new AccountService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf8");
        PrintWriter out = resp.getWriter();


        if (Commutils.strIsNull(username) || Commutils.strIsNull(password)) {
            //输入错误，保留在登录页面
            out.println("<script>\n" +
                    "    alert(\"用户名或密码为空，请重新输入...\")\n" +
                    "    window.location.href = \"/index.html\";\n" +
                    "</script>");
        }
        if (accountService.userLogin(username,password)) {
            //登录成功,进入聊天页面
            //加载chat.ftl
            Template template = getTemplate(req,"/chat.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("username", username);
            try {
                template.process(map,out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            //out.println("***********");
        }else {
            //登录失败
            out.println("<script>\n" +
                    "    alert(\"用户名或密码错误，请重新输入...\")\n" +
                    "    window.location.href = \"/index.html\";\n" +
                    "</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    private Template getTemplate(HttpServletRequest req, String filaName) {
        Configuration cfg =
                (Configuration) req.getServletContext().getAttribute(FreeMarkerListener.TEMPLATE_KEY);
        try {
            return cfg.getTemplate(filaName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
