package com.gmail.lairmartes.shyftlab.result.rest.dto;

import com.gmail.lairmartes.shyftlab.result.domain.Result;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResultDTO {

    private Long id;
    private Long studentId;
    private String studentFullName;
    private String studentEmail;
    private Long courseId;
    private String courseName;
    private String score;

    public Long getId() {
        return id == null ? 0L : id;
    }

    public Long getStudentId() {
        return studentId == null ? 0L : studentId;
    }

    public Long getCourseId() {
        return courseId == null ? 0L : courseId;
    }

    public Result toDomain() {
        return Result.builder()
                .id(getId())
                .studentId(getStudentId())
                .courseId(getCourseId())
                .score(score)
                .build();
    }

    public static ResultDTO fromDomain(Result result) {
        return ResultDTO
                .builder()
                .id(result.getId())
                .studentId(result.getStudentId())
                .studentFullName(String.format("%s %s",
                        result.getStudent().getFirstName(), result.getStudent().getFamilyName()))
                .studentEmail(result.getStudent().getEmail())
                .courseId(result.getCourseId())
                .courseName(result.getCourse().getName())
                .score(result.getScore())
                .build();
    }
}
