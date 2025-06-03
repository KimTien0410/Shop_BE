package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.Order;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser(User user, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.orderId = :orderId")
    Optional<Order> findByOrderIdAndUser(@Param("orderId") Long orderId, @Param("user") User user);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.orderStatus = :orderStatus")
    Page<Order> findByUserAndOrderStatus(@Param("user") User user,@Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    long countByOrderStatus(OrderStatus orderStatus);

    @Query("SELECT COALESCE(SUM(o.orderTotalAmount), 0) FROM Order o WHERE o.orderStatus = 'DELIVERED' AND o.orderDate BETWEEN :start AND :end")
    Double getTotalRevenue(@Param("start") Date start, @Param("end") Date end);

    @Query(value = """
    SELECT DATE(o.order_date) AS date, SUM(o.order_total_amount) AS revenue
    FROM orders o
    WHERE o.order_status = 'DELIVERED' AND o.order_date BETWEEN :start AND :end
    GROUP BY DATE(o.order_date)
    ORDER BY DATE(o.order_date)
""", nativeQuery = true)
    List<Object[]> getDailyRevenue(Date start, Date end);


    @Query(value = """
    SELECT DATE_FORMAT(o.order_date, '%Y-%m') AS month, SUM(o.order_total_amount) AS revenue
    FROM orders o
    WHERE o.order_status = 'PAID' AND o.order_date BETWEEN :start AND :end
    GROUP BY DATE_FORMAT(o.order_date, '%Y-%m')
    ORDER BY month
""", nativeQuery = true)
    List<Object[]> getMonthlyRevenue(Date start, Date end);


    @Query("SELECT COALESCE(SUM(o.orderTotalAmount), 0) FROM Order o WHERE o.orderStatus = 'DELIVERED'")
    Double getTotalRevenues();

}
