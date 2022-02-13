package execpipe.tasks;

import execpipe.Task;
import execpipe.constants.TaskCompletionStatus;
import execpipe.model.NLUPayload;

public class SentencePreprocessor implements Task<NLUPayload> {

    public TaskCompletionStatus execute(NLUPayload payload) {
        if (payload != null && payload.getSentence() != null) {
            payload.setSentence(
                    payload.getSentence().trim()
            );
            return TaskCompletionStatus.SUCCESS;
        }

        return TaskCompletionStatus.FAILED;
    }
}
