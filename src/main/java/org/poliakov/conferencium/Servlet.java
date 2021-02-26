package org.poliakov.conferencium;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.CommandManager;
import org.poliakov.conferencium.command.ServletCommand;
import org.poliakov.conferencium.connection.ConnectionPool;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Servlet.class);

    private CommandManager commandManager;

    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("Starting Servlet");
        commandManager = new CommandManager();

        config.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.info("Processing get request");
        ServletCommand command = commandManager.getGetCommand(request);
        String page = command.execute(request, response);
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");

        ServletCommand command = commandManager.getPostCommand(request);
        String page = command.execute(request, response);
        request.getRequestDispatcher(page).forward(request, response);
    }
}