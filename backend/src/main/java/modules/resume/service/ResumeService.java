package modules.resume.service;

import modules.resume.dto.ResumeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    ResumeDTO uploadResume(MultipartFile file);

    List<ResumeDTO> listResumes();

    ResumeDTO getResumeById(Long id);

    void deleteResume(Long id);
}
