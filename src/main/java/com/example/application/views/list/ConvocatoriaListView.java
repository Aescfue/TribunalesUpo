package com.example.application.views.list;

import com.example.application.data.Convocatoria;
import com.example.application.data.ConvocatoriaId;
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
@Route(value = "convocatorias", layout = MainLayout.class)
@PageTitle("Convocatorias")
public class ConvocatoriaListView extends VerticalLayout {
    Grid<Convocatoria> grid = new Grid<>(Convocatoria.class);
    TextField filterText = new TextField();
    ConvocatoriaForm form;
    CrmService service;

    public ConvocatoriaListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
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
        form = new ConvocatoriaForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveConvocatoria);
        form.addDeleteListener(this::deleteConvocatoria);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveConvocatoria(ConvocatoriaForm.SaveEvent event) {
        service.guardarConvocatoria(event.getConvocatoria());
        updateList();
        closeEditor();
    }

    private void deleteConvocatoria(ConvocatoriaForm.DeleteEvent event) {
        service.eliminarConvocatoria(event.getConvocatoria());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Convocatoria-grid");
        grid.setSizeFull();
        grid.setColumns("id.curso","id.numero");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editConvocatoria(event.getValue().getId()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Buscar por año..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addConvocatoriaButton = new Button("Añadir convocatoria");
        addConvocatoriaButton.addClickListener(click -> addConvocatoria());

        var toolbar = new HorizontalLayout(filterText, addConvocatoriaButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editConvocatoria(ConvocatoriaId Convocatoria) {
        if (Convocatoria == null) {
            closeEditor();
        } else {
            form.setConvocatoria(Convocatoria);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setConvocatoria(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addConvocatoria() {
        grid.asSingleSelect().clear();
        editConvocatoria(new ConvocatoriaId());
    }


    private void updateList() {
        grid.setItems(service.buscarTodasConvocatorias(filterText.getValue()));
    }
}