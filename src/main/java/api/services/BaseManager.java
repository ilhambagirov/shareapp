package api.services;

import api.entities.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseManager {

    public long userId;

    public AppUser getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                AppUser userDetails = (AppUser) authentication.getPrincipal();
                userId = userDetails.getId();
                return userDetails;
            }
        }

        return null;
    }
}