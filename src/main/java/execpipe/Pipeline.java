package execpipe;

import execpipe.exception.ExecPipeException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code ExecutionPipeline} creates a pipeline. Can add tasks to the pipeline and create a job
 * that can be executed.
 *
 * @author Dulanjaya Tennekoon
 */
public class Pipeline<P extends Payload> {
    private final List<Task<P>> tasks;
    private final boolean isReadOnly;

    public Pipeline() {
        this.tasks = new ArrayList<>();
        this.isReadOnly = false;
    }

    protected Pipeline(List<Task<P>> tasks) {
        this.tasks = tasks;
        this.isReadOnly = true;
    }

    /**
     * Creates an execution job for the pipeline
     *
     * @param payload Execution Payload
     * @return New Execution Job
     * @see Job for execute the pipeline
     */
    public Job<P> createExecutionJob(P payload) {
        return new Job<>(payload, this);
    }

    public Pipeline<P> createReadOnlyPipeline() {
        return new Pipeline<>(this.tasks);
    }

    /**
     * Add a task to the pipeline
     *
     * @param clazz class that extends {@code Task}
     * @throws ExecPipeException if it fails to instantiate task object
     */
    public void addTask(Class<? extends Task<P>> clazz) throws ExecPipeException {
        List<Task<P>> tempComponents = new ArrayList<>();

        try {
            tempComponents.add(clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new ExecPipeException(e.getMessage());
        }

        this.tasks.addAll(tempComponents);
    }

    /**
     * Add a task to the pipeline
     *
     * @param task A task to be executed
     * @throws ExecPipeException if the task object is null
     */
    public void addTask(Task<P> task) throws ExecPipeException {
        if (isReadOnly) {
            if (task != null) {
                this.tasks.add(task);
            } else throw new ExecPipeException("Task object is null");
        } else {
            throw new ExecPipeException("Pipeline is read only");
        }
    }

    /**
     * Returns a read only lists of tasks
     *
     * @return a Read only list of tasks
     */
    public List<Task<P>> getTasks() {
        return new ArrayList<>(tasks);
    }
}
