package com.besafx.app.service;
import com.besafx.app.entity.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public interface PaymentService extends PagingAndSortingRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    Payment findByCode(Integer code);
    Payment findByCodeAndAccountCourseMasterBranch(Integer code, Branch branch);
    Payment findByCodeAndLastPersonBranch(Integer code, Branch branch);
    List<Payment> findByAccount(Account account);
    List<Payment> findByAccountAndType(Account account, String type);
    List<Payment> findByAccountCourseMasterBranch(Branch branch);
    List<Payment> findByAccountCourseMaster(Master master);
    List<Payment> findByAccountCourse(Course course);
    List<Payment> findByAccountIn(List<Account> accounts);
    List<Payment> findByAccountCourseMasterBranchAndDateBetween(Branch branch, @Temporal(TemporalType.TIMESTAMP) Date startDate, @Temporal(TemporalType.TIMESTAMP) Date endDate);
    List<Payment> findByAccountCourseMasterAndDateBetween(Master master, @Temporal(TemporalType.TIMESTAMP) Date startDate, @Temporal(TemporalType.TIMESTAMP) Date endDate);
    List<Payment> findByAccountCourseAndDateBetween(Course course, @Temporal(TemporalType.TIMESTAMP) Date startDate, @Temporal(TemporalType.TIMESTAMP) Date endDate);
    List<Payment> findByAccountInAndDateBetween(List<Account> accounts, @Temporal(TemporalType.TIMESTAMP) Date startDate, @Temporal(TemporalType.TIMESTAMP) Date endDate);

}
