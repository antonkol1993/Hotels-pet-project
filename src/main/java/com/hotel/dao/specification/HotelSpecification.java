package com.hotel.dao.specification;

import com.hotel.model.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class HotelSpecification {


    public Specification<Hotel> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get(Hotel.NAME_PROPERTY)), "%" + name.toLowerCase() + "%");
        };
    }

    public Specification<Hotel> brandContains(String brand) {
        return (root, query, cb) -> {
            if (brand == null || brand.isBlank()) return null;
            return cb.like(cb.lower(root.get(Hotel.BRAND_PROPERTY)), "%" + brand.toLowerCase() + "%");
        };
    }

    public Specification<Hotel> cityContains(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isBlank()) return null;
            return cb.like(cb.lower(root.get("address").get(Hotel.CITY_PROPERTY)), "%" + city.toLowerCase() + "%");
        };
    }

    public Specification<Hotel> countryContains(String country) {
        return (root, query, cb) -> {
            if (country == null || country.isBlank()) return null;
            return cb.like(cb.lower(root.get("address").get(Hotel.COUNTRY_PROPERTY)), "%" + country.toLowerCase() + "%");
        };
    }

    public Specification<Hotel> hasAmenity(String amenity) {
        return (root, query, cb) -> {
            if (amenity == null || amenity.isBlank()) return null;
            Join<Object, Object> amenitiesJoin = root.join(Hotel.AMENITIES_PROPERTY, JoinType.INNER);
            return cb.like(cb.lower(amenitiesJoin.get("name")), "%" + amenity.toLowerCase() + "%");
        };
    }
}
