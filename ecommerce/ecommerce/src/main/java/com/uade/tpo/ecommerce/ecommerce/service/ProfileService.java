package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ProfileDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.ProfileRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO getProfileById(Long id) throws Exception {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new Exception("Profile not found"));
        return convertToDTO(profile);
    }

    public ProfileDTO updateProfile(Long id, ProfileDTO profileDTO) throws Exception {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new Exception("Profile not found"));

        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setEmail(profileDTO.getEmail());
        profile.setDateOfBirth(profileDTO.getDateOfBirth());

        profileRepository.save(profile);
        return convertToDTO(profile);
    }

    private ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setDateOfBirth(profile.getDateOfBirth());
        return profileDTO;
    }
}
