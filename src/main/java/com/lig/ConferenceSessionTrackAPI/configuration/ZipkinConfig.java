package com.lig.ConferenceSessionTrackAPI.configuration;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.StrictCurrentTraceContext;
import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
public class ZipkinConfig {

    @Bean
    public Sender zipkinSender() {
        return OkHttpSender.create("http://localhost:9411/api/v2/spans");
    }

    @Bean
    public AsyncReporter<Span> spanReporter(Sender sender) {
        return AsyncReporter.create(sender);
    }

    @Bean
    public Tracing tracing(AsyncReporter<zipkin2.Span> spanReporter) {
        CurrentTraceContext currentTraceContext = StrictCurrentTraceContext.create();
        return Tracing.newBuilder()
                .localServiceName("your-service-name")
                .currentTraceContext(currentTraceContext)
                .spanReporter(spanReporter)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build();
    }

    @Bean
    public HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }
}
