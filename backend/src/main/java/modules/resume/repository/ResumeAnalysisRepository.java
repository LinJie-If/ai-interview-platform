package modules.resume.repository;

import modules.resume.entity.ResumeAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysisEntity, Long> {
    // 查询某份简历最新的一次分析结果
    Optional<ResumeAnalysisEntity> findTopByResumeIdOrderByAnalyzedAtDesc(Long resumeId);
}
