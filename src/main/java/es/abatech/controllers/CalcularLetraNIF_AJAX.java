package es.abatech.controllers;
import es.abatech.models.Utils;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet para calcular la letra del NIF utilizando AJAX.
 */
@WebServlet(name = "CalcularLetraNIF_AJAX", value = "/CalcularLetraNIF_AJAX")
public class CalcularLetraNIF_AJAX extends HttpServlet {
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
        String nif = jsonRequest.getString("NIF");

        Character letra = Utils.calcularLetraNIF(nif);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("Letra", letra);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonResponse.toString());
    }
}