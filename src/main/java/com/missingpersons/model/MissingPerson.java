package com.missingpersons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "missing_persons")
@Data

public class MissingPerson {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Name is required")
	    @Size(max = 100, message = "Name must be less than 100 characters")
	    @Column(nullable = false, length = 100)
	    private String name;

	    @NotNull(message = "Age is required")
	    @Min(value = 0, message = "Age must be positive")
	    @Max(value = 120, message = "Age must be reasonable")
	    @Column(nullable = false)
	    private Integer age;

	    @NotNull(message = "Last seen date is required")
	    @Column(name = "last_seen", nullable = false)
	    private LocalDate lastSeen;

	    @NotBlank(message = "Location is required")
	    @Size(max = 200, message = "Location must be less than 200 characters")
	    @Column(nullable = false, length = 200)
	    private String location;

	    @Column(length = 1000)
	    @Size(max = 1000, message = "Description must be less than 1000 characters")
	    private String description;

	    @NotBlank(message = "Contact information is required")
	    @Size(max = 100, message = "Contact must be less than 100 characters")
	    @Column(nullable = false, length = 100)
	    private String contact;

	    @Column(name = "case_number", length = 50)
	    private String caseNumber;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, length = 20)
	    private Status status = Status.MISSING;

	    @Column(name = "photo_url", length = 500)
	    private String photoUrl;

	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }

	    public enum Status {
	        MISSING, FOUND
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public LocalDate getLastSeen() {
			return lastSeen;
		}

		public void setLastSeen(LocalDate lastSeen) {
			this.lastSeen = lastSeen;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getCaseNumber() {
			return caseNumber;
		}

		public void setCaseNumber(String caseNumber) {
			this.caseNumber = caseNumber;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}

		public String getPhotoUrl() {
			return photoUrl;
		}

		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		@Override
		public String toString() {
			return "MissingPerson [id=" + id + ", name=" + name + ", age=" + age + ", lastSeen=" + lastSeen
					+ ", location=" + location + ", description=" + description + ", contact=" + contact
					+ ", caseNumber=" + caseNumber + ", status=" + status + ", photoUrl=" + photoUrl + ", createdAt="
					+ createdAt + ", updatedAt=" + updatedAt + "]";
		}

		public MissingPerson(Long id, String name, Integer age, LocalDate lastSeen, String location, String description,
				String contact, String caseNumber, Status status, String photoUrl, LocalDateTime createdAt,
				LocalDateTime updatedAt) {
			super();
			this.id = id;
			this.name = name;
			this.age = age;
			this.lastSeen = lastSeen;
			this.location = location;
			this.description = description;
			this.contact = contact;
			this.caseNumber = caseNumber;
			this.status = status;
			this.photoUrl = photoUrl;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}

		public MissingPerson() {
			super();
			// TODO Auto-generated constructor stub
		}

}
