package com.parkingproject.availability.objects;

import lombok.Data;

@Data
public class Hour
{
    private Integer hour;

    private Integer average;

    public Hour(Integer hour, Integer average)
    {
        this.hour = hour;
        this.average = average;
    }
}
