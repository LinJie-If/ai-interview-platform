package modules.resume.entity;
import jakarta.persistence.*;


import java.time.LocalDateTime;

// esumeEntity 定义了 resumes 主表映射。
@Entity
@Table(
    name = "resumes",
    indexes = {
        @Index(name = "idx_resume_file_hash", columnList = "file_hash")
    }
)
public class ResumeEntity {

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_hash", nullable = false, unique = true, length = 128)
    private String fileHash;

    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "storage_key", length = 255)
    private String storageKey;

    @Lob
    @Column(name = "resume_text", columnDefinition = "TEXT")
    private String resumeText;

    @Enumerated(EnumType.STRING)
    @Column(name = "analyze_status", nullable = false, length = 32)
    private ResumeAnalyzeStatus analyzeStatus;

    @Lob
    @Column(name = "analyze_error", columnDefinition = "TEXT")
    private String analyzeError;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    //

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        if (uploadedAt == null) {
            uploadedAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (analyzeStatus == null) {
            analyzeStatus = ResumeAnalyzeStatus.UPLOADED;
        }
    }

    //
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

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
    
}
