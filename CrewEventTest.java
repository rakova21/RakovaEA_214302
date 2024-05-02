import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventContTest {

    @Test
    void testEvents() {
        EventRepository eventRepo = Mockito.mock(EventRepository.class);
        Model model = new ExtendedModelMap();

        EventCont controller = new EventCont();
        controller.eventRepo = eventRepo;

        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("Event 1");
        event1.setDate("2022-01-01");
        event1.setStatus(EventStatus.IN_PROGRESS);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("Event 2");
        event2.setDate("2022-02-01");
        event2.setStatus(EventStatus.COMPLETED);

        List<Event> expectedEvents = Arrays.asList(event1, event2);
        EventStatus[] expectedStatuses = EventStatus.values();

        when(eventRepo.findAll()).thenReturn(expectedEvents);

        String result = controller.events(model);

        assertEquals("event", result);
        assertEquals(expectedEvents, model.getAttribute("events"));
        assertEquals(expectedStatuses, model.getAttribute("statuses"));

        verify(eventRepo).findAll();
    }

    @Test
    void testAdd() {
        EventRepository eventRepo = Mockito.mock(EventRepository.class);
        EventCont controller = new EventCont();
        controller.eventRepo = eventRepo;

        String eventName = "New Event";
        String eventDate = "2022-05-01";
        EventStatus eventStatus = EventStatus.IN_PROGRESS;

        String result = controller.add(eventName, eventDate, eventStatus);

        assertEquals("redirect:/events", result);
        verify(eventRepo).save(new Event(eventName, eventDate, eventStatus));
    }

    @Test
    void testEdit() {
        EventRepository eventRepo = Mockito.mock(EventRepository.class);
        EventCont controller = new EventCont();
        controller.eventRepo = eventRepo;

        Long eventId = 1L;
        String eventName = "Updated Event";
        String eventDate = "2022-05-01";
        EventStatus eventStatus = EventStatus.COMPLETED;

        Event event = new Event();
        event.setId(eventId);

        when(eventRepo.getReferenceById(eventId)).thenReturn(event);

        String result = controller.edit(eventName, eventDate, eventStatus, eventId);

        assertEquals("redirect:/events", result);
        verify(event).set(eventName, eventDate, eventStatus);
        verify(eventRepo).save(event);
    }

    @Test
    void testDelete() {
        EventRepository eventRepo = Mockito.mock(EventRepository.class);
        EventCont controller = new EventCont();
        controller.eventRepo = eventRepo;

        Long eventId = 1L;

        String result = controller.delete(eventId);

        assertEquals("redirect:/events", result);
        verify(eventRepo).deleteById(eventId);
    }
}