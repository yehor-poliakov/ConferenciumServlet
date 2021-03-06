package org.poliakov.conferencium.command;

import org.poliakov.conferencium.model.user.UserRole;

public abstract class ModeratorServletCommand extends RestrictedServletCommand {
    @Override
    protected UserRole[] getAllowedRoles() {
        return new UserRole[] { UserRole.MODERATOR };
    }
}
