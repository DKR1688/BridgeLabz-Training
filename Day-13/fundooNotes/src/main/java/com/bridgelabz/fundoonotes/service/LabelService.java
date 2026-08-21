package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LabelService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public LabelService(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    public Tag createLabel(int userId, String labelName) {
        User owner = getOwnerOrThrow(userId);
        String cleanName = labelName != null ? labelName.trim() : "";
        if (cleanName.isEmpty()) {
            throw new IllegalArgumentException("Label name cannot be empty");
        }

        // Custom Service-layer per-user uniqueness check
        Optional<Tag> existing = tagRepository.findByNameAndOwner(cleanName, owner);
        if (existing.isPresent()) {
            Tag tag = existing.get();
            if (tag.isDeleted()) {
                // Reactivate soft-deleted label
                tag.setDeleted(false);
                return tagRepository.save(tag);
            }
            throw new IllegalArgumentException("Label with name '" + cleanName + "' already exists for this user");
        }

        Tag newTag = new Tag(cleanName, owner);
        newTag.setDeleted(false);
        return tagRepository.save(newTag);
    }

    public Tag updateLabel(int labelId, int userId, String newName) {
        User owner = getOwnerOrThrow(userId);
        Tag tag = tagRepository.findByTagIdAndOwner(labelId, owner)
                .orElseThrow(() -> new IllegalArgumentException("Label not found"));

        String cleanName = newName != null ? newName.trim() : "";
        if (cleanName.isEmpty()) {
            throw new IllegalArgumentException("Label name cannot be empty");
        }

        // Check if another active label with the same name exists for this user
        Optional<Tag> duplicate = tagRepository.findByNameAndOwner(cleanName, owner);
        if (duplicate.isPresent() && duplicate.get().getTagId() != labelId && !duplicate.get().isDeleted()) {
            throw new IllegalArgumentException("Label with name '" + cleanName + "' already exists for this user");
        }

        tag.setName(cleanName);
        return tagRepository.save(tag);
    }

    public boolean softDeleteLabel(int labelId, int userId) {
        User owner = getOwnerOrThrow(userId);
        return tagRepository.findByTagIdAndOwner(labelId, owner)
                .map(tag -> {
                    tag.setDeleted(true);
                    tagRepository.save(tag);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<Tag> getActiveLabels(int userId) {
        User owner = getOwnerOrThrow(userId);
        return tagRepository.findByOwnerAndIsDeletedFalse(owner);
    }

    @Transactional(readOnly = true)
    public Optional<Tag> getLabelById(int labelId, int userId) {
        User owner = getOwnerOrThrow(userId);
        return tagRepository.findByTagIdAndOwner(labelId, owner);
    }

    private User getOwnerOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
