package modules.resume.service;

import common.exception.BusinessException;
import modules.resume.dto.ResumeDetailDTO;
import modules.resume.dto.ResumeListItemDTO;
import modules.resume.entity.ResumeAnalysisEntity;
import modules.resume.repository.ResumeAnalysisRepository;
import modules.resume.repository.ResumeRepository;
import modules.resume.entity.ResumeAnalyzeStatus;
import modules.resume.entity.ResumeEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeAnalysisRepository resumeAnalysisRepository;

    // 上传
    // 上传简历时，先创建一条简历记录
    @Override
    public ResumeDetailDTO uploadResume(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        ResumeEntity resume = new ResumeEntity();
        resume.setFileHash(UUID.randomUUID().toString().replace("-", ""));
        resume.setOriginalFilename(file.getOriginalFilename());
        resume.setFileSize(file.getSize());
        resume.setContentType(file.getContentType());
        resume.setAnalyzeStatus(ResumeAnalyzeStatus.UPLOADED);

        ResumeEntity saved = resumeRepository.save(resume);
        return toDetailDTO(saved, null);
    }

    // 查询简历列表时，返回轻量列表 DTO
    @Override
    public List<ResumeListItemDTO> listResumes() {
        return resumeRepository.findAll().stream()
                .map(this::toListItemDTO)
                .collect(Collectors.toList());
    }

    // 查询简历详情时，返回详情 DTO + 最新分析结果
    @Override
    public ResumeDetailDTO getResumeById(Long id) {
        ResumeEntity resume = resumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "简历不存在"));

        ResumeAnalysisEntity latestAnalysis = resumeAnalysisRepository
                .findTopByResumeIdOrderByAnalyzedAtDesc(id)
                .orElse(null);

        return toDetailDTO(resume, latestAnalysis);
    }

    // 删除简历时，先校验再删除
    @Override
    public void deleteResume(Long id) {
        if (!resumeRepository.existsById(id)) {
            throw new BusinessException(404, "简历不存在");
        }
        resumeRepository.deleteById(id);
    }
    

    //dto
    private ResumeListItemDTO toListItemDTO(ResumeEntity resume) {
        ResumeListItemDTO dto = new ResumeListItemDTO();
        dto.setId(resume.getId());
        dto.setOriginalFilename(resume.getOriginalFilename());
        dto.setFileSize(resume.getFileSize());
        dto.setContentType(resume.getContentType());
        dto.setAnalyzeStatus(resume.getAnalyzeStatus());
        dto.setUploadedAt(resume.getUploadedAt());
        dto.setUpdatedAt(resume.getUpdatedAt());
        return dto;
    }

    private ResumeDetailDTO toDetailDTO(ResumeEntity resume, ResumeAnalysisEntity latestAnalysis) {
        ResumeDetailDTO dto = new ResumeDetailDTO();
        dto.setId(resume.getId());
        dto.setFileHash(resume.getFileHash());
        dto.setOriginalFilename(resume.getOriginalFilename());
        dto.setFileSize(resume.getFileSize());
        dto.setContentType(resume.getContentType());
        dto.setStorageKey(resume.getStorageKey());
        dto.setResumeText(resume.getResumeText());
        dto.setAnalyzeStatus(resume.getAnalyzeStatus());
        dto.setAnalyzeError(resume.getAnalyzeError());
        dto.setUploadedAt(resume.getUploadedAt());
        dto.setUpdatedAt(resume.getUpdatedAt());

        if (latestAnalysis != null) {
            ResumeDetailDTO.ResumeAnalysisDTO analysisDTO = new ResumeDetailDTO.ResumeAnalysisDTO();
            analysisDTO.setId(latestAnalysis.getId());
            analysisDTO.setOverallScore(latestAnalysis.getOverallScore());
            analysisDTO.setSummary(latestAnalysis.getSummary());
            analysisDTO.setScoreBreakdownJson(latestAnalysis.getScoreBreakdownJson());
            analysisDTO.setStrengthsJson(latestAnalysis.getStrengthsJson());
            analysisDTO.setSuggestionsJson(latestAnalysis.getSuggestionsJson());
            analysisDTO.setAnalyzedAt(latestAnalysis.getAnalyzedAt());
            dto.setLatestAnalysis(analysisDTO);
        }

        return dto;
    }
}
