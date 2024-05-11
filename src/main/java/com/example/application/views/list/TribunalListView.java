package com.example.application.views.list;

import com.example.application.data.Tribunal;
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

import java.time.LocalDate;

@PermitAll
@Route(value = "tribunales", layout = MainLayout.class)
@PageTitle("Tribunales")
public class TribunalListView extends VerticalLayout {
    Grid<Tribunal> grid = new Grid<>(Tribunal.class);
    TextField anno = new TextField();
    TextField curso = new TextField("Curso para gen. aut.");
    TextField numero = new TextField("Número para gen. aut.");
    Button boton = new Button("Generación aleatoria");
    TribunalForm form;
    CrmService service;

    public TribunalListView(CrmService service) {
        this.service = service;
        addClassName("TribunalesList-view");
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
        form = new TribunalForm(service.buscarTodosDocentes(null), service.buscarTodosTfgs(null), service.buscarTodasConvocatorias(null) ,service );
        form.setWidth("25em");
        form.addSaveListener(this::guardaTribunal);
        form.addDeleteListener(this::eliminaTribunal);
        form.addCloseListener(e -> closeEditor());
    }

    private void guardaTribunal(TribunalForm.SaveEvent event) {
        service.guardaTribunal(event.getTribunal());
        updateList();
        closeEditor();
    }

    private void eliminaTribunal(TribunalForm.DeleteEvent event) {
        service.eliminaTribunal(event.getTribunal());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Tribunal-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.setColumns("fecha");
        grid.addColumn(Tribunal -> (Tribunal.getDocente1().getPersona().getNombre() + " " + Tribunal.getDocente1().getPersona().getApellidos())).setHeader("Presidente");
        grid.addColumn(Tribunal -> (Tribunal.getDocente2().getPersona().getNombre() + " " + Tribunal.getDocente2().getPersona().getApellidos())).setHeader("Vocal");
        grid.addColumn(Tribunal -> (Tribunal.getDocente3().getPersona().getNombre() + " " + Tribunal.getDocente3().getPersona().getApellidos())).setHeader("Secretario");
        grid.addColumn(Tfg -> (Tfg.getCodigoTFG().getCodigo())).setHeader("TFG");
        grid.addColumn(Tribunal -> (Tribunal.getConvocatoria().getId().getCurso())).setHeader("Convocatoria");
        grid.addColumn(Tribunal -> (Tribunal.getConvocatoria().getId().getNumero() )).setHeader("Número convocatoria");
        grid.setColumnOrder(grid.getColumns().get(1),grid.getColumns().get(2),grid.getColumns().get(3),grid.getColumns().get(4),grid.getColumns().get(0),grid.getColumns().get(5),grid.getColumns().get(6));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editTribunal(event.getValue()));}

    private Component getToolbar() {
        anno.setPlaceholder("Buscar por año: ...");
        anno.setClearButtonVisible(true);
        anno.setValueChangeMode(ValueChangeMode.LAZY);
        anno.setValue(String.valueOf(LocalDate.now().getYear()-1));
        anno.addValueChangeListener(e -> updateList());

        boton.addClickListener(click ->{
            service.generarTribunalesAleatorios(curso.getValue(),numero.getValue());
            updateList();
        });

        Button addTribunalButton = new Button("Gestión manual de tribunales");
        addTribunalButton.addClickListener(click -> addTribunal());

        var toolbar = new HorizontalLayout(anno, addTribunalButton, boton, curso, numero);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editTribunal(Tribunal Tribunal) {
        if (Tribunal == null) {
            closeEditor();
        } else {
            form.setTribunal(Tribunal);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setTribunal(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addTribunal() {
        grid.asSingleSelect().clear();
        editTribunal(new Tribunal());
    }

    private void updateList() {
        if(anno.isEmpty()){
            grid.setItems(service.buscarTodosTribunales());
        }else{
            grid.setItems(service.buscarTribunalesAnno(anno.getValue()) );
        }
    }
}