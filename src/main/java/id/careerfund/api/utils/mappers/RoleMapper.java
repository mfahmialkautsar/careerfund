package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.ERoleRegister;

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
}
