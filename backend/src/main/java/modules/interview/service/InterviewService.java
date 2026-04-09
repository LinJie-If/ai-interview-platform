package modules.interview.service;

import modules.interview.dto.InterviewDTO;

import java.util.List;

public interface InterviewService {

    InterviewDTO createInterview(InterviewDTO dto);

    List<InterviewDTO> listInterviews();

    List<InterviewDTO> listByResumeId(Long resumeId);

    InterviewDTO getInterviewById(Long id);

    InterviewDTO updateInterview(Long id, InterviewDTO dto);

    void deleteInterview(Long id);
}
