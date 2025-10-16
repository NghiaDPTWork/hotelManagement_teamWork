/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.hotel_management.presentation.controller;

import edu.hotel_management.presentation.constants.IConstant;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TR_NGHIA
 * 
 */

public class HomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String url = IConstant.ACTION_HOME;
        
        try {
            String action = request.getParameter("action");
            
            if (action == null || action.isEmpty()) {
                url = IConstant.PAGE_HOME;
            } else {
                switch (action) {
                    case IConstant.LOGIN:
                        url = IConstant.ACTION_LOGIN;
                        System.out.println(action);
                        break;
                    case IConstant.LOGOUT:
                        url = IConstant.ACTION_LOGOUT;
                        break;
                    case IConstant.REGISTER:
                        url = IConstant.ACTION_REGISTER;
                        break;
                    case IConstant.VIEW_PROFILE:
                        url = IConstant.ACTION_VIEW_PROFILE;
                        break;
                    case IConstant.VIEW_ROOM_BOOKING:
                        url = IConstant.ACTION_VIEW_ROOM_BOOKING;
                        break;
                    case IConstant.VIEW_HISTORY_ROOM_BOOKING: 
                        url = IConstant.ACTION_VIEW_HISTORY_BOOKING;
                        break;
                    case IConstant.BOOKING: 
                        url = IConstant.ACTION_BOOKING;
                        break;
                    
                }
            }
        } catch (Exception e) {
            log("Error at HomeController: " + e.toString());
        } finally {
            try {
                System.out.println(url);
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Home Front Controller";
    }
}
