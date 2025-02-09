package org.upo.tribunalesupo.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.upo.tribunalesupo.services.CrmService;

import java.util.List;

@RolesAllowed("ADMIN")
@Route(value = "estadisticas", layout = MainLayout.class)
@PageTitle("Estadísticas")
public class DashboardView extends VerticalLayout {
    private final CrmService service;

    public DashboardView(CrmService service) {
        this.service = service;
        addClassName("estadisticas-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompaniesChart());
    }

    private Component getContactStats() {
        Span stats = new Span("Docentes que han participado en más Tribunales");
        stats.addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }

    private Chart getCompaniesChart() {

        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(0);
        conf.setTooltip(tooltip);

        DataSeries dataSeries = new DataSeries();


        List<Object[]> lista = service.obtenerDocentesTribunalesEstadistica();
        for (Object[] elemento : lista) {
            dataSeries.add(new DataSeriesItem((String) elemento[0], (Number) elemento[1]));
        }
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}