package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.responses.UserClassBorrowerDto;
import id.careerfund.api.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class UserClassMapper {
    private final ModelMapper modelMapper;
    private final LoanMapper loanMapper;
    @Lazy
    private final ClassService classService;

    public UserClassBorrowerDto entityToBorrowerDto(UserClass userClass) {
        UserClassBorrowerDto userClassBorrowerDto = modelMapper.map(userClass, UserClassBorrowerDto.class);
        userClassBorrowerDto.setDpPaid(!userClass.getLoan().getLoanPayments().isEmpty());
        userClassBorrowerDto.setLoan(loanMapper.entityToBorrowerDto(userClass.getLoan()));
        userClassBorrowerDto.getAClass().setDurationMonth(classService.getMonthDuration(userClass.getAClass()));
        return userClassBorrowerDto;
    }
}
