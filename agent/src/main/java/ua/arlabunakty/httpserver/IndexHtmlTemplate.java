package ua.arlabunakty.httpserver;

import java.util.Collection;
import ua.arlabunakty.core.domain.HistoryRecord;
import ua.arlabunakty.core.domain.Metric;

class IndexHtmlTemplate {
    private static final String HTML_TEMPLATE_START = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Page Title</title>\n" +
            "<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\" " +
            "       rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
            "<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>\n" +
            "<script src=\"//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.1.0/css/all.css\" " +
            "       integrity=\"sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt\"" +
            "       crossorigin=\"anonymous\">\n" +
            "<div class=\"container\">\n" +
            "    <br/>\n" +
            "\t<div class=\"row justify-content-center\">\n" +
            "                        <div class=\"col-12 col-md-10 col-lg-8\">\n" +
            "                            <form class=\"card card-sm\">\n" +
            "                                <div class=\"card-body row no-gutters align-items-center\">\n" +
            "                                    <div class=\"col-auto\">\n" +
            "                                        <i class=\"fas fa-search h4 text-body\"></i>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"col\">\n" +
            "                                        <input " +
            "       class=\"form-control form-control-lg form-control-borderless\" name=\"" +
            IndexHttpHandler.SEARCH_ID_QUERY_PARAMETER_NAME +
            "\" type=\"search\" placeholder=\"Search history data by Unique Identifier\">\n" +
            "                                    </div>\n" +
            "                                    <div class=\"col-auto\">\n" +
            "                                        <button class=\"btn btn-lg btn-success\" type=\"submit\">" +
            "                                               Search" +
            "                                        </button>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </form>\n" +
            "                        </div>\n" +
            "                        <!--end of col-->\n" +
            "                    </div>\n" +
            "</div>";
    private static final String HTML_TEMPLATE_SEARCH_LIST_TABLE = "<div class=\"search-list\">\n" +
            "                <table class=\"table\" id=\"myTable\">\n" +
            "                    <thead>\n" +
            "                        <tr>\n" +
            "                            <th>Title</th>\n" +
            "                            <th>Value</th>\n" +
            "                        </tr>\n" +
            "                    </thead>\n" +
            "                    <tbody>\n" +
            "                        \n" +
            "                    \n";
    private static final String HTML_TEMPLATE_END =
            "                    </tbody>\n" +
                    "                </table>" +
                    "</body>\n" +
                    "</html>";
    private static final String NO_METRIC_DATA = "";

    String apply(Collection<HistoryRecord> historyRecordData, Metric requestOperationTime,
                 Metric responseBodyLength) {
        return HTML_TEMPLATE_START +
                formatMetric(requestOperationTime) +
                formatMetric(responseBodyLength) +
                HTML_TEMPLATE_SEARCH_LIST_TABLE +
                prepareTableRows(historyRecordData) +
                HTML_TEMPLATE_END;
    }

    private String formatMetric(Metric metric) {
        if (null == metric) {
            return NO_METRIC_DATA;
        }
        return "<h3 id=\"" + metric.getCategory() + "\">" + metric.getCategory() +
                ": min = <span class=\"min\">" + metric.getMin() +
                "</span>, max = <span class=\"max\">" + metric.getMax() +
                "</span>, avg = <span class=\"avg\">" + metric.getAvg() +
                "</span></h3>";
    }

    private String prepareTableRows(Collection<HistoryRecord> historyRecordData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (HistoryRecord model : historyRecordData) {
            stringBuilder.append("<tr>")
                    .append("<td>").append(model.getCategory()).append("</td>")
                    .append("<td class=\"").append(model.getCategory()).append("\">")
                    .append(model.getValue()).append("</td>")
                    .append("</tr>");
        }
        return stringBuilder.toString();
    }
}
