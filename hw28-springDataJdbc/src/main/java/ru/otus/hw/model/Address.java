package ru.otus.hw.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public record Address(@Column("street") @Nonnull String street, @Column("address_id") Long addressId) {}
