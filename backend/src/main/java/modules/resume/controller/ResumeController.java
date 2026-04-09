package modules.resume.controller;

import common.result.Result;
import modules.resume.dto.ResumeDTO;
import modules.resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public Result<ResumeDTO> upload(@RequestParam("file") MultipartFile file) {
        return Result.success(resumeService.uploadResume(file));
    }

    @GetMapping("/list")
    public Result<List<ResumeDTO>> list() {
        return Result.success(resumeService.listResumes());
    }

    @GetMapping("/{id}")
    public Result<ResumeDTO> getById(@PathVariable Long id) {
        return Result.success(resumeService.getResumeById(id));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return Result.success();
    }
}
