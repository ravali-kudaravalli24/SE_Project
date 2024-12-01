package com.innovators.jobreferralportal.repository;

import com.innovators.jobreferralportal.entity.ReferredCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferredCandidateRepo extends JpaRepository<ReferredCandidate, Long> {
    public List<ReferredCandidate> findByReferredBy(Long referredBy);

    public List<ReferredCandidate> findByFirstNameContainingIgnoreCase(String name);

    public List<ReferredCandidate> findByReferredByAndFirstNameContainingIgnoreCase(Long referredBy, String name);

}
