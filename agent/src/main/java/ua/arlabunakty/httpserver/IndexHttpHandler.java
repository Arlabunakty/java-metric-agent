package ua.arlabunakty.httpserver;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.core.model.MetricModel;
import ua.arlabunakty.core.model.WebCategoryEnum;
import ua.arlabunakty.core.service.MetricService;
import ua.arlabunakty.core.service.ServiceFactory;
import ua.arlabunakty.core.service.StatsService;

class IndexHttpHandler {
    static final String SEARCH_ID_QUEARY_PARAMETER_NAME = "id";

    private final StatsService statsService;
    private final MetricService metricService;
    private final IndexHtmlTemplate indexHtmlTemplate = new IndexHtmlTemplate();

    IndexHttpHandler(StatsService statsService, MetricService metricService) {
        Objects.requireNonNull(statsService, "statsService should be non null");
        Objects.requireNonNull(metricService, "metricService should be non null");

        this.statsService = statsService;
        this.metricService = metricService;
    }

    static IndexHttpHandler getInstance() {
        return IndexHttpHandlerHolder.INDEX_HTTP_HANDLER;
    }

    HttpHandler indexHttpHandler() {
        return (exchange) -> {
            Collection<HistoryDataModel> historyData = findHistoryDataByQueryParameter(exchange);

            exchange.setStatusCode(200);
            exchange.getResponseHeaders()
                    .put(Headers.CONTENT_TYPE, "text/html");
            exchange.getResponseSender()
                    .send(prepareHtmlPage(historyData));
        };
    }

    private String prepareHtmlPage(Collection<HistoryDataModel> historyData) {
        MetricModel requestOperationTime =
                metricService.getMetricByCategory(WebCategoryEnum.REQUEST_OPERATION_TIME.getId());
        MetricModel responseBodyLength =
                metricService.getMetricByCategory(WebCategoryEnum.RESPONSE_BODY_LENGTH.getId());

        return indexHtmlTemplate.apply(historyData, requestOperationTime, responseBodyLength);
    }

    private Collection<HistoryDataModel> findHistoryDataByQueryParameter(HttpServerExchange exchange) {
        Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
        if (queryParameters.containsKey(SEARCH_ID_QUEARY_PARAMETER_NAME)) {
            String searchId = queryParameters.get(SEARCH_ID_QUEARY_PARAMETER_NAME).getFirst();
            return statsService.findByTag(searchId);
        }
        return Collections.emptyList();
    }

    private static class IndexHttpHandlerHolder {

        private static final IndexHttpHandler INDEX_HTTP_HANDLER =
                new IndexHttpHandler(ServiceFactory.getInstance().getStatsService(),
                        ServiceFactory.getInstance().getMetricService());
    }
}
