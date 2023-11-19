package ru.qwerty.schedulerbot.core.util;

import lombok.extern.slf4j.Slf4j;
import ru.qwerty.schedulerbot.exception.InternalException;

import java.util.concurrent.CompletableFuture;

@Slf4j
public final class WaitUtils {

    private WaitUtils() {
    }

    public static <T> T get(CompletableFuture<T> completableFuture) {
        try {
            return completableFuture.get();
        } catch (InterruptedException e) {
            log.error("Failed to get completable future", e);
            Thread.currentThread().interrupt();
            throw new InternalException();
        } catch (Exception e) {
            log.error("Failed to get completable future", e);
            throw new InternalException();
        }
    }
}
