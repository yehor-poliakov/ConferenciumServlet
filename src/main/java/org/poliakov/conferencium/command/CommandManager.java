package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper to work with servlet commands that maps urls to commands
 */
public class CommandManager {

    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);

    private HashMap<String, ServletCommand> getCommands;
    private HashMap<String, ServletCommand> postCommands;

    public CommandManager() {
        LOGGER.info("Starting CommandManager");

        getCommands = new HashMap<>();
        postCommands = new HashMap<>();

        getCommands.put("/", new GetConferencesPageCommand());
        getCommands.put("/login", new GetLoginPageCommand());
        getCommands.put("/registration", new GetRegistrationPageCommand());
        getCommands.put("/conferences", new GetConferencesPageCommand());
        getCommands.put("/cabinet", new GetCabinetPageCommand());
        getCommands.put("/conference/(\\d+)", new GetConferencePageCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/registration", new RegistrationCommand());
    }

    /**
     * Mapping command instances to get methods
     *
     * @param request http request
     * @return servlet command
     */
    public ServletCommand getGetCommand(HttpServletRequest request) {
        String command = getMapping(request);

        LOGGER.info("Instantiating command " + command);

        for (String key : getCommands.keySet()) {
            if (command.matches(key)) {
                return getCommands.get(key);
            }
        }

        return getCommands.get("/");
    }

    /**
     * Mapping commands to post methods
     *
     * @param request http request
     * @return servlet command
     */
    public ServletCommand getPostCommand(HttpServletRequest request) {
        String command = getMapping(request);

        LOGGER.info("Instantiating command " + command);

        for (String key : postCommands.keySet()) {
            Pattern pattern = Pattern.compile(key);
            Matcher m = pattern.matcher(command);

            if (m.find()) {
                return postCommands.get(key);
            }
        }

        return postCommands.get("/");
    }

    /**
     * Helper method mapping command
     *
     * @param request http request
     * @return Command mapping
     */
    public String getMapping(HttpServletRequest request) {
        String mapping = request.getRequestURI().substring(request.getContextPath().length());

        if (mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }

        return mapping;
    }
}
