package cz.kostka.pochod.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public final class LoginUtils {

    private LoginUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static String getRedirectUrlAfterLoginForRole(final Collection<? extends GrantedAuthority> role) {
        switch (role.toString()) {
            case "[ROLE_ADMIN]":
                return "redirect:/admin";
            case "[ROLE_ORGANIZER]":
                return "redirect:/organizer/progress";
            case "[ROLE_USER]":
                return "redirect:/pop";
            default:
                return "redirect:/login";
        }
    }
}
