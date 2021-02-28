package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Optional;
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
        getCommands.put("/conference/create", new GetCreateConferencePageCommand());
        getCommands.put("/conference/(\\d+)", new GetConferencePageCommand());
        getCommands.put("/conference/(\\d+)/edit", new GetEditConferencePageCommand());
        getCommands.put("/conference/(\\d+)/delete", new GetDeleteConferencePageCommand());
        getCommands.put("/presentation/create/conference/(\\d+)", new GetCreatePresentationPageCommand());
        getCommands.put("/presentation/suggest/conference/(\\d+)", new GetSuggestPresentationPageCommand());
        getCommands.put("/presentation/(\\d+)/edit/conference/(\\d+)", new GetEditPresentationPageCommand());
        getCommands.put("/cabinet", new GetCabinetPageCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/logout", new LogoutCommand());
        postCommands.put("/conference/create", new CreateConferenceCommand());
        postCommands.put("/conference/(\\d+)/edit", new EditConferenceCommand());
        postCommands.put("/conference/(\\d+)/delete", new DeleteConferenceCommand());
        postCommands.put("/presentation/create/conference/(\\d+)", new CreatePresentationCommand());
        postCommands.put("/presentation/(\\d+)/edit/conference/(\\d+)", new EditPresentationCommand());
        postCommands.put("/presentation/suggest/conference/(\\d+)", new SuggestPresentationCommand());
        postCommands.put("/registration", new RegistrationCommand());
        postCommands.put("/conference/(\\d+)/signup", new ConferenceRegistrationCommand());
        postCommands.put("/conference/(\\d+)/signout", new ConferenceUnregistrationCommand());
    }

    /**
     * Mapping command instances to get methods
     *
     * @param request http request
     * @return servlet command
     */
    public Optional<ServletCommandInfo> getGetCommand(HttpServletRequest request) {
        return getCommandInfo(request, getCommands);
    }

    /**
     * Mapping commands to post methods
     *
     * @param request http request
     * @return servlet command
     */
    public Optional<ServletCommandInfo> getPostCommand(HttpServletRequest request) {
        return getCommandInfo(request, postCommands);
    }

    private Optional<ServletCommandInfo> getCommandInfo(HttpServletRequest request, HashMap<String,
                                                        ServletCommand> commands) {
        String command = getMapping(request);

        LOGGER.info("Instantiating command " + command);

        for (String key : commands.keySet()) {
            Pattern pattern = Pattern.compile("^" + key + "$");
            Matcher matcher = pattern.matcher(command);

            if (matcher.find()) {
                ServletCommand servletCommand = commands.get(key);
                String[] params = extractParams(matcher);
                ServletCommandInfo result = new ServletCommandInfo(servletCommand, params);
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    private String[] extractParams(Matcher matcher) {
        String[] params = new String[matcher.groupCount()];
        for (int i = 0; i < matcher.groupCount(); i++) {
            params[i] = matcher.group(i + 1);
        }
        return params;
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


