package tasks;

import java.util.concurrent.Callable;

public interface PortEcosystemTaskGenerator<T, U, R> {
    Callable<R> generateTask(T taskOwner, U targetForTask);
}
