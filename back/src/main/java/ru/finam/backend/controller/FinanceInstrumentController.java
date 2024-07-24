package ru.finam.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.finam.backend.model.dto.FinanceInstrumentRequestDTO;
import ru.finam.backend.model.dto.FinanceInstrumentResponseDTO;
import ru.finam.backend.service.FinanceInstrumentService;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/firms")
@Tag(
        name = "Контроллер скринера"
)
@Validated
@Slf4j
public class FinanceInstrumentController {

    private final FinanceInstrumentService financeInstrumentService;

    @Operation(
            summary = "Получение инструментов",
            description = "Получения инструментов по фильтрам"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = @Content(mediaType = "application/json", examples = { @ExampleObject(
                            value = "{\"message\": \"Данной страницы не существует\", \"debugMessage\":\"" +
                                    "Данной страницы не существует\"}") })),
            @ApiResponse(responseCode = "400", description = "Неправильное значение числа элементов страницы",
                    content = @Content(mediaType = "application/json", examples = { @ExampleObject(
                            value = "{\"message\": \"В данное поле неккоретно введены данные\", \"debugMessage\":\"" +
                                    "В данное поле неккоретно введены данные: негативные числа," +
                                    " буквы в поля для чисел и т.д.\"}") })),
            @ApiResponse(responseCode = "200", description = "ОК")
    })
    @PostMapping("/finance_instruments")
    public ResponseEntity<Page<FinanceInstrumentResponseDTO>> getFinanceInstruments(
            @Valid @RequestBody FinanceInstrumentRequestDTO filter,

            @Parameter(description = "Смещение")
            @Min(0) @Max(Integer.MAX_VALUE)
            @RequestParam(defaultValue = "0")
            int offset,


            @Parameter(description = "Количество записей")
            @Min(1) @Max(100)
            @RequestParam(defaultValue = "10")
            int limit

    ) {
        try {
            Page<FinanceInstrumentResponseDTO> response = financeInstrumentService.getFinanceInstruments(filter, offset, limit);
            log.info("Instrument {} was found", filter);
            return ResponseEntity.ok(response);
        } catch (IllegalAccessError e) {
            log.error("Instrument {} was not found", filter);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
