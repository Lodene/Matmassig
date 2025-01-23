package com.school.matmassig.recipeservice.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewFromRecipeIAMessage {
    private String reviewId; // Identifiant unique de la critique
    private String recipeIAId; // Identifiant unique de la recette
    private String authorId; // Identifiant unique de l'auteur
    private String authorName; // Nom de l'auteur
    private String rating; // Note donnée à la recette (ex: "5/5")
    private String review; // Contenu de la critique
    private LocalDateTime dateSubmitted; // Date de soumission de la critique
    private LocalDateTime dateModified; // Date de modification de la critique
    private String email; // Adresse email de l'auteur

    // constructeur
    public ReviewFromRecipeIAMessage(String reviewId, String recipeIAId, String authorId, String authorName,
            String rating, String review, LocalDateTime dateSubmitted, LocalDateTime dateModified) {
        this.reviewId = reviewId;
        this.recipeIAId = recipeIAId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.rating = rating;
        this.review = review;
        this.dateSubmitted = dateSubmitted;
        this.dateModified = dateModified;
    }

    // getters et setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getrecipeIAId() {
        return recipeIAId;
    }

    public void setrecipeIAId(String recipeIAId) {
        this.recipeIAId = recipeIAId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

}
