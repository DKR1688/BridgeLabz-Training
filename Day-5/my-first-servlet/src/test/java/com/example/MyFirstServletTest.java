package com.example;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyFirstServletTest {

    @Test
    void doPostReturnsJsonForValidInput() throws Exception {
        MyFirstServlet servlet = new MyFirstServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter output = new StringWriter();

        when(request.getServletPath()).thenReturn("/myfirst");
        when(request.getParameter("name")).thenReturn("Deepak");
        when(request.getParameter("password")).thenReturn("Abc123!x");
        when(response.getWriter()).thenReturn(new PrintWriter(output));

        servlet.doPost(request, response);

        String body = output.toString();
        assertTrue(body.contains("Validation successful"));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void passwordWithTwoSpecialCharactersIsRejected() throws Exception {
        MyFirstServlet servlet = new MyFirstServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter output = new StringWriter();

        when(request.getServletPath()).thenReturn("/validate");
        when(request.getParameter("name")).thenReturn("Deepak");
        when(request.getParameter("password")).thenReturn("Abc12!@x");
        when(response.getWriter()).thenReturn(new PrintWriter(output));

        servlet.doPost(request, response);

        String body = output.toString();
        assertTrue(body.contains("invalid password"));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void predefinedLoginForwardsToSuccessJsp() throws Exception {
        LoginServlet servlet = new LoginServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("Admin@123");
        when(request.getRequestDispatcher("/LoginSuccess.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("username", "admin");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void invalidNameIsRejected() throws Exception {
        MyFirstServlet servlet = new MyFirstServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter output = new StringWriter();

        when(request.getServletPath()).thenReturn("/validate");
        when(request.getParameter("name")).thenReturn("deepak");
        when(request.getParameter("password")).thenReturn("Abc123!x");
        when(response.getWriter()).thenReturn(new PrintWriter(output));

        servlet.doPost(request, response);

        String body = output.toString();
        assertTrue(body.contains("invalid name"));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void weakPasswordIsRejected() throws Exception {
        MyFirstServlet servlet = new MyFirstServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter output = new StringWriter();

        when(request.getServletPath()).thenReturn("/validate");
        when(request.getParameter("name")).thenReturn("Deepak");
        when(request.getParameter("password")).thenReturn("abc123");
        when(response.getWriter()).thenReturn(new PrintWriter(output));

        servlet.doPost(request, response);

        String body = output.toString();
        assertTrue(body.contains("invalid password"));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
