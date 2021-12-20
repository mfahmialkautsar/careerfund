package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.reqres.UpdateUser;
import id.careerfund.api.domains.models.requests.UserRegister;
import id.careerfund.api.domains.models.responses.MyProfile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.stream.Collectors;

public final class UserMapper {
    public static User userRegisterToUser(UserRegister userRegister) {
        User user = new User();
        user.setName(userRegister.getName());
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        return user;
    }

    public static User principalToUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    public static UpdateUser principalToUpdateUser(Principal principal) {
        User user = principalToUser(principal);
        UpdateUser updateUser = new UpdateUser();
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setAddress(user.getAddress());
        return updateUser;
    }

    public static UpdateUser userToUpdateUser(User user) {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setAddress(user.getAddress());
        return updateUser;
    }

    public static MyProfile principalToMyProfile(Principal principal) {
        User user = principalToUser(principal);

        MyProfile myProfile = new MyProfile();
        myProfile.setId(user.getId());
        myProfile.setName(user.getName());
        myProfile.setEmail(user.getEmail());
        myProfile.setRoles(user.getRoles());
        myProfile.setPhoneNumber(user.getPhoneNumber());
        myProfile.setAddress(user.getAddress());
        myProfile.setInterests(user.getInterests());
        myProfile.setBalance(user.getBalance());
        myProfile.setPhotoPath(user.getPhotoPath());
        myProfile.setIdentityCardPath(user.getIdentityCardPath());
        myProfile.setSelfiePath(user.getSelfiePath());
        myProfile.setAssessmentScore(user.getAssessmentScore());
        myProfile.setIdVerificationStatus(user.getIdVerificationStatus());
        myProfile.setRemainingDebt(getRemainingDebt(user));
        myProfile.setAssets(getTotalAssets(user));
        return myProfile;
    }

    private static Long getRemainingDebt(User user) {
        long totalDebt = 0L;
        long paidDebt = 0L;
        for (Loan loan : user.getLoans()) {
            for (int i = 1; i < loan.getLoanPayments().size(); i++) {
                if (loan.getLoanPayments().get(i) != null)
                    paidDebt += loan.getLoanPayments().get(i).getPayment().getFinancialTransaction().getNominal()
                            .longValue();
            }
        }
        for (Loan loan : user.getLoans()) {
            if (!loan.getLoanPayments().isEmpty())
                totalDebt += loan.getTotalPayment();
        }
        return totalDebt - paidDebt;
    }

    private static Long getTotalAssets(User user) {
        long totalAssets = 0L;
        for (Funding funding : user.getFundings().stream().filter(funding -> funding.getWithdrawals() != null).collect(Collectors.toList())) {
            totalAssets += funding.getFinancialTransaction().getNominal();
        }
        return totalAssets;
    }
}
