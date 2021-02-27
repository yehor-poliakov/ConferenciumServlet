package org.poliakov.conferencium.command;

public class ServletCommandInfo {
    private ServletCommand command;
    private String[] params;

    public ServletCommandInfo(ServletCommand command, String[] params) {
        this.command = command;
        this.params = params;
    }

    public ServletCommand getCommand() {
        return command;
    }

    public String[] getParams() {
        return params;
    }
}
