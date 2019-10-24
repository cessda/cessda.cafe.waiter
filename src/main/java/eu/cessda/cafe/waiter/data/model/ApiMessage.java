package eu.cessda.cafe.waiter.data.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ApiMessage {
    @NonNull String message;
}
