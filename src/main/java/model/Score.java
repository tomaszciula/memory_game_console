package model;

import java.time.LocalDate;

public record Score(String name, LocalDate date, long guessing_time, int guessing_tries ) {
}
