package org.poliakov.conferencium.command;

import org.poliakov.conferencium.model.user.UserRole;

public abstract class UserServletCommand extends RestrictedServletCommand {
    @Override
    protected UserRole[] getAllowedRoles() {
        return new UserRole[] { UserRole.PARTICIPANT, UserRole.SPEAKER, UserRole.MODERATOR };
    }
}
