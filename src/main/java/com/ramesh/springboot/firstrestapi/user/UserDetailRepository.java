package com.ramesh.springboot.firstrestapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDetailRepository extends PagingAndSortingRepository<UserDetail, Long> {
}
