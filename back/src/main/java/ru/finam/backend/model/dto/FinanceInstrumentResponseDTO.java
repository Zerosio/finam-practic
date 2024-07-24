package ru.finam.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Инструменты")
public class FinanceInstrumentResponseDTO {

    @Schema(description = "Тикер", name = "ticker", example = "SBER")
    private String ticker;

    @Schema(description = "Наименование", name = "name", example = "Сбербанк")
    private String name;

    @Schema(description = "Цена", name = "price", example = "320.20")
    private float price;

    @Schema(description = "Капитализация", name = "capitalization", example = "7000000000")
    private float capitalization;

    @Schema(description = "Ср. объем торгов", name = "averageTradingVolume", example = "0.5")
    private float averageTradingVolume;

}
