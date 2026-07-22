package com.UserService.UserService.controller;

import com.UserService.UserService.dto.UpdateProfileRequest;
import com.UserService.UserService.dto.UserProfileDTO;
import com.UserService.UserService.model.UserProfile;
import com.UserService.UserService.model.UserStats;
import com.UserService.UserService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "User Profile API", description = "Endpoints for user profile creation, retrieval, update, stats, and deletion")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get current user profile",
            description = "Returns the profile for the user identified by the X-USER-ID header.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Current user profile returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserProfile> getCurrentUser(
            @Parameter(description = "UUID of the current authenticated user", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @RequestHeader("X-USER-ID") UUID userId) {

        return ResponseEntity.ok(userService.getCurrentUser(userId));
    }

    @Operation(
            summary = "Get user profile by ID",
            description = "Returns a public user profile for the requested user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<UserProfile> getUser(
            @Parameter(description = "UUID of the target user", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Update an existing user profile",
            description = "Updates the user profile fields specified in the request body.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile update payload",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateProfileRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PatchMapping("/update")
    public ResponseEntity<UserProfile> updateProfile(
            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(userService.updateProfile(request.getId(), request));
    }

    @Operation(
            summary = "Get user statistics",
            description = "Returns the statistics for the specified user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User statistics returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserStats.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/{id}/stats")
    public ResponseEntity<UserStats> getStats(
            @Parameter(description = "UUID of the target user", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {

        return ResponseEntity.ok(userService.getUserStats(id));
    }

    @Operation(
            summary = "Create a new user profile",
            description = "Creates a new user profile from the provided payload.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New user profile payload",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfile.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserProfile> createProfile(
            @RequestBody UserProfileDTO userProfile) {
        System.out.println(userProfile.toString());
        return ResponseEntity.ok(userService.createProfile(userProfile));
    }

    @Operation(
            summary = "Delete a user profile",
            description = "Deletes the user profile with the given ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "UUID of the user to delete", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {

        userService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update user stats",
            description = "Increments the user's statistics based on whether the submission was accepted.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated user statistics",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserStats.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PatchMapping("/{id}/stats")
    public ResponseEntity<UserStats> updateStats(
            @Parameter(description = "UUID of the target user", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id,
            @Parameter(description = "Whether the submission was accepted", required = true, example = "true")
            @RequestParam boolean accepted) {

        return ResponseEntity.ok(userService.updateStats(id, accepted));
    }

    @Operation(
            summary = "Get user profile by username",
            description = "Returns a user profile matching the provided username.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserByUsername(
            @Parameter(description = "Username of the target user", required = true, example = "coder123")
            @PathVariable String username) {

        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @Operation(
            summary = "Get all users",
            description = "Returns a list of all user profiles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = UserProfile.class)))
            }
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

}
