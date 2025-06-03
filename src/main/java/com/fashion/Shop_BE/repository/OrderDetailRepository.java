package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.OrderDetail;
import com.fashion.Shop_BE.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT o.orderStatus FROM OrderDetail od JOIN Order o  where od.productVariant.variantId = :productVariantId")
    List<OrderStatus> findOrderStatusesByProductVariantId(@Param("productVariantId") Long productVariantId);

    @Query(value = """
    SELECT
         p.product_id,
         p.product_name,
         (
             SELECT pr.resource_url
             FROM product_resources pr\s
             WHERE pr.product_id = p.product_id and pr.is_primary=true\s
             ORDER BY pr.product_resource_id ASC
             LIMIT 1
         ) AS product_image,
         SUM(od.quantity) AS total_sold,
         SUM(od.quantity * od.item_price) AS total_revenue
     FROM order_details od
     JOIN orders o ON o.order_id = od.order_id
     JOIN product_variants pv ON pv.product_variant_id = od.product_variant_id
     JOIN products p ON p.product_id = pv.product_id
     WHERE o.order_status = 'DELIVERED'
       AND o.order_date BETWEEN :start AND :end
     GROUP BY p.product_id, p.product_name
     ORDER BY total_sold DESC
     LIMIT :limit
""", nativeQuery = true)
    List<Object[]> getTopSellingProducts(@Param("start") Date start,
                                         @Param("end") Date end,
                                         @Param("limit") int limit);


}
