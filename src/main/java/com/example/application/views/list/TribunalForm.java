package com.example.application.views.list;

import com.example.application.data.Convocatoria;
import com.example.application.data.Docente;
import com.example.application.data.Tfg;
import com.example.application.data.Tribunal;
import com.example.application.services.CrmService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class TribunalForm extends FormLayout {
    ComboBox<Docente> docente_1 = new ComboBox<>("Presidente");
    ComboBox<Docente> docente_2 = new ComboBox<>("Vocal");
    ComboBox<Docente> docente_3 = new ComboBox<>("Secretario");
    ComboBox<Convocatoria> convocatoria = new ComboBox<>("Convocatoria");
    ComboBox<Tfg> codigoTfg = new ComboBox<>("TFG");
    DatePicker fecha = new DatePicker("Fecha de evaluación");
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");
    CrmService service;

    BeanValidationBinder<Tribunal> binder = new BeanValidationBinder<>(Tribunal.class);

    public TribunalForm(List<Docente> docentes, List<Tfg> tfgs, List<Convocatoria> convocatorias,CrmService service) {
            this.service = service;
            addClassName("Tribunal-form");
            binder.bindInstanceFields(this);
            docente_1.setItems(docentes);
            docente_1.setItemLabelGenerator(docente -> docente.getPersona().getNombre() + " " + docente.getPersona().getApellidos());
            docente_2.setItems(docentes);
            docente_2.setItemLabelGenerator(docente -> docente.getPersona().getNombre() + " " + docente.getPersona().getApellidos());
            docente_3.setItems(docentes);
            docente_3.setItemLabelGenerator(docente -> docente.getPersona().getNombre() + " " + docente.getPersona().getApellidos());
            convocatoria.setItems(convocatorias);
            convocatoria.setItemLabelGenerator(Convocatoria -> Convocatoria.getId().getCurso() + " " + Convocatoria.getId().getNumero());
            codigoTfg.setItems(tfgs);
            codigoTfg.setItemLabelGenerator(Tfg::getCodigo);
            add(docente_1,docente_2,docente_3,codigoTfg,convocatoria,fecha,createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new TribunalForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new TribunalForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {//Valida docentes distintos y TFG no asignado ya
        if(binder.isValid() && service.docentesValidos(binder.getBean()) == 1) {
            //Valido y se debe insertar, falta ordenar los docentes de acuerdo a la normativa
            //Si existe el id se quiere editar por lo que se le asigna el id que tenía.
            Integer id = tfgAsignado(binder.getBean().getCodigoTFG().getCodigo(), binder.getBean().getConvocatoria().getId().getCurso(), binder.getBean().getConvocatoria().getId().getNumero());
            if (id != null){
                binder.getBean().setId(id);
            }

            //se ordenan los docentes según la normativa
            binder.getBean().ordenarDocentes();

            fireEvent(new TribunalForm.SaveEvent(this, binder.getBean()));
        }else{
            Notification notification = new Notification();
            notification.setDuration(3000);
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);

            Div text;
            text = new Div(new Text("No se puede insertar el mismo docente."));

            if(service.docentesValidos(binder.getBean()) == -1){
                text = new Div(new Text("No se puede insertar el mismo docente."));
            }else{
                text = new Div(new Text("No puede formar parte del tribunal el tutor."));
            }

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.setAriaLabel("Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(FlexComponent.Alignment.CENTER);

            notification.add(layout);
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.open();
        }
    }

    private Integer tfgAsignado(String codigo, Integer curso, Integer numero) {
        List<Tribunal> lista = service.buscarTodosTribunalesCodigo(codigo, curso, numero);
        return lista.get(0).getId();
    }

    public void setTribunal(Tribunal Tribunal) {
        binder.setBean(Tribunal);
    }

    private Boolean validaDocentes(BeanValidationBinder<Tribunal> binder){
        boolean valido = true;

        Docente d1 = binder.getBean().getDocente1();
        Docente d2 = binder.getBean().getDocente2();
        Docente d3 = binder.getBean().getDocente3();

        if(d1.equals(d2) || d1.equals(d3) || d2.equals(d3) ){
            valido = false;
        }
        return valido;
    }

    // Events
    public static abstract class TribunalFormEvent extends ComponentEvent<TribunalForm> {
        private Tribunal Tribunal;

        protected TribunalFormEvent(TribunalForm source, Tribunal Tribunal) {
            super(source, false);
            this.Tribunal = Tribunal;
        }

        public Tribunal getTribunal() {
            return Tribunal;
        }
    }

    public static class SaveEvent extends TribunalForm.TribunalFormEvent {
        SaveEvent(TribunalForm source, Tribunal Tribunal) {
            super(source, Tribunal);
        }
    }

    public static class DeleteEvent extends TribunalForm.TribunalFormEvent {
        DeleteEvent(TribunalForm source, Tribunal Tribunal) {
            super(source, Tribunal);
        }

    }

    public static class CloseEvent extends TribunalForm.TribunalFormEvent {
        CloseEvent(TribunalForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<TribunalForm.DeleteEvent> listener) {
        return addListener(TribunalForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<TribunalForm.SaveEvent> listener) {
        return addListener(TribunalForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<TribunalForm.CloseEvent> listener) {
        return addListener(TribunalForm.CloseEvent.class, listener);
    }

}
