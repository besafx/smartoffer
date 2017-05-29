package com.besafx.app.service;

import com.besafx.app.entity.BillBuy;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface BillBuyService extends PagingAndSortingRepository<BillBuy, Long>, JpaSpecificationExecutor<BillBuy> {

    List<BillBuy> findByIdIn(List<Long> listId);
}