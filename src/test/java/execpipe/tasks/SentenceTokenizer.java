package execpipe.tasks;

import execpipe.Task;
import execpipe.constants.TaskCompletionStatus;
import execpipe.model.NLUPayload;

public class SentenceTokenizer implements Task<NLUPayload> {

    public TaskCompletionStatus execute(NLUPayload payload) {
        if (payload != null && payload.getSentence() != null) {
            // split the sentence into words (by space)
            payload.setTokens(payload.getSentence().split(" "));
            return TaskCompletionStatus.SUCCESS;
        }

        return TaskCompletionStatus.FAILED;
    }
}
