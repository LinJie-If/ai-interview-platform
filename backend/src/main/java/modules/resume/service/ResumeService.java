package modules.resume.service;

import modules.resume.dto.ResumeDetailDTO;
import modules.resume.dto.ResumeListItemDTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    ResumeDetailDTO uploadResume(MultipartFile file);

    List<ResumeListItemDTO> listResumes();

    ResumeDetailDTO getResumeById(Long id);

    void deleteResume(Long id);

}
