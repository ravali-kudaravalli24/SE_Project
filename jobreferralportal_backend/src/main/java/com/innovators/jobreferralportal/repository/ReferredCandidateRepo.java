package com.innovators.jobreferralportal.repository;


import com.innovators.jobreferralportal.entity.ReferredCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferredCandidateRepo extends JpaRepository<ReferredCandidate,Long> {
}
