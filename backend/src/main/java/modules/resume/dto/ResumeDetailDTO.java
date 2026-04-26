package modules.resume.dto;

import java.time.LocalDateTime;

import modules.resume.entity.ResumeAnalyzeStatus;

public class ResumeDetailDTO {

    private Long id;

    private String fileHash;

    private String originalFilename;

    private Long fileSize;

    private String contentType;

    private String storageKey;

    private String resumeText;

    private ResumeAnalyzeStatus analyzeStatus;

    private String analyzeError;

    private LocalDateTime uploadedAt;

    private LocalDateTime updatedAt;

    private ResumeAnalysisDTO latestAnalysis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public ResumeAnalyzeStatus getAnalyzeStatus() {
        return analyzeStatus;
    }

    public void setAnalyzeStatus(ResumeAnalyzeStatus analyzeStatus) {
        this.analyzeStatus = analyzeStatus;
    }

    public String getAnalyzeError() {
        return analyzeError;
    }

    public void setAnalyzeError(String analyzeError) {
        this.analyzeError = analyzeError;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ResumeAnalysisDTO getLatestAnalysis() {
        return latestAnalysis;
    }

    public void setLatestAnalysis(ResumeAnalysisDTO latestAnalysis) {
        this.latestAnalysis = latestAnalysis;
    }

    public static class ResumeAnalysisDTO {

        private Long id;

        private Integer overallScore;

        private String summary;

        private String scoreBreakdownJson;

        private String strengthsJson;

        private String suggestionsJson;

        private LocalDateTime analyzedAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getOverallScore() {
            return overallScore;
        }

        public void setOverallScore(Integer overallScore) {
            this.overallScore = overallScore;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getScoreBreakdownJson() {
            return scoreBreakdownJson;
        }

        public void setScoreBreakdownJson(String scoreBreakdownJson) {
            this.scoreBreakdownJson = scoreBreakdownJson;
        }

        public String getStrengthsJson() {
            return strengthsJson;
        }

        public void setStrengthsJson(String strengthsJson) {
            this.strengthsJson = strengthsJson;
        }

        public String getSuggestionsJson() {
            return suggestionsJson;
        }

        public void setSuggestionsJson(String suggestionsJson) {
            this.suggestionsJson = suggestionsJson;
        }

        public LocalDateTime getAnalyzedAt() {
            return analyzedAt;
        }

        public void setAnalyzedAt(LocalDateTime analyzedAt) {
            this.analyzedAt = analyzedAt;
        }
    }

}
