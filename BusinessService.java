import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    public Optional<Business> getBusinessById(Long id) {
        return businessRepository.findById(id);
    }

    public Business addBusiness(Business business) {
        return businessRepository.save(business);
    }

    public Business updateBusiness(Long id, Business updatedBusiness) {
        Optional<Business> existingBusiness = businessRepository.findById(id);
        if (existingBusiness.isPresent()) {
            Business business = existingBusiness.get();
            business.setName(updatedBusiness.getName());
            business.setAddress(updatedBusiness.getAddress());
            business.setLatitude(updatedBusiness.getLatitude());
            business.setLongitude(updatedBusiness.getLongitude());
            business.setPhone(updatedBusiness.getPhone());
            business.setCategories(updatedBusiness.getCategories());
            business.setRating(updatedBusiness.getRating());
            business.setPrice(updatedBusiness.getPrice());
            return businessRepository.save(business);
        } else {
            throw new RuntimeException("Business not found with id: " + id);
        }
    }

    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}

