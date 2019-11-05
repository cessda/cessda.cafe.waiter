package eu.cessda.cafe.waiter.data.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Class to hold messages sent to other Coffee API components
 */
@Data
public class ApiMessage {

    // Prevent messages from being modified after creation
    /**
     * The message to store
     */
    @NonNull
    private final String message;

    /**
     * Collected jobs message
     *
     * @param jobsCollected    Jobs collected
     * @param jobsNotCollected Jobs not collected
     * @return The message
     */
    public static ApiMessage collectJobMessage(int jobsCollected, int jobsNotCollected) {
        String message = jobsCollected + " job(s) collected" + ", still waiting for " + jobsNotCollected + " job(s).";
        return new ApiMessage(message);
    }

    public static ApiMessage Healthy() {
        return new ApiMessage("Ok");
    }
}
