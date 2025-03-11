package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.JobApplicationDTO;
import emerson_care.emerson_care.dto.JobDTO;
import emerson_care.emerson_care.entity.Job;
import emerson_care.emerson_care.entity.JobApplication;
import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.JobApplicationRepository;
import emerson_care.emerson_care.repository.JobRepository;
import emerson_care.emerson_care.repository.UserRepository;
import emerson_care.emerson_care.service.NotificationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/jobs")
public class JobsController {

    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public JobsController(JobRepository jobRepository, JobApplicationRepository jobApplicationRepository, UserRepository userRepository, NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/post")
    public ResponseEntity<Job> postJob(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
            @RequestParam("location") String location,
            @RequestParam("rate") String rate,
            @RequestParam("type") String type,
            @RequestParam("posterImage") MultipartFile posterImage) {

        try {
            // Directory for image storage
            String uploadDir = "/job/posters/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // File name in the desired format
            String imageFileName = "job_" + UUID.randomUUID() + "_" + posterImage.getOriginalFilename();
            String imagePath = uploadDir + imageFileName;

            // Save the file to the directory
            posterImage.transferTo(new File(imagePath));

            // Create and save the Job entity
            Job job = new Job();
            job.setTitle(title);
            job.setDescription(description);
            job.setDeadline(deadline);
            job.setLocation(location);
            job.setRate(rate);
            job.setType(type);
            job.setPosterImage(imagePath);
            job.setPostedDate(LocalDate.now());
            job.setUuid(UUID.randomUUID().toString());

            Job savedJob = jobRepository.save(job);

            notificationService.createJobNotification(savedJob);

            return ResponseEntity.ok(savedJob);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobRepository.findAll().stream()
                .map(job -> {
                    JobDTO dto = new JobDTO();
                    dto.setId(job.getId());
                    dto.setUuid(job.getUuid());
                    dto.setTitle(job.getTitle());
                    dto.setDescription(job.getDescription());
                    dto.setLocation(job.getLocation());
                    dto.setRate(job.getRate());
                    dto.setType(job.getType());
                    dto.setPosterImage(job.getPosterImage());
                    dto.setPostedDate(job.getPostedDate());
                    dto.setDeadline(job.getDeadline());
                    dto.setApplicationCount(job.getApplications().size());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(job -> {
                    JobDTO jobDTO = new JobDTO();
                    jobDTO.setId(job.getId());
                    jobDTO.setUuid(job.getUuid());
                    jobDTO.setTitle(job.getTitle());
                    jobDTO.setDescription(job.getDescription());
                    jobDTO.setLocation(job.getLocation());
                    jobDTO.setRate(job.getRate());
                    jobDTO.setType(job.getType());
                    jobDTO.setPosterImage(job.getPosterImage());
                    jobDTO.setPostedDate(job.getPostedDate());
                    jobDTO.setDeadline(job.getDeadline());

                    // Map applications to JobApplicationDTO
                    List<JobApplicationDTO> applicationDTOs = job.getApplications().stream()
                            .map(application -> {
                                JobApplicationDTO appDTO = new JobApplicationDTO();
                                appDTO.setId(application.getId());
                                appDTO.setApplicantName(application.getApplicantName());
                                appDTO.setCv(application.getCv());
                                appDTO.setApplicationLetter(application.getApplicationLetter());
                                appDTO.setAvailableStartDate(application.getAvailableStartDate());
                                appDTO.setCurrentEmploymentStatus(application.getCurrentEmploymentStatus());
                                return appDTO;
                            })
                            .collect(Collectors.toList());

                    jobDTO.setApplications(applicationDTOs);

                    return ResponseEntity.ok(jobDTO);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyForJob(
            @PathVariable Long jobId,
            @RequestParam("cv") MultipartFile cv,
            @RequestParam("applicationLetter") MultipartFile applicationLetter,
            @RequestParam("availableStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate availableStartDate,
            @RequestParam("currentEmploymentStatus") String currentEmploymentStatus) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        return jobRepository.findById(jobId).map(job -> {
            if (!cv.getContentType().equals("application/pdf") || !applicationLetter.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Files must be in PDF format");
            }

            try {
                // Fetch user's first name and last name from the database
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

                String firstName = user.getFirstName();
                String lastName = user.getLastName();

                // Directory for file storage
                String uploadDir = "/job/applications/";

                // File names in the desired format
                String cvFileName = firstName + "_" + lastName + "_CV.pdf";
                String letterFileName = firstName + "_" + lastName + "_ApplicationLetter.pdf";

                // File paths
                String cvPath = uploadDir + cvFileName;
                String letterPath = uploadDir + letterFileName;

                // Ensure the directory exists
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save files to the directory
                cv.transferTo(new File(cvPath));
                applicationLetter.transferTo(new File(letterPath));

                // Create and save the application
                JobApplication application = new JobApplication();
                application.setId(user.getId());
                application.setApplicantName(firstName + " " + lastName);
                application.setCv(cvPath);
                application.setApplicationLetter(letterPath);
                application.setAvailableStartDate(availableStartDate);
                application.setCurrentEmploymentStatus(currentEmploymentStatus);
                application.setJob(job);

                jobApplicationRepository.save(application);
                return ResponseEntity.ok("Application submitted successfully");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving files");
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found"));
    }

    @GetMapping("/applications")
    public ResponseEntity<List<JobApplicationDTO>> getUserJobApplications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        List<JobApplicationDTO> applications = jobApplicationRepository.findByApplicantName(username)
                .stream()
                .map(application -> {
                    JobApplicationDTO dto = new JobApplicationDTO();
                    dto.setId(application.getId());
                    dto.setApplicantName(application.getApplicantName());
                    dto.setCv(application.getCv());
                    dto.setApplicationLetter(application.getApplicationLetter());
                    dto.setAvailableStartDate(application.getAvailableStartDate());
                    dto.setCurrentEmploymentStatus(application.getCurrentEmploymentStatus());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(applications);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobDTO> editJob(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
            @RequestParam(value = "posterImage", required = false) MultipartFile posterImage) {

        return (ResponseEntity<JobDTO>) jobRepository.findById(id).map(job -> {
            try {
                // Update job details
                job.setTitle(title);
                job.setDescription(description);
                job.setDeadline(deadline);

                // Handle new poster image if provided
                if (posterImage != null) {
                    // Delete old poster image if it exists
                    String oldPosterPath = job.getPosterImage();
                    if (oldPosterPath != null) {
                        File oldFile = new File(oldPosterPath);
                        if (oldFile.exists() && oldFile.delete()) {
                            System.out.println("Deleted old poster: " + oldPosterPath);
                        }
                    }

                    // Save new poster image
                    String uploadDir = "/job/posters/";
                    File directory = new File(uploadDir);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    String newPosterFileName = "job_" + UUID.randomUUID() + "_" + posterImage.getOriginalFilename();
                    String newPosterPath = uploadDir + newPosterFileName;
                    posterImage.transferTo(new File(newPosterPath));

                    job.setPosterImage(newPosterPath);
                }

                // Save updated job
                Job updatedJob = jobRepository.save(job);

                // Convert to DTO
                JobDTO jobDTO = new JobDTO();
                jobDTO.setId(updatedJob.getId());
                jobDTO.setUuid(updatedJob.getUuid());
                jobDTO.setTitle(updatedJob.getTitle());
                jobDTO.setDescription(updatedJob.getDescription());
                jobDTO.setLocation(job.getLocation());
                jobDTO.setRate(job.getRate());
                jobDTO.setType(job.getType());
                jobDTO.setPosterImage(updatedJob.getPosterImage());
                jobDTO.setPostedDate(updatedJob.getPostedDate());
                jobDTO.setDeadline(updatedJob.getDeadline());

                // Map applications to DTOs
                List<JobApplicationDTO> applicationDTOs = updatedJob.getApplications().stream()
                        .map(application -> {
                            JobApplicationDTO appDTO = new JobApplicationDTO();
                            appDTO.setId(application.getId());
                            appDTO.setApplicantName(application.getApplicantName());
                            appDTO.setCv(application.getCv());
                            appDTO.setApplicationLetter(application.getApplicationLetter());
                            appDTO.setAvailableStartDate(application.getAvailableStartDate());
                            appDTO.setCurrentEmploymentStatus(application.getCurrentEmploymentStatus());
                            return appDTO;
                        })
                        .collect(Collectors.toList());

                jobDTO.setApplications(applicationDTOs);

                return ResponseEntity.ok(jobDTO);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        return jobRepository.findById(id).map(job -> {
            // Delete associated poster image if it exists
            String posterImagePath = job.getPosterImage();
            if (posterImagePath != null) {
                File file = new File(posterImagePath);
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("Deleted poster image: " + posterImagePath);
                    } else {
                        System.out.println("Failed to delete poster image: " + posterImagePath);
                    }
                }
            }

            // Delete the job
            jobRepository.delete(job);
            return ResponseEntity.ok("Job deleted successfully");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found"));
    }

}
