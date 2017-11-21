/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package sbt.bit.zaborovskiy;

import entities.MessagesOverseer;
import entities.Topology;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class MyBenchmark {

    public Topology t;

    @Setup(Level.Iteration)
    public void prepareFreshTopology() throws InterruptedException {
        t = Topology.createRing(BenchmarkSettings.TOPOLOGY_SIZE);
        t.start();
    }

    @TearDown(Level.Iteration)
    public void stopTopology() throws InterruptedException {
        t.stop();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void oneToken() throws InterruptedException {
        t.askOperator().sendTokenTo(0);
        while(BenchmarkSettings.MESSAGES_TO_RECEIVE > MessagesOverseer.numberOfMessagesReceived()) {
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    //@Warmup(iterations = 6)
    public void twoTokens() throws InterruptedException {
        t.askOperator().sendTokenTo(0);
        t.askOperator().sendTokenTo(BenchmarkSettings.TOPOLOGY_SIZE / 2);
        while(BenchmarkSettings.MESSAGES_TO_RECEIVE > MessagesOverseer.numberOfMessagesReceived()) {
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    //@Warmup(iterations = 6)
    public void threeTokens() throws InterruptedException {
        t.askOperator().sendTokenTo(0);
        t.askOperator().sendTokenTo(BenchmarkSettings.TOPOLOGY_SIZE / 2);
        t.askOperator().sendTokenTo(BenchmarkSettings.TOPOLOGY_SIZE -1 );
        while(BenchmarkSettings.MESSAGES_TO_RECEIVE > MessagesOverseer.numberOfMessagesReceived()) {
        }
    }

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .warmupIterations(BenchmarkSettings.WARMUP_ITERATIONS)
                .measurementIterations(BenchmarkSettings.MEASUREMENT_ITERATIONS)
                .threads(BenchmarkSettings.THREAD_NUMBER)
                //.forks(1)
                .resultFormat(ResultFormatType.TEXT)
                .result(name())
                .build();

        new Runner(opt).run();
    }

    private static String name() {
        return new StringBuilder()
                .append("m")
                .append(BenchmarkSettings.MEASUREMENT_ITERATIONS)
                .append("w")
                .append(BenchmarkSettings.WARMUP_ITERATIONS)
                .append("top")
                .append(BenchmarkSettings.TOPOLOGY_SIZE)
                .append(".txt")
                .toString();
    }

}
