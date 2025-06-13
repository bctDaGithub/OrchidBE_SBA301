# OrchidBe - Backend for Orchid E-Commerce System

OrchidBe là hệ thống backend được xây dựng bằng **Spring Boot**, phục vụ cho nền tảng thương mại điện tử chuyên về **hoa lan (orchid)**. Ứng dụng triển khai mô hình **CQRS (Command Query Responsibility Segregation)** để tách biệt xử lý ghi (Command) và đọc (Query), từ đó cải thiện khả năng mở rộng và tối ưu hiệu năng. Dữ liệu được đồng bộ theo hướng sự kiện (event-driven) thông qua RabbitMQ.

---

## 🧩 Key Features

- **User Management**: Tạo, cập nhật, khóa/mở khóa tài khoản người dùng với phân quyền theo vai trò.
- **Order Management**: Tạo đơn hàng với thông tin hoa lan thông qua quan hệ nhiều-nhiều (OrderDetail).
- **Orchid Catalog**: Quản lý sản phẩm hoa lan (tên, mô tả, giá, trạng thái).
- **CQRS Architecture**: Phân tách rõ ràng giữa ghi và đọc.
- **Event-Driven Synchronization**: Đồng bộ dữ liệu bằng RabbitMQ giữa Command và Query.
- **RESTful APIs**: Cung cấp các endpoint cho quản lý tài khoản, đơn hàng, và sản phẩm.

---

## ⚙️ Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA (SQL Server - Command Side)
- Spring Data MongoDB (Query Side)
- RabbitMQ
- Lombok
- Maven

---

## 📁 Project Structure
org.example.orchidbe
├── command
│ ├── entities # JPA entities
│ ├── repositories # SQL Server repositories
│ ├── services
│ │ ├── define # Service interfaces
│ │ ├── implement # Service implementations
│ ├── controllers # Command REST controllers
├── query
│ ├── documents # MongoDB documents
│ ├── repositories # MongoDB repositories
│ ├── services
│ │ ├── define # Service interfaces
│ │ ├── implement # Service implementations
│ ├── controllers # Query REST controllers
├── events # Event classes
├── listeners # RabbitMQ listeners
├── config # Config classes (RabbitMQ, MongoDB)
└── OrchidBeApplication.java

---

## 🗃️ Data Models

### 📌 Command Side (SQL Server)
- `AccountEntity`: id, userName, email, password, role, orders, isAvailable
- `OrderEntity`: id, account, orderDetails, totalPrice, orderDate
- `OrchidEntity`: id, name, price, description, url, isNatural, isAvailable
- `OrderDetail`: order, orchid, quantity, unitPrice

### 📌 Query Side (MongoDB)
- `AccountDocument`: id, userName, email, role, orderIds, isAvailable
- `OrderDocument`: id, accountId, orderDetails[], totalPrice, orderDate
- `OrchidDocument`: orchidId, orchidName, price, description, url, isNatural, isAvailable
- `OrderDetailDocument`: orchidId, orchidName, unitPrice, quantity (embedded)

---

## 🚀 Setup Instructions

### ✅ Prerequisites

- Java 17+
- Maven 3.8+
- SQL Server
- MongoDB
- RabbitMQ
- IDE (IntelliJ, VSCode, Eclipse,...)

### 🧪 Clone & Configure

```bash
git clone <repository-url>
cd orchidbe


