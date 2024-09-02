package de.ait_tr.g_36_shop.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirm_code")
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "expired")
    private LocalDateTime expired;

    @ManyToOne
    private User user;

    public ConfirmationCode() {
    }

    public ConfirmationCode(String code, LocalDateTime expired, User user) {
        this.code = code;
        this.expired = expired;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfirmationCode that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(expired, that.expired) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, expired, user);
    }

    @Override
    public String toString() {
        return String.format("Confirm code: id - %d, code - %s, username - %s, expired - %s",
                id, code, user == null ? "empty" : user.getUsername(), expired);
    }

}
