package ru.finam.backend.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "instrument_types")
public class InstrumentTypeEntity implements Serializable {
    @Id
    @Column(name = "instrument_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "instrument_type")
    private String name;

    @OneToMany(mappedBy = "instrumentType")
    @ToString.Exclude
    @JsonIgnore
    private List<FinanceInstrumentEntity> financeInstrumentList;
}