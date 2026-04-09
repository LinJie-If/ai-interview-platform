package modules.interview.controller;

import common.result.Result;
import modules.interview.dto.InterviewDTO;
import modules.interview.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping
    public Result<InterviewDTO> create(@RequestBody InterviewDTO dto) {
        return Result.success(interviewService.createInterview(dto));
    }

    @GetMapping("/list")
    public Result<List<InterviewDTO>> list() {
        return Result.success(interviewService.listInterviews());
    }

    @GetMapping("/resume/{resumeId}")
    public Result<List<InterviewDTO>> listByResumeId(@PathVariable Long resumeId) {
        return Result.success(interviewService.listByResumeId(resumeId));
    }

    @GetMapping("/{id}")
    public Result<InterviewDTO> getById(@PathVariable Long id) {
        return Result.success(interviewService.getInterviewById(id));
    }

    @PutMapping("/{id}")
    public Result<InterviewDTO> update(@PathVariable Long id, @RequestBody InterviewDTO dto) {
        return Result.success(interviewService.updateInterview(id, dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return Result.success();
    }
}
