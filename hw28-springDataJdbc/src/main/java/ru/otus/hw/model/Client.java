package ru.otus.hw.model;

import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
public record Client(
        @Id Long id,
        @Column("name") @Nonnull String name,
        @MappedCollection(idColumn = "address_id") Address address,
        @MappedCollection(idColumn = "phone_id", keyColumn = "number") List<Phone> phones) {}
