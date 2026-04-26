package modules.resume.dto;

import java.time.LocalDateTime;

import modules.resume.entity.ResumeAnalyzeStatus;

public class ResumeListItemDTO {
    private Long id;

    private String originalFilename;

    private Long fileSize;

    private String contentType;

    private ResumeAnalyzeStatus analyzeStatus;

    private LocalDateTime uploadedAt;

    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ResumeAnalyzeStatus getAnalyzeStatus() {
        return analyzeStatus;
    }

    public void setAnalyzeStatus(ResumeAnalyzeStatus analyzeStatus) {
        this.analyzeStatus = analyzeStatus;
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
}
