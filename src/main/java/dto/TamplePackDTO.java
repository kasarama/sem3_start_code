/*
 * CA3
 */
package dto;

/**
 *
 * @author magda
 */
public class TamplePackDTO {
    private String category;
    private String carId;
    private String employeeId;
    private String commentId;

    public TamplePackDTO(String category, String carId, String employeeId, String commentId) {
        this.category = category;
        this.carId = carId;
        this.employeeId = employeeId;
        this.commentId = commentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    
}
