package com.example.application.views.list;

import com.example.application.data.Alumno;
import com.example.application.services.CrmService;
import com.example.application.views.MainLayout;
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

@PermitAll
@Route(value = "alumnos", layout = MainLayout.class)
@PageTitle("Alumnos")
public class AlumnoListView extends VerticalLayout {
    Grid<Alumno> grid = new Grid<>(Alumno.class);
    TextField filterText = new TextField();

    AlumnoForm form;
    CrmService service;

    public AlumnoListView(CrmService service) {
        this.service = service;
        addClassName("AlumnoList-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new AlumnoForm(service.buscarTodasPersonas(null),service );
        form.setWidth("25em");
        form.addSaveListener(this::guardaAlumno);
        form.addDeleteListener(this::eliminaAlumno);
        form.addCloseListener(e -> closeEditor());
    }

    private void guardaAlumno(AlumnoForm.SaveEvent event) {
        service.guardaAlumno(event.getAlumno());
        updateList();
        closeEditor();
    }

    private void eliminaAlumno(AlumnoForm.DeleteEvent event) {
        service.eliminaAlumno(event.getAlumno());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Alumno-grid");
        grid.setSizeFull();
        grid.setColumns("fechaIngreso");
        grid.addColumn(Alumno -> (Alumno.getPersona().getNombre() + " " + Alumno.getPersona().getApellidos())).setHeader("Alumno");
        grid.setColumnOrder(grid.getColumns().get(1),grid.getColumns().get(0));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editAlumno(event.getValue()));}

    private Component getToolbar() {
        filterText.setPlaceholder("Buscar por nombre: ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addAlumnoButton = new Button("Añadir Alumno");
        addAlumnoButton.addClickListener(click -> addAlumno());

        var toolbar = new HorizontalLayout(filterText, addAlumnoButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editAlumno(Alumno Alumno) {
        if (Alumno == null) {
            closeEditor();
        } else {
            form.setAlumno(Alumno);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setAlumno(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addAlumno() {
        grid.asSingleSelect().clear();
        editAlumno(new Alumno());
    }

    private void updateList() {
        grid.setItems(service.buscarTodosAlumno(filterText.getValue()));
    }
}