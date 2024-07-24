package ru.finam.backend.model.entities;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "finance_instruments")
public class FinanceInstrumentEntity implements Serializable {
    @Id
    @Column(name = "finance_instrument_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "firm_id")
    private FirmEntity firm;

    @ManyToOne
    @JoinColumn(name = "instrument_type_id")
    private InstrumentTypeEntity instrumentType;

    @Column(name = "average_trading_volume")
    private float averageTradingVolume;

    @Column(name = "price")
    private float price;
}
