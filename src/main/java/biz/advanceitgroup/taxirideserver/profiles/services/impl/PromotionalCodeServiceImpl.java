package biz.advanceitgroup.taxirideserver.profiles.services.impl;

import biz.advanceitgroup.taxirideserver.profiles.entities.PromotionalCode;
import biz.advanceitgroup.taxirideserver.profiles.repositories.PromotionalCodeRepository;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.PromotionalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionalCodeServiceImpl implements PromotionalCodeService {

    @Autowired
    private PromotionalCodeRepository promotionalCodeRepository;

    @Override
    public PromotionalCode save(PromotionalCode code) {
        return promotionalCodeRepository.save(code);
    }

    @Override
    public void delete(PromotionalCode code) {
        promotionalCodeRepository.delete(code);
    }

    @Override
    public Optional<PromotionalCode> findOne(String id) {
        return promotionalCodeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<PromotionalCode> findOne(Long id) {
        return promotionalCodeRepository.findById(id);
    }

    @Override
    public Boolean existsByCodeKey(String key) {
        return promotionalCodeRepository.existsByCodeKey(key);
    }

    @Override
    public Optional<PromotionalCode> findByKey(String key) {
        return promotionalCodeRepository.findByCodeKey(key);
    }

    @Override
    public PromotionalCode update(PromotionalCode code, Long id) {
        code.setId(id);
        return save(code);
    }

    @Override
    public List<PromotionalCode> findAll() {
        return promotionalCodeRepository.findAll();
    }

	@Override
	public Boolean deletePromotional(Long id) {
		promotionalCodeRepository.deleteById(id);
		return true;
	}
}
