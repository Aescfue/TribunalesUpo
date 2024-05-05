package com.example.application.views.list;

import com.example.application.data.Convocatoria;
import com.example.application.data.ConvocatoriaId;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

public class ConvocatoriaForm extends FormLayout {
    IntegerField curso = new IntegerField("Curso");
    IntegerField numero = new IntegerField("Número Convocatoria");

    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");
    BeanValidationBinder<ConvocatoriaId> binder = new BeanValidationBinder<>(ConvocatoriaId.class);

    public ConvocatoriaForm() {
        addClassName("Convocatoria-form");
        binder.bindInstanceFields(this);
        add(curso,numero,createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new ConvocatoriaForm.DeleteEvent(this, new Convocatoria(binder.getBean()))));
        close.addClickListener(event -> fireEvent(new ConvocatoriaForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new ConvocatoriaForm.SaveEvent(this, new Convocatoria(binder.getBean())));
        }
    }

    public void setConvocatoria(ConvocatoriaId Convocatoria) {
        binder.setBean(Convocatoria);
    }

    // Eventos
    public static abstract class ConvocatoriaFormEvent extends ComponentEvent<ConvocatoriaForm> {
        private Convocatoria Convocatoria;

        protected ConvocatoriaFormEvent(ConvocatoriaForm source, Convocatoria Convocatoria) {
            super(source, false);
            this.Convocatoria = Convocatoria;
        }

        public Convocatoria getConvocatoria() {
            return Convocatoria;
        }
    }

    public static class SaveEvent extends ConvocatoriaForm.ConvocatoriaFormEvent {
        SaveEvent(ConvocatoriaForm source, Convocatoria Convocatoria) {super(source, Convocatoria);
        }
    }

    public static class DeleteEvent extends ConvocatoriaForm.ConvocatoriaFormEvent {
        DeleteEvent(ConvocatoriaForm source, Convocatoria Convocatoria) {
            super(source, Convocatoria);
        }

    }

    public static class CloseEvent extends ConvocatoriaForm.ConvocatoriaFormEvent {
        CloseEvent(ConvocatoriaForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<ConvocatoriaForm.DeleteEvent> listener) {
        return addListener(ConvocatoriaForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<ConvocatoriaForm.SaveEvent> listener) {
        return addListener(ConvocatoriaForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<ConvocatoriaForm.CloseEvent> listener) {
        return addListener(ConvocatoriaForm.CloseEvent.class, listener);
    }
}
