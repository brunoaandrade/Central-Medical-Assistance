/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

import entities.PrescriptedAction;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import webServices.GetClass;
import webServices.PostClass;
import subClasses.Prescricoes;

/**
 *
 * @author Bruno
 */
@SessionScoped
public class CalendarioBean implements Serializable {

    private ScheduleModel lazyEventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private String title = " ";
    private Date dateFim = new Date();
    private Date dateInicio = new Date();
    private int repeticoes = 1;
    private List<Prescricoes> prescricoes;
    private final GetClass get = new GetClass();
    private final PostClass post = new PostClass();
    private int idPatient = -1;
    private int idDoctor = -1;
    int idPrescMeasure;
    private CalendarioPresc presc = new CalendarioPresc(-1, -1);
    private final long oneDay = 1 * 24 * 60 * 60 * 1000;

    public CalendarioBean(int doctor, int id) {
        idPatient = id;
        idDoctor = doctor;
        prescricoes = new ArrayList<>();
        prescricoes = get.listaPresc(id);
        if (prescricoes != null && prescricoes.isEmpty()) {
            prescricoes = null;
        }
        lazyEventModel = new DefaultScheduleModel();
        if (prescricoes != null) {
            presc = new CalendarioPresc(doctor, id);

            for (Prescricoes prescricoe : prescricoes) {
                try {
                    String name = prescricoe.getNome() + " -" + prescricoe.getIdAction();
                    String dt = new SimpleDateFormat("dd/MM/yyyy").format(prescricoe.getTimeToStart());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(dt));
                    c.add(Calendar.DAY_OF_MONTH, prescricoe.getPeriod());  // number of days to add
                    c.add(Calendar.MINUTE, -1);
                    Date date = c.getTime();

                    DefaultScheduleEvent x = new DefaultScheduleEvent(name, prescricoe.getTimeToStart(), date);
                    lazyEventModel.addEvent(x);
                } catch (ParseException ex) {
                    Logger.getLogger(CalendarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public Date getDateFim() {
        return dateFim;
    }

    public CalendarioPresc getPresc() {
        return presc;
    }

    public void setPresc(CalendarioPresc presc) {
        this.presc = presc;
    }

    public void setDateFim(Date dataFim) {
        this.dateFim = dataFim;
    }

    public Date getDateInicio() {
        return dateInicio;
    }

    public void setDateInicio(Date dataInicio) {
        this.dateInicio = dataInicio;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        title = event.getTitle() + "  " + event.getDescription();
        String[] parts = event.getTitle().split("-");
        idPrescMeasure = Integer.parseInt(parts[1]);

        List<PrescriptedAction> listPresc = get.prescAction(idDoctor);
        outerloop:
        for (PrescriptedAction pa : listPresc) {
            if (pa.getIdAction() == idPrescMeasure) {
                repeticoes = pa.getNumberreps();
                dateInicio = pa.getTimeToStart();
                dateFim = dateInicio;
                if (pa.getPeriod() > 1) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateFim);
                    c.add(Calendar.DATE, pa.getPeriod() - 1);
                    dateFim = c.getTime();
                }
                break outerloop;
            }
        }

        FacesMessage message;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event Selected", "Event: " + event.getTitle());
        addMessage(message);

    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        /*Date now = new Date();
         try {
         DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh");
         now = df.parse(df.format(now));
         Date eventdate;
         FacesMessage message;
         eventdate = df.parse(df.format(event.getScheduleEvent().getData()));

         if (now.compareTo(eventdate) < 0) {
         update();
         message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Operação Impossivel", "Não pode mover um evento para uma data anterior a: " + now);
         } else {
         int id;
         event.getScheduleEvent().getTitle();
         String[] parts = event.getScheduleEvent().getTitle().split("-");
         id = Integer.parseInt(parts[parts.length - 1]);
         //vai busbar a lista de prescricoes

         //retira a que tem o id
         //cria uma nova
         //elimina a antiga
         boolean successDelete = post.deletePrescription(id);
         if (successDelete) {
         //create new prescripcao 
         //envia prescricao

         message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         } else {
         update();
         message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Ligação", "Não foi possivel mover evento");
         }
         }

         addMessage(message);
         } catch (ParseException ex) {
         Logger.getLogger(CalendarioBean.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public void onDataSelect(SelectEvent event) {
        presc.setDateIWant((Date) (event.getObject()));
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage("calendario", message);
    }

    void update() {
        List<Prescricoes> p;
        p = get.listaPresc(idPatient);
        if (p != null) {
            prescricoes = p;
            lazyEventModel = new DefaultScheduleModel();
            presc = new CalendarioPresc(idDoctor, idPatient);
            for (Prescricoes prescricoe : prescricoes) {
                try {
                    String name = prescricoe.getNome() + " -" + prescricoe.getIdAction();
                    String dt = new SimpleDateFormat("dd/MM/yyyy").format(prescricoe.getTimeToStart());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(dt));
                    c.add(Calendar.DAY_OF_MONTH, prescricoe.getPeriod() - 1);  // number of days to add
                    c.add(Calendar.MINUTE, -1);
                    Date date = c.getTime();
                    DefaultScheduleEvent x = new DefaultScheduleEvent(name, prescricoe.getTimeToStart(), date);
                    lazyEventModel.addEvent(x);
                } catch (ParseException ex) {
                    Logger.getLogger(CalendarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
