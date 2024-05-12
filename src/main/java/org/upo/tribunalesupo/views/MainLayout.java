package org.upo.tribunalesupo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.upo.tribunalesupo.security.SecurityService;
import org.upo.tribunalesupo.views.list.*;

import java.util.Collection;

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
        Button cambiarTema = new Button();
        cambiarTema.setIcon(VaadinIcon.MOON.create());
        cambiarTema.addClickListener (event -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();

            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });

        var header = new HorizontalLayout(new DrawerToggle(), logo, cambiarTema, logout );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void createDrawer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        if(roles.contains(new SimpleGrantedAuthority("ROLE_ALU")) || roles.contains(new SimpleGrantedAuthority("ROLE_DOC")) || roles.isEmpty()) {
            addToDrawer(new VerticalLayout(
                    new RouterLink("Personas", PersonaListView.class),
                    new RouterLink("Tfg", TfgListView.class),
                    new RouterLink("Tribunales", TribunalListView.class)
            ));
        }else{
            addToDrawer(new VerticalLayout(
                    new RouterLink("Personas", PersonaListView.class),
                    new RouterLink("Docentes", DocenteListView.class),
                    new RouterLink("Alumnos", AlumnoListView.class),
                    new RouterLink("Tfg", TfgListView.class),
                    new RouterLink("Tribunales", TribunalListView.class),
                    new RouterLink("Convocatorias",ConvocatoriaListView.class),
                    new RouterLink("Defensas", DefensaListView.class),
                    new RouterLink("Estadísticas", DashboardView.class)
            ));
        }

    }
}