package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.models.responses.LoanResponse;

public final class LoanMapper {
    public static LoanResponse entityToResponse(id.careerfund.api.domains.entities.Loan loan) {
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.setId(loan.getId());
        loanResponse.setBorrower(UserMapper.userToBorrower(loan.getBorrower()));
        loanResponse.setInterestPercent(loan.getInterestPercent());
        loanResponse.setTenorMonth(loan.getTenorMonth());
        loanResponse.setTargetFund(loan.getTotalPayment());
        loanResponse.setAClass(loan.getUserClass().getAClass());
        loanResponse.setMonthlyPayment(loan.getMonthlyPayment());
        loanResponse.setTotalPayment(loan.getTotalPayment());
        loanResponse.setLoanPayments(loan.getLoanPayments());
        return loanResponse;
    }
}
