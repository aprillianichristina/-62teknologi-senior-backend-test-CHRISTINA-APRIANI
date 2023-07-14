import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/search")
public ResponseEntity<List<Business>> searchBusinesses(
        @RequestParam("term") String term,
        @RequestParam("location") String location,
        @RequestParam(value = "latitude", required = false) Double latitude,
        @RequestParam(value = "longitude", required = false) Double longitude,
        @RequestParam(value = "radius", defaultValue = "5000") Integer radius,
        @RequestParam(value = "categories", required = false) String categories,
        @RequestParam(value = "locale", defaultValue = "en_US") String locale,
        @RequestParam(value = "limit", defaultValue = "10") Integer limit,
        @RequestParam(value = "offset", defaultValue = "0") Integer offset,
        @RequestParam(value = "sort_by", defaultValue = "best_match") String sortBy,
        @RequestParam(value = "price", required = false) String price,
        @RequestParam(value = "open_now", defaultValue = "false") Boolean openNow,
        @RequestParam(value = "open_at", required = false) Long openAt,
        @RequestParam(value = "attributes", required = false) String attributes
) {
    List<Business> businesses = new ArrayList<>();
    businesses = businessService.searchBusinessesByNameAndLocation(term, location);

    return new ResponseEntity<>(businesses, HttpStatus.OK);
}
    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long id) {
        Optional<Business> business = businessService.getBusinessById(id);
        return business.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Business> addBusiness(@RequestBody Business business) {
        Business newBusiness = businessService.addBusiness(business);
        return new ResponseEntity<>(newBusiness, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        Business updatedBusiness = businessService.updateBusiness(id, business);
        return new ResponseEntity<>(updatedBusiness, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        businessService.deleteBusiness(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
