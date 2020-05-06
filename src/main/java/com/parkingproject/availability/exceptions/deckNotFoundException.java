package com.parkingproject.availability.exceptions;

public class deckNotFoundException extends RuntimeException
{
    public deckNotFoundException(int id)
    {
        super("Could not find deck: " + id);
    }
}
