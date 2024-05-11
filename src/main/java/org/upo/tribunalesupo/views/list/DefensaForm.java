package org.upo.tribunalesupo.views.list;

import org.upo.tribunalesupo.data.Defensa;
import org.upo.tribunalesupo.data.Tribunal;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class DefensaForm extends FormLayout {
    ComboBox<Tribunal> tribunal = new ComboBox<>("Tribunal");
    NumberField calidad = new NumberField("Calidad técnica del proyecto desarrollado");
    NumberField adquisicion = new NumberField("Adquisición de las competencias de la asignatura");
    NumberField presentacion = new NumberField("Presentación");
    NumberField defensa = new NumberField("Defensa");
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");

    BeanValidationBinder<Defensa> binder = new BeanValidationBinder<>(Defensa.class);

    public DefensaForm(List<Tribunal> tribunales) {
            addClassName("Defensa-form");
            binder.bindInstanceFields(this);
            tribunal.setItems(tribunales);
            tribunal.setItemLabelGenerator(tribunal -> tribunal.getCodigoTFG().getCodigo() + " " + tribunal.getConvocatoria().getId().getCurso() + " " + tribunal.getConvocatoria().getId().getNumero());
            add(tribunal,calidad,adquisicion,presentacion,defensa,createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DefensaForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new DefensaForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new DefensaForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setDefensa(Defensa Defensa) {
        binder.setBean(Defensa);
    }

    // Events
    public static abstract class DefensaFormEvent extends ComponentEvent<DefensaForm> {
        private Defensa Defensa;

        protected DefensaFormEvent(DefensaForm source, Defensa Defensa) {
            super(source, false);
            this.Defensa = Defensa;
        }

        public Defensa getDefensa() {
            return Defensa;
        }
    }

    public static class SaveEvent extends DefensaForm.DefensaFormEvent {
        SaveEvent(DefensaForm source, Defensa Defensa) {
            super(source, Defensa);
        }
    }

    public static class DeleteEvent extends DefensaForm.DefensaFormEvent {
        DeleteEvent(DefensaForm source, Defensa Defensa) {
            super(source, Defensa);
        }

    }

    public static class CloseEvent extends DefensaForm.DefensaFormEvent {
        CloseEvent(DefensaForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DefensaForm.DeleteEvent> listener) {
        return addListener(DefensaForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<DefensaForm.SaveEvent> listener) {
        return addListener(DefensaForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<DefensaForm.CloseEvent> listener) {
        return addListener(DefensaForm.CloseEvent.class, listener);
    }

}
