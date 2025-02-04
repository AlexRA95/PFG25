package es.abatech.controllers;

import es.abatech.DAO.IUsuariosDAO;
import es.abatech.DAOFactory.DAOFactory;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet para verificar si un correo electr&oacute;nico ya est&aacute; en uso utilizando AJAX.
 */
@WebServlet(name = "CorreoEnUsoAJAX", value = "/CorreoEnUsoAJAX")
public class CorreoEnUsoAJAX extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder requestBody = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        JSONObject jsonRequest = new JSONObject(requestBody.toString());
        String email = jsonRequest.getString("EMAIL");

        DAOFactory daof = DAOFactory.getDAOFactory();
        IUsuariosDAO udao = daof.getUsuariosDAO();
        Boolean correoEnUso = udao.correoEnUso(email);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("correoEnUso", correoEnUso);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonResponse.toString());
    }
}