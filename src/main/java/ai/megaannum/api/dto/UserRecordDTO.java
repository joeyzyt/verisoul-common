package ai.megaannum.api.dto;

/**
 * Represents a user's profile record containing personal information such as email, date of birth, and major.
 */
public class UserRecordDTO {
    private String email;
    private String dateOfBirth;
    private String gender;
    private String major;
    private String description;

    public UserRecordDTO() {}

    public UserRecordDTO(String email, String dateOfBirth, String gender, String major, String description) {
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.major = major;
        this.description = description;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
