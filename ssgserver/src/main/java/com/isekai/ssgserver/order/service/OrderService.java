package com.isekai.ssgserver.order.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;
}
