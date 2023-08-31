package com.packagecentre.ms.orderservice.jpa.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.packagecentre.ms.orderservice.jpa.entity.Order;

@Repository
public interface OrdersJPARepository extends JpaRepository<Order, Integer>{
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ORDERS O SET O.ORD_STATUS = ?1, O.STATUS_DT = ?2 WHERE O.ORD_ID = ?3 AND O.ORD_STATUS = ?4", nativeQuery = true)
	int updateOrderStatusPkgID( @Param("statusNew") String statusNew, @Param("orderStatusDate") LocalDateTime orderStatusDate,
			@Param("ordID") String ordID, @Param("statusOld") String statusOld);

}
