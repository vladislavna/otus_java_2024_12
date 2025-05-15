package ru.otus.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        if (phones != null) {
            phones.stream().forEach(phone -> phone.setClient(this));
            this.phones.addAll(phones);
        }
    }

    public Client(String name, Address address, List<Phone> phones) {
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.phones.forEach(phone -> phone.setClient(this));
    }

    @Override
    @SuppressWarnings({"java:S1182", "java:S2975"})
    public Client clone() {
        return new Client(
                this.id,
                this.name,
                this.address == null ? null : this.address.clone(),
                this.phones == null
                        ? null
                        : this.phones.stream()
                                .map(phone -> new Phone(phone.getId(), phone.getNumber(), this))
                                .toList());
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + ", address=" + address + ", phones=" + phones + '}';
    }
}
