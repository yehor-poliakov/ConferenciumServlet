package org.poliakov.conferencium;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.CommandManager;
import org.poliakov.conferencium.command.ServletCommand;
import org.poliakov.conferencium.command.ServletCommandInfo;
import org.poliakov.conferencium.properties.PageMappingProperties;

import java.io.*;
import java.util.HashSet;
import java.util.Optional;
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response, boolean isGetCommand) throws IOException, ServletException {
        Optional<ServletCommandInfo> commandInfo = isGetCommand ? commandManager.getGetCommand(request) : commandManager.getPostCommand(request);

        if (!commandInfo.isPresent()) {
            handleRedirect(response, PageMappingProperties.MAIN_PAGE_REDIRECT);
            return;
        }

        ServletCommand command = commandInfo.get().getCommand();
        String[] params = commandInfo.get().getParams();

        String page = command.execute(request, response, params);

        if (page.startsWith("redirect:")) {
            handleRedirect(response, page);
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    private void handleRedirect(HttpServletResponse response, String page) throws IOException {
        String path = page.substring("redirect:".length());
        String redirect = "/ConferenciumServletPoliakov/" + path;
        response.sendRedirect(redirect);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.info("Processing get request");
        processRequest(request, response, true);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");
        processRequest(request, response, false);
    }
}