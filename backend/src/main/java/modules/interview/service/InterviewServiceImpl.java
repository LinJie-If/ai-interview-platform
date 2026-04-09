package modules.interview.service;

import common.exception.BusinessException;
import modules.interview.dto.InterviewDTO;
import modules.interview.entity.Interview;
import modules.interview.repository.InterviewRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Override
    public InterviewDTO createInterview(InterviewDTO dto) {
        Interview interview = new Interview();
        BeanUtils.copyProperties(dto, interview);
        interview.setStatus("PENDING");
        return toDTO(interviewRepository.save(interview));
    }

    @Override
    public List<InterviewDTO> listInterviews() {
        return interviewRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewDTO> listByResumeId(Long resumeId) {
        return interviewRepository.findByResumeId(resumeId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewDTO getInterviewById(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "面试记录不存在"));
        return toDTO(interview);
    }

    @Override
    public InterviewDTO updateInterview(Long id, InterviewDTO dto) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "面试记录不存在"));
        BeanUtils.copyProperties(dto, interview, "id", "createdAt");
        return toDTO(interviewRepository.save(interview));
    }

    @Override
    public void deleteInterview(Long id) {
        if (!interviewRepository.existsById(id)) {
            throw new BusinessException(404, "面试记录不存在");
        }
        interviewRepository.deleteById(id);
    }

    private InterviewDTO toDTO(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        BeanUtils.copyProperties(interview, dto);
        return dto;
    }
}
