package com.parkingproject.availability.services;

import com.parkingproject.availability.exceptions.deckNotFoundException;
import com.parkingproject.availability.objects.deck;
import com.parkingproject.availability.objects.deckHistory;
import com.parkingproject.availability.repositories.DeckRepository;
import com.parkingproject.availability.repositories.deckHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistoryService
{
    @Autowired
    private deckHistoryRepository deckHistoryRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Scheduled(cron = "0 */15 * * * *")
    public void updateHistory()
    {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Iterable<deck> dlist = deckRepository.findAll();
        for(deck d: dlist)
        {
            addHistory(d.getId(), dayOfWeek, hour);
        }
    }

    //@PostMapping(path = "/add") // Map ONLY POST Requests
    public
    String addHistory(Integer deckId, Integer dayCode, Integer hourCode)
    {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Date creation = new Date();

        deck db_deck = deckRepository.findById(deckId).orElseThrow(() -> new deckNotFoundException(deckId));
        deckHistory n = new deckHistory();
        n.setDeckId(db_deck.getId());
        n.setSpaceLeft(db_deck.getCapacity() - db_deck.getOccupancy());
        n.setDayCode(dayCode);
        n.setHourCode(hourCode);
        //n.setTime()
        deckHistoryRepository.save(n);
        return "Saved";
    }

}
