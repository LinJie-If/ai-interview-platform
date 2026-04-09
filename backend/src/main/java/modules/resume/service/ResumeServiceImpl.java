package modules.resume.service;

import common.exception.BusinessException;
import modules.resume.dto.ResumeDTO;
import modules.resume.entity.Resume;
import modules.resume.repository.ResumeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public ResumeDTO uploadResume(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }
        Resume resume = new Resume();
        resume.setOriginalName(file.getOriginalFilename());
        resume.setFileName(file.getOriginalFilename());
        resume.setFileSize(file.getSize());
        resume.setStatus("UPLOADED");
        Resume saved = resumeRepository.save(resume);
        return toDTO(saved);
    }

    @Override
    public List<ResumeDTO> listResumes() {
        return resumeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResumeDTO getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "简历不存在"));
        return toDTO(resume);
    }

    @Override
    public void deleteResume(Long id) {
        if (!resumeRepository.existsById(id)) {
            throw new BusinessException(404, "简历不存在");
        }
        resumeRepository.deleteById(id);
    }

    private ResumeDTO toDTO(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        BeanUtils.copyProperties(resume, dto);
        return dto;
    }
}
