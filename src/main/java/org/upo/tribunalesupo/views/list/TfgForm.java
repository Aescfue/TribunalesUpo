package org.upo.tribunalesupo.views.list;

import org.upo.tribunalesupo.data.Alumno;
import org.upo.tribunalesupo.data.Convocatoria;
import org.upo.tribunalesupo.data.Docente;
import org.upo.tribunalesupo.data.Tfg;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class TfgForm extends FormLayout {
    ComboBox<Alumno> alumno = new ComboBox<>("Alumno");
    ComboBox<Convocatoria> convocatoria = new ComboBox<>("Convocatoria");
    ComboBox<Docente> docente1 = new ComboBox<>("Tutor");
    ComboBox<Docente> docente2 = new ComboBox<>("Tutor 2");
    TextField codigo = new TextField("Código");
    TextField nombre = new TextField("Nombre");
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");

    BeanValidationBinder<Tfg> binder = new BeanValidationBinder<>(Tfg.class);

    public TfgForm(List<Alumno> alumnos, List<Convocatoria> convocatorias, List<Docente> docentes) {
            addClassName("Tfg-form");
            binder.bindInstanceFields(this);
            alumno.setItems(alumnos);
            alumno.setItemLabelGenerator(Alumno -> Alumno.getPersona().getNombre() + " " + Alumno.getPersona().getApellidos());
            convocatoria.setItems(convocatorias);
            convocatoria.setItemLabelGenerator(Convocatoria -> Convocatoria.getId().getCurso() + " " + Convocatoria.getId().getNumero());
            docente1.setItems(docentes);
            docente1.setItemLabelGenerator(Docente -> Docente.getPersona().getNombre() + " " + Docente.getPersona().getApellidos());
            docente2.setItems(docentes);
            docente2.setItemLabelGenerator(Docente -> Docente.getPersona().getNombre() + " " + Docente.getPersona().getApellidos());
            add(alumno,convocatoria, docente1, docente2,codigo,nombre,createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new TfgForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new TfgForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            if(!binder.getBean().getDocente1().equals(binder.getBean().getDocente2())) {
                fireEvent(new TfgForm.SaveEvent(this, binder.getBean()));
            }else{
                Notification notification = new Notification();
                notification.setDuration(3000);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);

                Div text;
                text = new Div(new Text("No se puede insertar el mismo docente."));

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
    }

    public void setTfg(Tfg Tfg) {
        binder.setBean(Tfg);
    }

    // Events
    public static abstract class TfgFormEvent extends ComponentEvent<TfgForm> {
        private Tfg Tfg;

        protected TfgFormEvent(TfgForm source, Tfg Tfg) {
            super(source, false);
            this.Tfg = Tfg;
        }

        public Tfg getTfg() {
            return Tfg;
        }
    }

    public static class SaveEvent extends TfgForm.TfgFormEvent {
        SaveEvent(TfgForm source, Tfg Tfg) {
            super(source, Tfg);
        }
    }

    public static class DeleteEvent extends TfgForm.TfgFormEvent {
        DeleteEvent(TfgForm source, Tfg Tfg) {
            super(source, Tfg);
        }

    }

    public static class CloseEvent extends TfgForm.TfgFormEvent {
        CloseEvent(TfgForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<TfgForm.DeleteEvent> listener) {
        return addListener(TfgForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<TfgForm.SaveEvent> listener) {
        return addListener(TfgForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<TfgForm.CloseEvent> listener) {
        return addListener(TfgForm.CloseEvent.class, listener);
    }

}
