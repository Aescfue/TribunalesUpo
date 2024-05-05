package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Tribunales UPO");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        String u = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Cerrar sesión ", e -> securityService.logout());

        var header = new HorizontalLayout(new DrawerToggle(), logo, logout );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Personas", PersonaListView.class),
                new RouterLink("Docentes", DocenteListView.class),
                new RouterLink("Alumnos", AlumnoListView.class),
                new RouterLink("Tfg", TfgListView.class),
                new RouterLink("Tribunales", TribunalListView.class),
                new RouterLink("Convocatorias",ConvocatoriaListView.class),
                new RouterLink("Defensas",DefensaListView.class),
                new RouterLink("Estadísticas", DashboardView.class)
        ));
    }
}