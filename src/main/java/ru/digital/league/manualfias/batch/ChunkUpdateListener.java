package ru.digital.league.manualfias.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChunkUpdateListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
    }

    @Override
    public void afterChunk(ChunkContext context) {
        int count = context.getStepContext().getStepExecution().getReadCount();
        String stepName = context.getStepContext().getStepName();
        Long updateId = (Long)context.getStepContext().getJobParameters().get("updateId");
        log.info("Processed {} entities in '{}' updateId {}", count, stepName, updateId);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("Error during update");
    }
}
