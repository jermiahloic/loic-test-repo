package za.co.bmw.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import za.co.bmw.kanban.model.Kanban;
import za.co.bmw.kanban.repository.KanbanRepository;
import za.co.bmw.kanban.service.KanbanService;
import za.co.bmw.kanban.service.KanbanServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KanbanServiceTest {

    KanbanService kanbanService;
    @Mock
    KanbanRepository kanbanRepository;

    @Before
    public void init() {
        kanbanService = new KanbanServiceImpl(kanbanRepository);
    }

    @Test
    public void when2KanbansInDatabase_thenGetListWithAllOfThem() {
        //given
        mockKanbanInDatabase(2);

        //when
        List<Kanban> kanbans = kanbanService.getAllKanbanBoards();

        //then
        assertEquals(2, kanbans.size());
    }

    @Test
    public void whenKanbanIsRerieved_thenGetCreatedDate() {
        when(kanbanRepository.findById(anyLong())).thenReturn(Optional.of(createKanban()));
        Optional<Kanban> kanbanOptional = kanbanService.getKanbanById(1L);

        if(kanbanOptional.isPresent() ) {
            Kanban kanban = kanbanOptional.get();
            Assert.assertNotNull(kanban.getCreated());
        }
    }

    private void mockKanbanInDatabase(int kanbanCount) {
        when(kanbanRepository.findAll())
                .thenReturn(createKanbanList(kanbanCount));
    }

    private List<Kanban> createKanbanList(int kanbanCount) {
        List<Kanban> kanbans = new ArrayList<>();
        IntStream.range(0, kanbanCount)
                .forEach(number ->{
                    Kanban kanban = new Kanban();
                    kanban.setId(Long.valueOf(number));
                    kanban.setTitle("Kanban " + number);
                    kanban.setTasks(new ArrayList<>());
                    kanbans.add(kanban);
                });
        return kanbans;
    }

    protected Kanban createKanban(){
        Kanban kanban = new Kanban();
        int random = (int)(Math.random() * 100 + 1);
        kanban.setTitle("A Kanban " + random);
        kanban.setTasks(new ArrayList<>());
        return kanban;
    }
}
