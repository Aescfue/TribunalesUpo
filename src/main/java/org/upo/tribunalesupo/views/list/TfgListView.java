package org.upo.tribunalesupo.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.upo.tribunalesupo.data.ComparadorCodigoTfg;
import org.upo.tribunalesupo.data.Persona;
import org.upo.tribunalesupo.data.Tfg;
import org.upo.tribunalesupo.services.CrmService;
import org.upo.tribunalesupo.views.MainLayout;

import java.util.List;

@PermitAll
@Route(value = "tfg", layout = MainLayout.class)
@PageTitle("TFG")
public class TfgListView extends VerticalLayout {
    Grid<Tfg> grid = new Grid<>(Tfg.class);
    TextField filterText = new TextField();

    TfgForm form;
    CrmService service;
    Authentication rolesAut = SecurityContextHolder.getContext().getAuthentication();

    public TfgListView(CrmService service) {
        this.service = service;
        addClassName("TfgList-view");
        setSizeFull();
        configureGrid();

        if(!rolesAut.getAuthorities().isEmpty() && !rolesAut.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ALU")) && !rolesAut.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOC"))) {
            configureForm();
            add(getToolbar(), getContent());
            updateList();
            closeEditor();
        }else{
            add(getContent());
            Persona p = service.buscarPersona(rolesAut.getName()).get(0);
            updateList(p);
        }
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content;
        if(rolesAut.getAuthorities().isEmpty() || rolesAut.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ALU")) || rolesAut.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOC"))) {
            content = new HorizontalLayout(grid);
            content.setFlexGrow(2, grid);
        }else{
            content = new HorizontalLayout(grid, form);
            content.setFlexGrow(2, grid);
            content.setFlexGrow(1, form);
        }
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new TfgForm(service.buscarTodosAlumno(null), service.buscarTodasConvocatorias(null), service.buscarTodosDocentes(null));
        form.setWidth("25em");
        form.addSaveListener(this::guardaTfg);
        form.addDeleteListener(this::eliminaTfg);
        form.addCloseListener(e -> closeEditor());
    }

    private void guardaTfg(TfgForm.SaveEvent event) {
        service.guardaTfg(event.getTfg());
        updateList();
        closeEditor();
    }

    private void eliminaTfg(TfgForm.DeleteEvent event) {
        service.eliminaTfg(event.getTfg());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Tfg-grid");
        grid.setSizeFull();
        grid.setColumns("codigo","nombre");
        grid.addColumn(Tfg -> (Tfg.getAlumno().getPersona().getNombre() + " " + Tfg.getAlumno().getPersona().getApellidos())).setHeader("Alumno");
        grid.addColumn(Tfg -> (Tfg.getConvocatoria().getId().getCurso())).setHeader("Convocatoria");
        grid.addColumn(Tfg -> (Tfg.getConvocatoria().getId().getNumero() )).setHeader("Número conv");
        grid.addColumn(Tfg -> (Tfg.getDocente1().getPersona().getNombre() + " " + Tfg.getDocente1().getPersona().getApellidos() )).setHeader("Tutor");
        grid.addColumn(Tfg -> (Tfg.getDocente2() == null ? "" : Tfg.getDocente2().getPersona().getNombre() + " " + Tfg.getDocente2().getPersona().getApellidos() )).setHeader("Tutor 2");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editTfg(event.getValue()));}

    private Component getToolbar() {
        filterText.setPlaceholder("Buscar por código: ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addTfgButton = new Button("Añadir Tfg");
        addTfgButton.addClickListener(click -> addTfg());

        var toolbar = new HorizontalLayout(filterText, addTfgButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editTfg(Tfg Tfg) {
        if (Tfg == null) {
            closeEditor();
        } else {
            form.setTfg(Tfg);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setTfg(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addTfg() {
        grid.asSingleSelect().clear();
        editTfg(new Tfg());
    }

    private void updateList() {
        List<Tfg> lista =service.buscarTodosTfgs(filterText.getValue());
        ComparadorCodigoTfg c = new ComparadorCodigoTfg();
        lista.sort(c);
        grid.setItems(lista);
    }

    private void updateList(Persona p) {
        List<Tfg> lista =service.buscarTfgPersona(p);
        ComparadorCodigoTfg c = new ComparadorCodigoTfg();
        lista.sort(c);
        grid.setItems(lista);
    }
}