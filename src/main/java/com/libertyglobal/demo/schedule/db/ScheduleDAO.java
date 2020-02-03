package com.libertyglobal.demo.schedule.db;

import com.libertyglobal.demo.schedule.model.Program;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScheduleDAO {
    private final List<Program> schedule;

    public ScheduleDAO(List<Program> schedule) {
        this.schedule = schedule;
    }

    public Optional<Program> get(String id) {
        return schedule.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Set<String> getPrograms() {
        return schedule.stream().map(Program::getName).collect(Collectors.toSet());
    }

    /**
     * Method that filters schedule by date and by list of words.
     *
     * @param date
     * @param words
     * @return
     */
    public List<Program> getByDateAndWords(LocalDate date, List<String> words) {
        Stream<Program> programStream = schedule.stream();

        if (date != null) {
            programStream = programStream.filter(p -> p.getDate().isAfter(date));
        }

        if (words != null && !words.isEmpty()) {
            // Preprocess words by removing spaces and lower-casing
            List<String> preprocessedWords = words.stream().map(w -> w.toLowerCase().trim()).collect(Collectors.toList());

            // Check if program name contains any of the words
            programStream = programStream.filter(t -> preprocessedWords.stream().anyMatch(w -> t.getName().toLowerCase().trim().contains(w)));
        }

        return programStream.collect(Collectors.toList());
    }

    /**
     * Method that inserts a new program into schedule. Does not insert duplicate programs (same name and time).
     *
     * @param program
     * @return
     */
    public Program create(Program program) {
        Optional<Program> first = schedule.stream().filter(p -> p.getName().equals(program.getName())).findFirst();

        if (isSameDate(program, first)) {

            return null;
        }

        program.setId(generateID());

        schedule.add(program);

        return program;
    }

    /**
     * Method that deletes a program by ID.
     *
     * @param id
     * @return
     */
    public boolean delete(String id) {
        Optional<Program> first = schedule.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (!first.isPresent()) {
            return false;
        }

        schedule.remove(first.get());

        return true;
    }

    public Program update(Program program) {
        Optional<Program> first = schedule.stream().filter(p -> p.getId().equals(program.getId())).findFirst();

        if (!first.isPresent()) {
            return null;
        }

        program.setId(first.get().getId());

        schedule.set(schedule.indexOf(first.get()), program);

        return program;
    }

    public List<Program> getSchedule() {
        return schedule;
    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

    private boolean isSameDate(Program program, Optional<Program> first) {
        return first.isPresent() && first.get().getDate().equals(program.getDate()) && first.get().getStart().equals(program.getStart());
    }
}
