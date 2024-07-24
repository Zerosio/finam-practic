package ru.finam.backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ru.finam.backend.model.dto.FinanceInstrumentRequestDTO;
import ru.finam.backend.model.dto.FinanceInstrumentResponseDTO;
import ru.finam.backend.model.entities.FinanceInstrumentEntity;

import ru.finam.backend.validation.ValidationService;

@Service
@RequiredArgsConstructor
public class FinanceInstrumentService {

    private final ApplicationUtils applicationUtils;
    private final ValidationService validationService;
    private final RedisTemplate<String, Object> redisTemplate;
    @PersistenceContext
    private final EntityManager em;

    private static final String KEY = "INSTRUMENTS";


    public Page<FinanceInstrumentResponseDTO> getFinanceInstruments(
            FinanceInstrumentRequestDTO filter, int offset, int limit) throws IndexOutOfBoundsException,
            IllegalArgumentException {
        // проверка полей DTO на валидность
        validationService.checkRequestDTOFieldsAreValid(filter);

        // Получение отфильтрованных данных либо из кэша, либо из БД
        List<FinanceInstrumentEntity> entitylist = getInstruments(filter);

        // проверка offset и limit на валидность
        validationService.checkOffsetAndLimitAreValid(offset, limit, entitylist.size());

        // Преобразование списка сущностей в список responseDTO
        List<FinanceInstrumentResponseDTO> responseDTOList =
                applicationUtils.mapToFinanceInstrumentResponseDTOList(entitylist);

        // перевод в Page<FinanceInstrumentResponseDTO> и возвращение
        return applicationUtils.convertListToPage(responseDTOList, offset, limit);
    }

    private List<FinanceInstrumentEntity> getInstruments(FinanceInstrumentRequestDTO filter){
        String tickerName = filter.getTickerName();
        List<FinanceInstrumentEntity> entityList;

        
        if (redisTemplate.opsForHash().hasKey(KEY, tickerName)) {
            entityList = (List<FinanceInstrumentEntity>) redisTemplate.opsForHash().get(KEY, tickerName);
        } else{
            entityList = getInstrumentsByTickerOrNameFromDB(tickerName);
        }
        //entityList = getInstrumentsByTickerOrNameFromDB(tickerName);

        List<FinanceInstrumentEntity> filteredEntityList = filterInstruments(filter, entityList);

        
        if(!tickerName.isEmpty() && !redisTemplate.opsForHash().hasKey(KEY, tickerName)){
            redisTemplate.opsForHash().put(KEY, tickerName, filteredEntityList);
        }

        return sortInstruments(filter.getSortBy(), filter.getSortOrder(), filteredEntityList);
    }

    private List<FinanceInstrumentEntity> getInstrumentsByTickerOrNameFromDB(String tickerName){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FinanceInstrumentEntity> cr =
                cb.createQuery(FinanceInstrumentEntity.class);
        Root<FinanceInstrumentEntity> root =
                cr.from(FinanceInstrumentEntity.class);

        root.fetch("firm").fetch("sector");
        root.fetch("instrumentType");

        if (!tickerName.isEmpty()) {
            cr.where(cb.or(
                    cb.like(cb.upper(root.get("firm").get("ticker")), cb.upper(cb.literal("%" + tickerName + "%"))),
                    cb.like(cb.upper(root.get("firm").get("name")), cb.upper(cb.literal("%" + tickerName + "%")))
            ));
        }

        cr.select(root);

        return em.createQuery(cr).getResultList();

    }

    private List<FinanceInstrumentEntity> filterInstruments(FinanceInstrumentRequestDTO filter,
                                                            List<FinanceInstrumentEntity> l){

        Predicate<FinanceInstrumentEntity> filterPredicate = fi -> {
            return fi.getFirm().getSector().getName().equals(filter.getSector()) &&
                    fi.getInstrumentType().getName().equals(filter.getType()) &&
                    applicationUtils.isInRange(fi.getPrice(), filter.getPriceFrom(), filter.getPriceUpTo()) &&
                    applicationUtils.isInRange(fi.getAverageTradingVolume(), filter.getVolumeFrom(),
                            filter.getVolumeUpTo()) &&
                    applicationUtils.isInRange(fi.getFirm().getCapitalization(), filter.getCapitalizationFrom(),
                            filter.getCapitalizationUpTo());
        };

        return l.parallelStream().filter(filterPredicate).collect(Collectors.toList());
    }

    private List<FinanceInstrumentEntity> sortInstruments(String sortBy, String sortOrder,
                                                            List<FinanceInstrumentEntity> l){
        List<FinanceInstrumentEntity> sortedEntityList = new ArrayList<>();

        switch (sortBy) {
            case "price" -> sortedEntityList = l.parallelStream().sorted((fi1, fi2) -> {
                return sortOrder.equals("asc") ? Float.compare(fi1.getPrice(), fi2.getPrice()) :
                        Float.compare(fi2.getPrice(), fi1.getPrice());
            }).toList();
            case "averageTradingVolume" -> sortedEntityList = l.parallelStream().sorted((fi1, fi2) -> {
                return sortOrder.equals("asc") ?
                        Float.compare(fi1.getAverageTradingVolume(), fi2.getAverageTradingVolume()) :
                        Float.compare(fi2.getAverageTradingVolume(), fi1.getAverageTradingVolume());
            }).toList();
            case "capitalization" -> sortedEntityList = l.parallelStream().sorted((fi1, fi2) -> {
                return sortOrder.equals("asc") ?
                        Float.compare(fi1.getFirm().getCapitalization(), fi2.getFirm().getCapitalization()) :
                        Float.compare(fi2.getFirm().getCapitalization(), fi1.getFirm().getCapitalization());
            }).toList();
            case "ticker" -> sortedEntityList = l.parallelStream().sorted((fi1, fi2) -> {
                return sortOrder.equals("asc") ?
                        fi1.getFirm().getTicker().compareTo(fi2.getFirm().getTicker()) :
                        fi2.getFirm().getTicker().compareTo(fi1.getFirm().getTicker());
            }).toList();
            case "name" -> sortedEntityList = l.parallelStream().sorted((fi1, fi2) -> {
                return sortOrder.equals("asc") ?
                        fi1.getFirm().getName().compareTo(fi2.getFirm().getName()) :
                        fi2.getFirm().getName().compareTo(fi1.getFirm().getName());
            }).toList();
            default -> throw new IllegalArgumentException("Данного поля для сортировки не существует : " + sortBy);
        }

        return sortedEntityList;
    }
}
