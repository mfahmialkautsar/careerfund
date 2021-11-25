package id.careerfund.api.domains;

public enum ERole {
    ROLE_ADMIN(Constants.ADMIN),
    ROLE_USER(Constants.USER),
    ROLE_LENDER(Constants.LENDER),
    ROLE_BORROWER(Constants.BORROWER);

    ERole(String roleString) {
    }

    public static class Constants {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
        public static final String LENDER = "ROLE_LENDER";
        public static final String BORROWER = "ROLE_BORROWER";
    }
}
