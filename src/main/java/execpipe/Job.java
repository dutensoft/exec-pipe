package execpipe;

import execpipe.constants.JobCompletionStatus;
import execpipe.constants.TaskCompletionStatus;
import execpipe.exception.ExecPipeException;

public class Job<P extends Payload> {
    private final Pipeline<P> pipeline;
    private final P payload;
    private JobCompletionStatus jobCompletionStatus;
    private Task<P> failedTask;
    private long createdAt;
    private long startedAt;
    private long endedAt;

    protected Job(P payload, Pipeline<P> pipeline) {
        this.payload = payload;
        this.pipeline = pipeline.createReadOnlyPipeline();
        this.jobCompletionStatus = JobCompletionStatus.READY;
        this.createdAt = System.currentTimeMillis();
    }

    public JobCompletionStatus executePipeline() throws ExecPipeException {
        if (jobCompletionStatus == JobCompletionStatus.READY) {
            this.startedAt = System.currentTimeMillis();
            for (Task<P> task : this.pipeline.getTasks()) {
                try {
                    TaskCompletionStatus status = task.execute(payload);
                    if (status == TaskCompletionStatus.FAILED) {
                        this.jobCompletionStatus = JobCompletionStatus.FAILED;
                        this.failedTask = task;
                    }
                } catch (Exception e) {
                    this.endedAt = System.currentTimeMillis();
                    this.jobCompletionStatus = JobCompletionStatus.FAILED;
                    this.failedTask = task;
                    throw new ExecPipeException(e.getMessage());
                }
            }
            this.endedAt = System.currentTimeMillis();
            this.jobCompletionStatus = JobCompletionStatus.COMPLETED;
            return this.jobCompletionStatus;
        } else {
            throw new ExecPipeException("Job is already executed.");
        }
    }

    public Pipeline<P> getPipeline() {
        return pipeline;
    }

    public P getPayload() {
        return payload;
    }

    public JobCompletionStatus getCompletionStatus() {
        return jobCompletionStatus;
    }

    public Task<P> getFailedTask() {
        return failedTask;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(long endedAt) {
        this.endedAt = endedAt;
    }
}
