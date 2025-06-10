package com.sparta.authservice.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected BaseEntity() {}
public abstract class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(insertable = false)
    private Long updatedBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    private boolean deleted;

    public void create(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void update(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void delete() {
        this.deleted = true;
    }
}
