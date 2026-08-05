package com.thghpi.bandapp.band_api.dto.request;

public record VoteRequest(
    Long surveyId,
    Long choiceId
) { }
