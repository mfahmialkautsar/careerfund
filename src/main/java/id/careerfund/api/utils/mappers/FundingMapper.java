package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.models.responses.FundingDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FundingMapper {
    private final ModelMapper modelMapper;
    private final LoanMapper loanMapper;

    public FundingDto entityToDto(Funding funding) {
        FundingDto fundingDto = modelMapper.map(funding, FundingDto.class);
        fundingDto.setLoan(loanMapper.entityToDto(funding.getLoan(), funding.getLender().getId()));
        return fundingDto;
    }
}
