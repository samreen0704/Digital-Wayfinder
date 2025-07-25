package com.example.DigitalWayfinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DigitalWayfinder.dto.ProjectInfoRequest;
import com.example.DigitalWayfinder.dto.ProjectInfoResponse;
import com.example.DigitalWayfinder.entity.ProjectType;
import com.example.DigitalWayfinder.repository.ProjectTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectInfoService {
    
    private final ProjectTypeRepository projectTypeRepository;
    
    public ProjectInfoResponse saveOrUpdateProjectInfo(ProjectInfoRequest request, String userID, String sessionID) {
        try {
            log.info("Processing project info for request ID: {} by user: {} in session: {}",
                     request.getRequestID(), userID, sessionID);
            
            // Check if a record exists for the same user and session
            Optional<ProjectType> existingProject = projectTypeRepository.findByUserIDAndSessionID(userID, sessionID);
            
            ProjectType projectType;
            boolean isUpdate = false;
            
            if (existingProject.isPresent()) {
                // Update existing record
                projectType = existingProject.get();
                
                // Update the existing entity
                projectType.setProjectType(request.getProjectType());
                projectType.setRequestID(request.getRequestID());
                projectType.setClientName(request.getClientName());
                projectType.setClientDescription(request.getClientDescription());
                projectType.setProjectScope(request.getProjectScope());
                // Note: Don't update createdDate, userID, or sessionID
                
                isUpdate = true;
                log.info("Updating existing project with ID: {}", projectType.getId());
                
            } else {
                // Create new entity
                projectType = new ProjectType(
                    userID,
                    request.getProjectType(),
                    request.getRequestID(),
                    request.getClientName(),
                    request.getClientDescription(),
                    request.getProjectScope(),
                    sessionID
                );
                
                log.info("Creating new project record");
            }
            
            // Save to database (works for both insert and update)
            ProjectType savedProject = projectTypeRepository.save(projectType);
            
            String message = isUpdate ? "Project information updated successfully" : "Project information saved successfully";
            log.info("{} with ID: {}", message, savedProject.getId());
            
            // Create response
            ProjectInfoResponse response = ProjectInfoResponse.success(message);
            response.setUpdated(isUpdate); // Add this field to track if it was an update
            response.setId(savedProject.getId());
            response.setUserID(savedProject.getUserID());
            response.setProjectType(savedProject.getProjectType());
            response.setRequestID(savedProject.getRequestID());
            response.setClientName(savedProject.getClientName());
            response.setClientDescription(savedProject.getClientDescription());
            response.setProjectScope(savedProject.getProjectScope());
            response.setCreatedDate(savedProject.getCreatedDate());
            response.setSessionID(savedProject.getSessionID());
            
            return response;
            
        } catch (Exception e) {
            log.error("Failed to save/update project information: {}", e.getMessage(), e);
            return ProjectInfoResponse.error("Failed to save/update project information: " + e.getMessage());
        }
    }

        public List<ProjectType> getAllProjects() {
        log.info("Fetching all functional areas");
        return projectTypeRepository.findAll();
    }

    // public ProjectInfoResponse getProjectInfoBySession(String sessionId) {
    //     try {
    //         log.info("Fetching project info for session: {}", sessionId);
            
    //         // Get the latest project info for the session (assuming you want the most recent one)
    //         Optional<ProjectType> projectInfoOpt = projectTypeRepository.findBySessionID(sessionId);
            
    //         if (projectInfoOpt.isPresent()) {
    //             ProjectType projectInfo = projectInfoOpt.get();
    //             log.info("Found project info with ID: {} for session: {}", 
    //                     projectInfo.getId(), sessionId);
                
    //             return ProjectInfoResponse.builder()
    //                     // .success(true)
    //                     // .message("Project info retrieved successfully")
    //                     .requestID(projectInfo.getRequestID())
    //                     .projectName(projectInfo.getClientName())
    //                     .projectDescription(projectInfo.getClientDescription())
    //                     .projectType(projectInfo.getProjectType())
    //                     .estimatedBudget(projectInfo.getProjectScope())
    //                     .build();
    //         } else {
    //             log.warn("No project info found for session: {}", sessionId);
    //             return ProjectInfoResponse.builder()
    //                     .success(false)
    //                     .message("No project information found for session: " + sessionId)
    //                     .build();
    //         }
            
    //     } catch (Exception e) {
    //         log.error("Error retrieving project info for session: {}", sessionId, e);
    //         return ProjectInfoResponse.builder()
    //                 .success(false)
    //                 .message("Error retrieving project information: " + e.getMessage())
    //                 .build();
    //     }
    // }
    /**
     * Retrieve all project information for a particular session ID.
    //  */
    // public List<ProjectType> getAllProjectInfoBySession(String sessionId) {
    //     log.info("Fetching all project info for session: {}", sessionId);
    //     return projectTypeRepository.findAll().stream()
    //             .filter(p -> sessionId.equals(p.getSessionID()))
    //             .toList();
    // }
}
    
    