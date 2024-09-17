package com.rgt.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgt.assignment.constant.SessionConstant;
import com.rgt.assignment.dto.LoginInfo;
import com.rgt.assignment.dto.OrderRequest;
import com.rgt.assignment.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @Test
    @DisplayName("유효한 주문 요청에 대해 주문을 성공적으로 처리하고 성공 응답을 반환한다")
    void testAddOrder_Successful() throws Exception {
        // given
        Long memberId = 1L;
        String memberName = "Test User";
        Long restaurantId = 123L;
        int tableNumber = 1;

        LoginInfo loginInfo = new LoginInfo(memberId, memberName, restaurantId, tableNumber);
        session.setAttribute(SessionConstant.LOGIN_INFO, loginInfo);

        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(new OrderRequest(1L, 1L, 10000, 2));
        orderRequests.add(new OrderRequest(2L, 2L, 5000, 1));

        // when, then
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequests))
                        .session(session))
                .andExpect(status().isOk());

        verify(orderService, times(1)).addOrder(eq(orderRequests), eq(loginInfo));
    }

    @Test
    @DisplayName("로그인 정보가 없는 상태에서 주문 요청 시 실패 응답을 반환한다")
    void testAddOrder_NoLoginInfo() throws Exception {
        // given
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(new OrderRequest(1L, 1L, 10000, 2));

        // when, then
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequests)))
                .andExpect(status().isUnauthorized());
    }
}
