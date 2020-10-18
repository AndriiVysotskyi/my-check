package com.mycheck.auth.repository;

import com.mycheck.auth.dao.TokenDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenDao, String> {
}
