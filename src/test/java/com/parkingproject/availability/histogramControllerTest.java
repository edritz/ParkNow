package com.parkingproject.availability;

import com.parkingproject.availability.exceptions.deckNotFoundAdvice;
import com.parkingproject.availability.objects.Hour;
import com.parkingproject.availability.objects.deckHistory;
import com.parkingproject.availability.repositories.DeckRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(SpringRunner.class)
public class histogramControllerTest
{
    @Autowired
    private com.parkingproject.availability.controlers.histogramController histogramController;

    @MockBean
    DeckRepository deckRepository;

    @MockBean
    com.parkingproject.availability.repositories.deckHistoryRepository deckHistoryRepository;

    private MockMvc mockMvc;

    private deckHistory dh = new deckHistory();

    private ArrayList<deckHistory> hlist = new ArrayList<>();

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(histogramController)
                .setControllerAdvice(new deckNotFoundAdvice()).build();
        ArrayList<deckHistory> dh = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        for(int j = 0; j < 5; j++)
        {
            deckHistory filler = new deckHistory();
            filler.setEventId(j);
            filler.setDeckId(1);
            filler.setSpaceLeft(1200 - j * 10);
            filler.setDayCode(dayOfWeek);
            filler.setHourCode(hour);
            hlist.add(filler);
        }

        //hlist.add(new(Hour()))
    }

    @Test
    public void getHistogramTest()
    {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Mockito.when(deckHistoryRepository.findAllByDeckIdAndDayCodeAndHourCode(1, dayOfWeek, hour)).thenReturn(hlist);
        List<Hour> output = histogramController.getHistogram(1);
        assertEquals(0 , output.size());
    }

    @Test
    public void getHistogramEmptyHistory()
    {
        //Mockito.when(deckHistoryRepository.findAllByDeckIdAndDayCodeAndHourCode(id, dayOfWeek, i)).thenReturn(hlist);
        List<Hour> output = histogramController.getHistogram(1);
        assertEquals(output.isEmpty(), hlist.isEmpty());
    }

    @Test
    public void getHistogramNoSuchDeck()
    {
        //Mockito.when(deckHistoryRepository.findAllByDeckIdAndDayCodeAndHourCode(id, dayOfWeek, i)).thenReturn(hlist);
        List<Hour> output = histogramController.getHistogram(654);
        assertEquals(output.isEmpty(), hlist.isEmpty());
    }
}