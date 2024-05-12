package org.upo.tribunalesupo.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;
import org.upo.tribunalesupo.data.Rol;

import java.util.List;
public class RolForm extends FormLayout {
    ComboBox<String> usuarios = new ComboBox<>("Usuario");
    Select<String> codigo = new Select<>();

    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");
    BeanValidationBinder<Rol> binder = new BeanValidationBinder<>(Rol.class);

    public RolForm(List<String> personas) {
        addClassName("Rol-form");
        binder.bindInstanceFields(this);
        usuarios.setItems(personas);
        usuarios.setItemLabelGenerator(String::toString);
        codigo.setItems("ADMIN","ALU","DOC");
        add(usuarios,
                codigo,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new RolForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new RolForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            Rol r = new Rol();
            r.setUsuario(this.usuarios.getValue());
            r.setCodigo(this.codigo.getValue());
            fireEvent(
                    new RolForm.SaveEvent(this, r)
            );
        }
    }

    public void setRol(Rol Rol) {
        binder.setBean(Rol);
    }

    // Eventos
    public static abstract class RolFormEvent extends ComponentEvent<RolForm> {
        private Rol Rol;

        protected RolFormEvent(RolForm source, Rol Rol) {
            super(source, false);
            this.Rol = Rol;
        }

        public Rol getRol() {
            return Rol;
        }
    }

    public static class SaveEvent extends RolForm.RolFormEvent {
        SaveEvent(RolForm source, Rol Rol) {super(source, Rol);
        }
    }

    public static class DeleteEvent extends RolForm.RolFormEvent {
        DeleteEvent(RolForm source, Rol Rol) {
            super(source, Rol);
        }

    }

    public static class CloseEvent extends RolForm.RolFormEvent {
        CloseEvent(RolForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<RolForm.DeleteEvent> listener) {
        return addListener(RolForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<RolForm.SaveEvent> listener) {
        return addListener(RolForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<RolForm.CloseEvent> listener) {
        return addListener(RolForm.CloseEvent.class, listener);
    }
}
