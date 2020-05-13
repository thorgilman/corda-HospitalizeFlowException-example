package com.example.flow

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowExternalOperation
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.HospitalizeFlowException
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.ProgressTracker
import okhttp3.OkHttpClient
import java.io.IOException

@StartableByRPC
class FlowThatPerformsSuspendableOperation : FlowLogic<String>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call(): String {
        val asyncOp = RetrieveBitcoinReadme()
        return await(asyncOp)
    }
}

internal class RetrieveBitcoinReadme : FlowExternalOperation<String> {
    override fun execute(deduplicationId: String): String {
        val client = OkHttpClient()
        val url = "https://raw.githubusercontent.com/bitcoin/bitcoin/master/doc/README.md"
        return try {
            client.newCall(
                    okhttp3.Request.Builder()
                            .url(url)
                            .get()
                            .build())
                    .execute()
                    .body()!!
                    .string()
        } catch (e: IOException) {
            throw HospitalizeFlowException("External API call failed", e)
        }
    }
}