package ru.finam.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Фильтры", description = "Выбирается пользователем")
public class FinanceInstrumentRequestDTO {

    @NotNull
    @Schema(description = "Наименование", name = "tickerName", example = "SBER")
    private String tickerName;

    @NotNull
    @Schema(description = "Тип инструмента", name = "type", example = "Акции")
    private String type;

    @NotNull
    @Schema(description = "Сектор", name = "sector", example = "Финансы")
    private String sector;

    @PositiveOrZero
    @Digits(integer = 30,fraction = 8)
    @Schema(description = "Цена от", name = "priceFrom", example = "0")
    private float priceFrom;

    @Positive
    @Digits(integer = 30,fraction = 8)
    @Schema(description = "Цена до", name = "priceUpTo", example = "300000")
    private float priceUpTo;

    @PositiveOrZero
    @Digits(integer = 30,fraction = 8)
    @Schema(description = "Капитализация от", name = "capitalizationFrom", example = "0")
    private float capitalizationFrom;

    @Positive
    @Digits(integer = 30,fraction = 8)
    @Schema(description = "Капитализация до", name = "capitalizationUpTo", example = "7000000000000")
    private float capitalizationUpTo;

    @PositiveOrZero
    @Digits(integer = 30,fraction = 8)
    @Schema(description = "Средний объем торгов от", name = "volumeFrom", example = "0")
    private float volumeFrom;

    @Positive
    @Digits(integer = 30,fraction = 8)
    @Schema(description = "Средний объем торгов до", name = "volumeUpTo", example = "1000000000000000")
    private float volumeUpTo;

    @Schema(description = "Сортировать по ", name = "sortBy", example = "price")
    @NotNull
    private String sortBy;

    @Schema(description = "Порядок сортировки", name = "sortOrder", example = "asc")
    @NotNull
    private String sortOrder;

}
