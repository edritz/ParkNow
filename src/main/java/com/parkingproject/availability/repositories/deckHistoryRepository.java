package com.parkingproject.availability.repositories;

import com.parkingproject.availability.objects.deckHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface deckHistoryRepository extends CrudRepository<deckHistory, Integer>
{
    List<deckHistory> findAllByDeckIdAndDayCodeAndHourCode(Integer id, Integer dayCode, Integer hourCode);
    List<deckHistory> findAllByDeckIdAndDayCode(Integer id, Integer dayCode);
}
