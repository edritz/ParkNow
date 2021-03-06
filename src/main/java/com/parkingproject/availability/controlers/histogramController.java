package com.parkingproject.availability.controlers;

import com.parkingproject.availability.objects.Hour;
import com.parkingproject.availability.objects.deckHistory;
import com.parkingproject.availability.repositories.deckHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/histogram")
public class histogramController
{
    @Autowired
    private deckHistoryRepository deckHistoryRepository;

    @GetMapping(path = "/{id}")
    public @ResponseBody
    List<Hour> getHistogram(@PathVariable Integer id)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        //List<deckHistory> history = deckHistoryRepository.findAllByDeckIdAndDayCode(id, dayOfWeek);
        ArrayList<Hour> histogram = new ArrayList<>();
        for(int i = 13; i < 22; i++)
        {
            List<deckHistory> history = deckHistoryRepository.findAllByDeckIdAndDayCodeAndHourCode(id, dayOfWeek, i);
            if(history.isEmpty())
            {
                continue;
            }
            Integer average = 0;
            for(deckHistory dh : history)
            {
                average += dh.getSpaceLeft();
            }
            histogram.add(new Hour(i, average/history.size()));
        }
        return histogram;
    }
}
