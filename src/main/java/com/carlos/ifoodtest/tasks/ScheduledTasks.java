package com.carlos.ifoodtest.tasks;

import com.carlos.ifoodtest.types.PlaylistType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void updateTracks() {

        for (PlaylistType playlistType : PlaylistType.values()) {
            jmsTemplate.convertAndSend("update-tracks-by-name", playlistType);
        }

    }
}
