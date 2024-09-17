package com.rgt.assignment.service;

import com.rgt.assignment.domain.Menu;
import com.rgt.assignment.exception.InvalidRequestException;
import com.rgt.assignment.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(InvalidRequestException::new);
    }
}
