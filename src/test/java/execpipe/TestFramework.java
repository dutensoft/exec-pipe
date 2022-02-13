package execpipe;

import execpipe.constants.JobCompletionStatus;
import execpipe.exception.ExecPipeException;
import execpipe.model.NLUPayload;
import execpipe.tasks.SentencePreprocessor;
import execpipe.tasks.SentenceTokenizer;
import org.junit.Assert;
import org.junit.Test;

public class TestFramework {

    @Test
    public void testPipeline() throws ExecPipeException {
        // instantiate a pipeline object with NLU Payload as the payload
        // NLU Payload is implemented using Payload interface
        Pipeline<NLUPayload> pipeline = new Pipeline<>();

        // register components in the pipeline one by one providing the class
        pipeline.addTask(SentencePreprocessor.class);
        pipeline.addTask(SentenceTokenizer.class);

        // execute the pipeline
        Job<NLUPayload> job = pipeline.createExecutionJob(new NLUPayload("This is a test run"));
        JobCompletionStatus status = job.executePipeline();
        Assert.assertEquals(JobCompletionStatus.COMPLETED, status);
        Assert.assertEquals(5, job.getPayload().getTokens().length);
    }

}
