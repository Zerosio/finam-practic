package ru.finam.backend.validation;

import org.springframework.stereotype.Service;
import ru.finam.backend.exceptions.IllegalPageLimitException;
import ru.finam.backend.exceptions.PageIndexOutOfBoundException;
import ru.finam.backend.model.dto.FinanceInstrumentRequestDTO;

@Service
public class ValidationService {

    public void checkOffsetAndLimitAreValid(int offset, int limit, int listSize){
        if (offset < 0 || (offset * limit >= listSize && listSize != 0) )
            throw new PageIndexOutOfBoundException(offset);
        if (limit < 1)
            throw new IllegalPageLimitException(limit);
    }

    public void checkRequestDTOFieldsAreValid(FinanceInstrumentRequestDTO filter){
        if( Float.compare(filter.getPriceFrom(), filter.getPriceUpTo()) > -1) {
            throw new IllegalArgumentException("Некорректные данные для полей : priceFrom, priceUpTo");
        }
        if(   Float.compare(filter.getCapitalizationFrom(), filter.getCapitalizationUpTo()) > -1){
            throw new IllegalArgumentException("Некорректные данные для полей : " +
                    "capitalizationFrom, capitalizationUpTo");
        }
        if( Float.compare(filter.getVolumeFrom(), filter.getVolumeUpTo()) > -1){
            throw new IllegalArgumentException("Некорректные данные для полей : volumeFrom, volumeUpTo");
        }
        if( !filter.getSortBy().isEmpty() &&
                !(filter.getSortOrder().equals("asc") || filter.getSortOrder().equals("desc")) ){
            throw new IllegalArgumentException("Некорректное значение для поля sortOrder");
        }
    }
}
