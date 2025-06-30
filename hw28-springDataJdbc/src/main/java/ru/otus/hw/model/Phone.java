package ru.otus.hw.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public record Phone(@Id Long id, @Column("number") @Nonnull String number, @Column("phone_id") Long phoneId) {}
