package com.rgt.assignment;

import com.rgt.assignment.domain.Member;
import com.rgt.assignment.domain.Menu;
import com.rgt.assignment.domain.Restaurant;
import com.rgt.assignment.repository.MemberRepository;
import com.rgt.assignment.repository.MenuRepository;
import com.rgt.assignment.repository.RestaurantRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("tester1", "test1@gmail.com", "1234"));
        memberRepository.save(new Member("tester2", "test2@gmail.com", "1234"));
        memberRepository.save(new Member("tester3", "test3@gmail.com", "1234"));

        Restaurant restaurant = new Restaurant(123L,"큭큭피자", 10);
        restaurantRepository.save(restaurant);

        menuRepository.save(new Menu(restaurant, "감자밭 피자", 25000, "감자"));
        menuRepository.save(new Menu(restaurant, "통통 새우 피자", 25000, "새우"));
        menuRepository.save(new Menu(restaurant,"베이컨 피자", 25000, "베이컨"));
    }
}
