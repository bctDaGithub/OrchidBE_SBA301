package org.example.orchidbe.events;

import lombok.Data;

import java.util.Set;

@Data
public class AccountCreatedEvent {
    private Long id;
    private String userName;
    private String email;
    private String roleName; // Chỉ gửi tên vai trò
    private Set<Long> orderIds; // Danh sách ID đơn hàng
    private boolean isAvailable;
}