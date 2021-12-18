package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.models.responses.FundingDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FundingMapper {
    Funding fundingDtoToFunding(FundingDto fundingDto);

    FundingDto fundingToFundingDto(Funding funding);

    @BeanMapping
    void updateFundingFromFundingDto(FundingDto fundingDto, @MappingTarget Funding funding);
}
