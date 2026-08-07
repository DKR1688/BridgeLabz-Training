package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "MyFirstServlet", urlPatterns = {"/myfirst", "/validate"})
public class MyFirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>My First Servlet</title></head>");
        out.println("<body>");
        out.println("<h2>My First Servlet</h2>");
        out.println("<p>Servlet demo from Day-5 assignment.</p>");
        out.println("<form method='post' action='myfirst'>");
        out.println("<label>Name:</label><input type='text' name='name' required /><br/>");
        out.println("<label>Password:</label><input type='password' name='password' required /><br/>");
        out.println("<button type='submit'>Submit</button>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> payload = new LinkedHashMap<>();

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        payload.put("status", "success");
        payload.put("message", "Servlet received the request");
        payload.put("name", name);
        payload.put("passwordLength", password == null ? 0 : password.length());

        boolean validName = isValidName(name);
        boolean validPassword = isValidPassword(password);

        if (!validName) {
            payload.put("status", "error");
            payload.put("message", "invalid name");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (!validPassword) {
            payload.put("status", "error");
            payload.put("message", "invalid password");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            payload.put("message", "Validation successful");
            response.setStatus(HttpServletResponse.SC_OK);
        }

        PrintWriter out = response.getWriter();
        out.write(toJson(payload));
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("[A-Z][a-zA-Z]{2,}");
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        long specialCharacterCount = password.chars()
                .filter(character -> !Character.isLetterOrDigit(character))
                .count();
        return hasUpper && hasDigit && specialCharacterCount == 1;
    }

    private String toJson(Map<String, Object> data) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) {
                builder.append(",");
            }
            first = false;
            builder.append('"').append(entry.getKey()).append('"').append(':');
            Object value = entry.getValue();
            if (value instanceof String) {
                builder.append('"').append(value.toString().replace("\"", "\\\"")).append('"');
            } else {
                builder.append(value);
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
