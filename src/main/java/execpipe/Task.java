package execpipe;

import execpipe.constants.TaskCompletionStatus;

public interface Task<P extends Payload> {
    TaskCompletionStatus execute(P payload);
}
