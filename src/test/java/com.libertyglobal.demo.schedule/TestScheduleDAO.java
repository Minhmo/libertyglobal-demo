package com.libertyglobal.demo.schedule;

import com.libertyglobal.demo.schedule.db.ScheduleDAO;
import com.libertyglobal.demo.schedule.model.Program;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestScheduleDAO {

    private final Program program = getProgram("MY PROGRAM");
    private ArrayList<Program> schedule = new ArrayList<>();
    private ScheduleDAO scheduleDAO;

    public TestScheduleDAO() {
        schedule.add(program);
        scheduleDAO = new ScheduleDAO(schedule);

    }

    @Test
    public void testFilteringByDate() {
        List<Program> filtered = scheduleDAO.getByDateAndWords(LocalDate.now().minus(1, ChronoUnit.DAYS), null);

        assertEquals(filtered.size(), 1);

        filtered = scheduleDAO.getByDateAndWords(LocalDate.now().plus(1, ChronoUnit.DAYS), null);

        assertEquals(filtered.size(), 0);
    }

    @Test
    public void testFilteringByWords() {
        List<Program> filtered = scheduleDAO.getByDateAndWords(null, Collections.singletonList("MY"));

        assertEquals(filtered.size(), 1);

        filtered = scheduleDAO.getByDateAndWords(null, Collections.singletonList("TEST"));

        assertEquals(filtered.size(), 0);
    }

    @Test
    public void testCreateProgram() {
        Program program = getProgram("MY PROGRAM 2");

        Program inserted = scheduleDAO.create(program);

        assertNotNull(inserted);

        Program inserted_null = scheduleDAO.create(program);

        assertNull(inserted_null);
    }

    @Test
    public void testUpdateProgram() {
        Program program = scheduleDAO.getSchedule().get(0);

        String newName = "MY PROGRAM UPDATED";

        program.setName(newName);

        Program updated = scheduleDAO.update(program);

        assertNotNull(updated);
        assertEquals(updated.getName(), newName);
    }

    @Test
    public void testRemoveProgram() {
        Program program = scheduleDAO.getSchedule().get(0);

        String newName = "MY PROGRAM UPDATED";

        program.setName(newName);

        boolean deleted = scheduleDAO.delete(program.getId());

        assertTrue(deleted);

        deleted = scheduleDAO.delete(program.getId());

        assertFalse(deleted);
    }

    @Test
    public void testGetProgramList() {
        Set<String> programs = scheduleDAO.getPrograms();

        assertEquals(programs.size(), 1);
        assertTrue(programs.stream().anyMatch(p -> p.equals("MY PROGRAM")));
    }

    private Program getProgram(String name) {
        return new Program(UUID.randomUUID().toString(),
                name,
                "MY CHANNEL",
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now().plus(1, ChronoUnit.HOURS));
    }
}
