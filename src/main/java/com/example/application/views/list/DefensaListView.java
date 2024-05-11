package com.example.application.views.list;

import com.example.application.data.Defensa;
import com.example.application.services.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@PermitAll
@Route(value = "defensas", layout = MainLayout.class)
@PageTitle("Defensas")
public class DefensaListView extends VerticalLayout {
    Grid<Defensa> grid = new Grid<>(Defensa.class);
    DefensaForm form;
    CrmService service;

    public DefensaListView(CrmService service) {
        this.service = service;
        addClassName("DefensasList-view");
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
        form = new DefensaForm(service.buscarTodosTribunales() );
        form.setWidth("25em");
        form.addSaveListener(this::guardaDefensa);
        form.addDeleteListener(this::eliminaDefensa);
        form.addCloseListener(e -> closeEditor());
    }

    private void guardaDefensa(DefensaForm.SaveEvent event) {
        service.guardaDefensa(event.getDefensa());
        updateList();
        closeEditor();
    }

    private void eliminaDefensa(DefensaForm.DeleteEvent event) {
        service.eliminaDefensa(event.getDefensa());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Defensa-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.setColumns("calidad","adquisicion", "presentacion", "defensa");
        grid.addColumn(Defensa -> (Defensa.getTribunal().getCodigoTFG().getCodigo() + " " + Defensa.getTribunal().getCodigoTFG().getNombre() )).setHeader("TFG");
        grid.addColumn(Defensa -> (Defensa.getTribunal().getConvocatoria().getId().getCurso())).setHeader("Convocatoria");
        grid.addColumn(Defensa -> (Defensa.getTribunal().getConvocatoria().getId().getNumero() )).setHeader("Número convocatoria");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, defensa) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        modificarGrid();
                    });
                    button.setIcon(new Icon(VaadinIcon.FILE_ADD ));
                })).setHeader("Generar Acta");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editDefensa(event.getValue()));}

    private Component getToolbar() {
        Button addDefensaButton = new Button("Añadir rúbrica");
        addDefensaButton.addClickListener(click -> addDefensa());

        var toolbar = new HorizontalLayout(addDefensaButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editDefensa(Defensa Defensa) {
        if (Defensa == null) {
            closeEditor();
        } else {
            form.setDefensa(Defensa);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setDefensa(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addDefensa() {
        grid.asSingleSelect().clear();
        editDefensa(new Defensa());
    }

    private void updateList() {
        grid.setItems(service.buscarTodasDefensas(null) );
    }

    private void modificarGrid() {
        // Configuración de las columnas existentes...

        grid.addColumn(new ComponentRenderer<>(defensa -> {
            Button button = new Button("Descargar");
            button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            button.setIcon(new Icon(VaadinIcon.DOWNLOAD));

            try {
                File f = service.generarActa(defensa);
                FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                        new StreamResource(f.getName(), () -> {
                            try {
                                grid.removeColumn(grid.getColumns().get(grid.getColumns().size()-1));
                                ByteArrayInputStream bytes = new ByteArrayInputStream(Files.readAllBytes(service.generarActa(defensa).toPath()));
                                return bytes;
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        })
                );
                buttonWrapper.wrapComponent(button);
                return buttonWrapper;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })).setHeader("Descargar Acta");
    }
}