package org.upo.tribunalesupo.views.list;

import org.upo.tribunalesupo.data.Rol;
import org.upo.tribunalesupo.services.CrmService;
import org.upo.tribunalesupo.views.MainLayout;
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
@Route(value = "roles", layout = MainLayout.class)
@PageTitle("Roles")
public class RolListView extends VerticalLayout {
    Grid<Rol> grid = new Grid<>(Rol.class);
    TextField filterText = new TextField();
    RolForm form;
    CrmService service;

    public RolListView(CrmService service) {
        this.service = service;
        addClassName("RolList-view");
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
        form = new RolForm(service.getTodosUsuarios());
        form.setWidth("25em");
        form.addSaveListener(this::saveRol);
        form.addDeleteListener(this::deleteRol);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveRol(RolForm.SaveEvent event) {
        service.guardarRol(event.getRol());
        updateList();
        closeEditor();
    }

    private void deleteRol(RolForm.DeleteEvent event) {
        service.eliminarRol(event.getRol());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Rol-grid");
        grid.setSizeFull();
        grid.setColumns("codigo", "usuario");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editRol(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Buscar por usuario o rol...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addRolButton = new Button("Añadir Rol");
        addRolButton.addClickListener(click -> addRol());

        var toolbar = new HorizontalLayout(filterText, addRolButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    public void editRol(Rol Rol) {
        if (Rol == null) {
            closeEditor();
        } else {
            form.setRol(Rol);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setRol(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addRol() {
        grid.asSingleSelect().clear();
        editRol(new Rol());
    }


    private void updateList() {
        grid.setItems(service.buscarTodosRoles(filterText.getValue()));
    }
}