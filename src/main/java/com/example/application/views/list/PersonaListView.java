package com.example.application.views.list;

import com.example.application.data.Persona;
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
@Route(value = "", layout = MainLayout.class)
@PageTitle("Personas")
public class PersonaListView extends VerticalLayout {
    Grid<Persona> grid = new Grid<>(Persona.class);
    TextField filterText = new TextField();
    PersonaForm form;
    CrmService service;

    public PersonaListView(CrmService service) {
        this.service = service;
        addClassName("PersonaList-view");
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
        form = new PersonaForm();
        form.setWidth("25em");
        form.addSaveListener(this::savePersona);
        form.addDeleteListener(this::deletePersona);
        form.addCloseListener(e -> closeEditor());
    }

    private void savePersona(PersonaForm.SaveEvent event) {
        service.guardarPersona(event.getPersona());
        updateList();
        closeEditor();
    }

    private void deletePersona(PersonaForm.DeleteEvent event) {
        service.eliminarPersona(event.getPersona());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Persona-grid");
        grid.setSizeFull();
        grid.setColumns("nombre", "apellidos", "dni", "correoElectronico","telefono","fechaNacimiento","usuario","contrasena");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editPersona(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Buscar por nombre...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPersonaButton = new Button("Añadir Persona");
        addPersonaButton.addClickListener(click -> addPersona());

        Button addRol = new Button("Añadir rol");
        addRol.addClickListener(click -> addRol());

        var toolbar = new HorizontalLayout(filterText, addPersonaButton, addRol);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRol() {
        getUI().ifPresent(ui -> ui.navigate("roles"));
    }

    public void editPersona(Persona Persona) {
        if (Persona == null) {
            closeEditor();
        } else {
            form.setPersona(Persona);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPersona(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addPersona() {
        grid.asSingleSelect().clear();
        editPersona(new Persona());
    }


    private void updateList() {
        grid.setItems(service.buscarTodasPersonas(filterText.getValue()));
    }
}