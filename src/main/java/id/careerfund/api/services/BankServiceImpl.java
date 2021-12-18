package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bank;
import id.careerfund.api.repositories.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepo;

    @Override
    public List<Bank> getBanks() {
        return bankRepo.findAll();
    }
}
