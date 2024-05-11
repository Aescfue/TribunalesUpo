package org.upo.tribunalesupo.views.list;

import org.upo.tribunalesupo.data.Docente;
import org.upo.tribunalesupo.data.Persona;
import org.upo.tribunalesupo.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class DocenteForm extends FormLayout {
    ComboBox<Persona> persona = new ComboBox<>("Persona");
    DatePicker fecha_ingreso = new DatePicker("Fecha de ingreso");
    Select<String> categoria = new Select<>();
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");
    CrmService service;

    BeanValidationBinder<Docente> binder = new BeanValidationBinder<>(Docente.class);

    public DocenteForm(List<Persona> personas, CrmService service) {
            this.service = service;
            addClassName("docente-form");
            categoria.setLabel("Categoria");
            categoria.setItems("CATEDRATICO/A DE UNIVERSIDAD","PROFESOR/A ASOCIADO LOU","PROFESOR/A AYUDANTE DOCTOR","PROFESOR/A COLABORADOR","PROFESOR/A CONTRATADO DOCTOR"
                    ,"PROFESOR/A CONTRATADO DOCTOR TEMPORAL","PROFESOR/A SUSTITUTO INTERINO","PROFESOR/A TITULAR DE UNIVERSIDAD","PROFESOR/A TITULAR ESCUELA UNIVERSITARIA","PROFESOR/A VISITANTE LOU");
            binder.bindInstanceFields(this);
            persona.setItems(personas);
            persona.setItemLabelGenerator(persona -> {
                return persona.getNombre() + " " + persona.getApellidos();
            });
            add(persona,fecha_ingreso,categoria,createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DocenteForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new DocenteForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            binder.getBean().setDni(binder.getBean().getPersona().getDni());
            fireEvent(new DocenteForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setDocente(Docente docente) {
        binder.setBean(docente);
    }

    // Events
    public static abstract class DocenteFormEvent extends ComponentEvent<DocenteForm> {
        private Docente Docente;

        protected DocenteFormEvent(DocenteForm source, Docente Docente) {
            super(source, false);
            this.Docente = Docente;
        }

        public Docente getDocente() {
            return Docente;
        }
    }

    public static class SaveEvent extends DocenteForm.DocenteFormEvent {
        SaveEvent(DocenteForm source, Docente Docente) {
            super(source, Docente);
        }
    }

    public static class DeleteEvent extends DocenteForm.DocenteFormEvent {
        DeleteEvent(DocenteForm source, Docente Docente) {
            super(source, Docente);
        }

    }

    public static class CloseEvent extends DocenteForm.DocenteFormEvent {
        CloseEvent(DocenteForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DocenteForm.DeleteEvent> listener) {
        return addListener(DocenteForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<DocenteForm.SaveEvent> listener) {
        return addListener(DocenteForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<DocenteForm.CloseEvent> listener) {
        return addListener(DocenteForm.CloseEvent.class, listener);
    }

}
