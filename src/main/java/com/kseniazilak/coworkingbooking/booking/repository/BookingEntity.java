package com.kseniazilak.coworkingbooking.booking.repository;

import com.kseniazilak.coworkingbooking.booking.BookingStatus;
import com.kseniazilak.coworkingbooking.user.repository.UserEntity;
import com.kseniazilak.coworkingbooking.workspace.repository.WorkspaceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private WorkspaceEntity workspace;

    @Column(name = "start_time")
    private Instant start;

    @Column(name = "end_time")
    private Instant end;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
