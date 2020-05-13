package com.example.flow;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.utilities.ProgressTracker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@StartableByRPC
public class FlowThatPerformsSuspendableOperation extends FlowLogic<String> {
    private final ProgressTracker progressTracker = new ProgressTracker();

    @Override
    public ProgressTracker getProgressTracker() { return progressTracker; }

    @Override
    @Suspendable
    public String call() {
        RetrieveBitcoinReadme asyncOp = new RetrieveBitcoinReadme();
        return await(asyncOp);
    }
}

class RetrieveBitcoinReadme implements FlowExternalOperation<String> {
    @NotNull
    @Override
    public String execute(@NotNull String deduplicationId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://raw.githubusercontent.com/bitcoin/bitcoin/master/doc/README.md";

        try {
            return client.newCall(
                    new Request.Builder()
                        .url(url)
                        .get()
                        .build())
                    .execute()
                    .body()
                    .string();
        } catch (IOException e) {
            throw new HospitalizeFlowException("External API call failed", e);
        }
    }
}