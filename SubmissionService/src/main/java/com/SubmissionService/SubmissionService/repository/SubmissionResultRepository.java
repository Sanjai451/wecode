package com.SubmissionService.SubmissionService.repository;

import com.SubmissionService.SubmissionService.model.SubmissionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionResultRepository extends JpaRepository<SubmissionResult, UUID> {

    List<SubmissionResult> findBySubmissionId(UUID submissionId);
}
