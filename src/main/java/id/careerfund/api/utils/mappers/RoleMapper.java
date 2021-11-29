package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.ERoleRegister;
import id.careerfund.api.domains.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

public final class RoleMapper {
    public static ERole mapRole(ERoleRegister roleRegister) {
        switch (roleRegister) {
            case LENDER:
                return ERole.ROLE_LENDER;
            case BORROWER:
                return ERole.ROLE_BORROWER;
            default:
                return null;
        }
    }

    public static Collection<ERole> rolesToERoles(Collection<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
