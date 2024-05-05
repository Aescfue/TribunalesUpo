package com.example.application.views.list;

import com.example.application.data.Alumno;
import com.example.application.data.Persona;
import com.example.application.services.CrmService;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class AlumnoForm extends FormLayout {
    ComboBox<Persona> persona = new ComboBox<>("Persona");
    DatePicker fecha_ingreso = new DatePicker("Fecha de ingreso");
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");
    CrmService service;

    BeanValidationBinder<Alumno> binder = new BeanValidationBinder<>(Alumno.class);

    public AlumnoForm() {}

    public AlumnoForm(List<Persona> personas, CrmService service) {
            addClassName("alumno-form");
            this.service=service;
            binder.bindInstanceFields(this);
            persona.setItems(personas);
            persona.setItemLabelGenerator(persona -> {
                return persona.getNombre() + " " + persona.getApellidos();
            });
            add(persona,fecha_ingreso,
                    createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new AlumnoForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new AlumnoForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            binder.getBean().setDni(binder.getBean().getPersona().getDni());
            fireEvent(new AlumnoForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setAlumno(Alumno Alumno) {
        binder.setBean(Alumno);
    }

    // Events
    public static abstract class AlumnoFormEvent extends ComponentEvent<AlumnoForm> {
        private Alumno Alumno;

        protected AlumnoFormEvent(AlumnoForm source, Alumno Alumno) {
            super(source, false);
            this.Alumno = Alumno;
        }

        public Alumno getAlumno() {
            return Alumno;
        }
    }

    public static class SaveEvent extends AlumnoForm.AlumnoFormEvent {
        SaveEvent(AlumnoForm source, Alumno Alumno) {
            super(source, Alumno);
        }
    }

    public static class DeleteEvent extends AlumnoForm.AlumnoFormEvent {
        DeleteEvent(AlumnoForm source, Alumno Alumno) {
            super(source, Alumno);
        }

    }

    public static class CloseEvent extends AlumnoForm.AlumnoFormEvent {
        CloseEvent(AlumnoForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<AlumnoForm.DeleteEvent> listener) {
        return addListener(AlumnoForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<AlumnoForm.SaveEvent> listener) {
        return addListener(AlumnoForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<AlumnoForm.CloseEvent> listener) {
        return addListener(AlumnoForm.CloseEvent.class, listener);
    }

}
