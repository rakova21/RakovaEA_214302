import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}