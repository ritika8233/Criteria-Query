package com.example.criteriaquerydemo.service;

import com.example.criteriaquerydemo.dto.SpecifiactionDto;
import com.example.criteriaquerydemo.dto.SpecificationRequest;
import com.example.criteriaquerydemo.model.User;
import com.example.criteriaquerydemo.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService<T> {
    @Autowired UserRepository userRepository;


    public Specification<T> getBySpecifiaction(SpecifiactionDto specifiactionDto){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(specifiactionDto.getColumn()), specifiactionDto.getValue());
            }
        };
    }


    public Specification<T> getBySpecifiactions(List<SpecifiactionDto> request, SpecificationRequest.Operator operator){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (SpecifiactionDto dto : request){


                switch (dto.getOperation()){

                    case LIKE: // eg : a
                        Predicate like = criteriaBuilder.like(root.get(dto.getColumn()), "%"+dto.getValue()+"%");
                        predicates.add(like);
                        break;

                    case EQUAL: // eg : Riya
                        Predicate equal = criteriaBuilder.equal(root.get(dto.getColumn()), dto.getValue());
                        predicates.add(equal);
                        break;

                    case IN: // eg : Riya, Siya, Prachi
                        String[] split = dto.getValue().split(",");
                        Predicate in = root.get(dto.getColumn()).in(Arrays.asList(split));
                        predicates.add(in);
                        break;

                    case BETWEEN: // eg : 10, 20
                        String[] split1 = dto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(dto.getColumn()), split1[0], split1[1]);
                        predicates.add(between);
                        break;

                    case GREATER_THAN: // eg : Riya
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(dto.getColumn()), dto.getValue());
                        predicates.add(greaterThan);
                        break;

                    case LESS_THAN: // eg : Riya
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(dto.getColumn()), dto.getValue());
                        predicates.add(lessThan);
                        break;

                    case JOIN: // eg : Riya
                        //criteriaBuilder.equal(root.join("Join Table name - address").get("attribute from join table - city"), dto.getValue());
                        Predicate join = criteriaBuilder.equal(root.join(dto.getJoinTable()).get(dto.getColumn()), dto.getValue());
                        predicates.add(join);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: ");
                }
            }

            if(operator.equals(SpecificationRequest.Operator.AND))
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

//            if(operator.equals(SpecificationRequest.Operator.OR))
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));


        };
    }


    public void saveUser(User user){
        userRepository.save(user);
    }

}
