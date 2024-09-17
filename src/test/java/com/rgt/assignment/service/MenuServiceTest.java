package com.rgt.assignment.service;

import com.rgt.assignment.domain.Menu;
import com.rgt.assignment.exception.InvalidRequestException;
import com.rgt.assignment.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("메뉴 ID가 유효할 때 메뉴를 성공적으로 반환한다")
    void testGetMenu_ValidMenuId() {
        // given
        Long menuId = 1L;
        Menu mockMenu = mock(Menu.class);
        given(mockMenu.getId()).willReturn(menuId);
        given(menuRepository.findById(menuId)).willReturn(Optional.of(mockMenu));

        // when
        Menu result = menuService.getMenu(menuId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(menuId);
        verify(menuRepository, times(1)).findById(menuId);
    }

    @Test
    @DisplayName("메뉴 ID가 유효하지 않을 때 InvalidRequestException을 던진다")
    void testGetMenu_InvalidMenuId() {
        // given
        Long menuId = 999L;
        given(menuRepository.findById(menuId)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> menuService.getMenu(menuId)).isInstanceOf(InvalidRequestException.class);
        verify(menuRepository, times(1)).findById(menuId);
    }
}
