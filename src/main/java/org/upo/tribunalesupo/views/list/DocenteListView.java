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
import jakarta.annotation.security.RolesAllowed;
import org.upo.tribunalesupo.data.Docente;
import org.upo.tribunalesupo.services.CrmService;
import org.upo.tribunalesupo.views.MainLayout;

@RolesAllowed("ADMIN")
@Route(value = "docentes", layout = MainLayout.class)
@PageTitle("Docentes")
public class DocenteListView extends VerticalLayout {
    Grid<Docente> grid = new Grid<>(Docente.class);
    TextField filterText = new TextField();

    DocenteForm form;
    CrmService service;

    public DocenteListView(CrmService service) {
        this.service = service;
        addClassName("DocenteList-view");
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
        form = new DocenteForm(service.buscarTodasPersonas(null),service );
        form.setWidth("25em");
        form.addSaveListener(this::guardaDocente);
        form.addDeleteListener(this::eliminaDocente);
        form.addCloseListener(e -> closeEditor());
    }

    private void guardaDocente(DocenteForm.SaveEvent event) {
        service.guardaDocente(event.getDocente());
        updateList();
        closeEditor();
    }

    private void eliminaDocente(DocenteForm.DeleteEvent event) {
        service.eliminaDocente(event.getDocente());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Docente-grid");
        grid.setSizeFull();
        grid.setColumns("fechaIngreso","categoria");
        grid.addColumn(Docente -> (Docente.getPersona().getNombre() + " " + Docente.getPersona().getApellidos())).setHeader("Docente");
        grid.setColumnOrder(grid.getColumns().get(2),grid.getColumns().get(0),grid.getColumns().get(1));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editDocente(event.getValue()));}

    private Component getToolbar() {
        filterText.setPlaceholder("Buscar por nombre: ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addDocenteButton = new Button("Añadir Docente");
        addDocenteButton.addClickListener(click -> addDocente());

        var toolbar = new HorizontalLayout(filterText, addDocenteButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editDocente(Docente Docente) {
        if (Docente == null) {
            closeEditor();
        } else {
            form.setDocente(Docente);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setDocente(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addDocente() {
        grid.asSingleSelect().clear();
        editDocente(new Docente());
    }


    private void updateList() {
        grid.setItems(service.buscarTodosDocentes(filterText.getValue()));
    }
}