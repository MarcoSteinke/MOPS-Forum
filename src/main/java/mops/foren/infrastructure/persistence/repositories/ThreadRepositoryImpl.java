package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ThreadRepositoryImpl implements IThreadRepository {

    private ThreadJpaRepository threadRepository;

    public ThreadRepositoryImpl(ThreadJpaRepository threadRepository) {
        this.threadRepository = threadRepository;
    }


    @Override
    public List<Thread> getThreadsFromDB(TopicId topicId) {
        return this.threadRepository.findByTopic_Id(topicId.getId()).stream()
                .map(ThreadMapper::mapThreadDtoToThread)
                .collect(Collectors.toList());
    }

    /**
     * This method gets a thread with the given id.
     *
     * @param threadId the id of the wanted thread.
     * @return the tread with the given id.
     */
    @Override
    public Thread getThreadById(ThreadId threadId) {
        ThreadDTO threadDTO = this.threadRepository.findById(threadId.getId()).get();
        return ThreadMapper.mapThreadDtoToThread(threadDTO);
    }
}
