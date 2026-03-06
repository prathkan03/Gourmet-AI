package com.pratham.foodreview.backend.controller;

import com.pratham.foodreview.backend.dto.ProfileResponse;
import com.pratham.foodreview.backend.entity.Profile;
import com.pratham.foodreview.backend.repo.ProfileRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ProfileRepository profileRepository;

    public UserController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/search")
    public List<ProfileResponse> searchUsers(@RequestParam String q) {
        if (q == null || q.isBlank() || q.length() < 1) {
            return List.of();
        }
        List<Profile> profiles = profileRepository.findByUsernamePrefix(
            q.trim(), PageRequest.of(0, 20)
        );
        return profiles.stream().map(p -> new ProfileResponse(
            p.getId().toString(),
            p.getUsername(),
            p.getDisplayName(),
            p.getAvatarUrl(),
            p.getBio(),
            0L, 0L, 0L
        )).toList();
    }
}
