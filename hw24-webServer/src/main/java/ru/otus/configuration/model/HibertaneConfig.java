package ru.otus.configuration.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HibertaneConfig {
    String dbUrl;
    String dbUserName;
    String dbPassword;
}
