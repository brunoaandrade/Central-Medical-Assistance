/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

/**
 *
 * @author Bruno
 */
import entities.BloodPressureMeasure;
import entities.TemperatureMeasure;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import webServices.GetClass;

@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartBean implements Serializable {

    boolean taExists = false;
    boolean paExists = false;
    boolean graphExit = false;
    private int id;
    GetClass getClass = new GetClass();
    TemperatureMeasure idTMeasure = null;
    BloodPressureMeasure idBPMeasure = null;
    private boolean taExistsofPrescript = false;
    private boolean tpExistsofPrescript = false;

    public ChartBean(int id) {
        this.id = id;
    }

    public LineChartModel getLinearModelTemp(int id) {
        LineChartModel dateModel = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Valores");
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Max");
        // series2.setMarkerStyle("diamond");
        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Min");
        //series3.setMarkerStyle("diamond");
        List<TemperatureMeasure> mylist;
        if (id != -1) {
            mylist = getClass.chartValuesTemp(id);
        } else {
            mylist = null;
        }
        if (mylist == null) {
            series1.set(0, 0);
            series2.set(0, 0);
            series3.set(0, 0);
        } else {
            TemperatureMeasure valorAnalise = null;
            taExists = true;
            for (TemperatureMeasure mylist1 : mylist) {
                Date data = new Date(mylist1.getTimeMeasured().getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd, HH:mm"); //Or whatever format fits best your needs.
                String dateStr = sdf.format(data);
                series2.set(dateStr, 34);
                series1.set(dateStr, mylist1.gettMeasure());
                series3.set(dateStr, 38);
                if ((mylist1.gettMeasure() < 36 || mylist1.gettMeasure() > 38) && ((idTMeasure == null) || mylist1.getIdTMeasure() != idTMeasure.getIdTMeasure() && mylist1.getTimeMeasured().after(idTMeasure.getTimeMeasured()))) {
                    valorAnalise = mylist1;
                    idTMeasure = mylist1;

                }
            }
            if (valorAnalise != null) {
                if (Hours.hoursBetween(new DateTime(valorAnalise.getTimeMeasured()), new DateTime()).getHours() < 10) {
                    showMessage("Valor: " + valorAnalise.gettMeasure() + "         Data: " + valorAnalise.getTimeMeasured(), 1);
                }
            }
        }
        Date data = new Date();

        DateTime dtOrg = new DateTime(data.getTime());
        DateTime dtPlusOne = dtOrg.plusDays(1);
        data = dtPlusOne.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); //Or whatever format fits best your needs.
        dateModel.addSeries(series1);
        dateModel.addSeries(series2);
        dateModel.addSeries(series3);
        dateModel.setTitle("Temperatura");
        dateModel.setZoom(true);
        dateModel.setAnimate(true);
        dateModel.setLegendPosition("se");
        DateAxis axis = new DateAxis();
        axis.setTickAngle(20);
        axis.setMax(sdf.format(data));
        axis.setTickFormat(" %#d %b, %H %M");
        dateModel.getAxes().put(AxisType.X, axis);

        return dateModel;
    }

    public CartesianChartModel getLinearModelTA(int id) {
        LineChartModel dateModel = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Systolic");
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Diastolic");
        //series2.setMarkerStyle("diamond");
        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Cardiac Freq.");
        //series3.setMarkerStyle("diamond");
        List<BloodPressureMeasure> mylist;
        if (id != -1) {
            mylist = getClass.chartValuesBlood(id);
        } else {
            mylist = null;
        }
        if (mylist == null) {
            series1.set(0, 0);
            series2.set(0, 0);
            series3.set(0, 0);
        } else {
            BloodPressureMeasure valorAnalise = null;
            paExists = true;
            for (BloodPressureMeasure mylist1 : mylist) {
                Date data = new Date(mylist1.getTimeMeasured().getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd, HH:mm"); //Or whatever format fits best your needs.
                String dateStr = sdf.format(data);
                series2.set(dateStr, mylist1.getsMeasure());
                series1.set(dateStr, mylist1.getdMeasure());
                series3.set(dateStr, mylist1.getFreqMeasure());
                if ((mylist1.getdMeasure() < 80 || mylist1.getdMeasure() > 90 || mylist1.getsMeasure() < 120 || mylist1.getsMeasure() > 140) && ((idBPMeasure == null) || mylist1.getIdBPMeasure() != idBPMeasure.getIdBPMeasure() && mylist1.getTimeMeasured().after(idBPMeasure.getTimeMeasured()))) {
                    valorAnalise = mylist1;
                    idBPMeasure = mylist1;
                }
            }
            if (valorAnalise != null) {
                if (Hours.hoursBetween(new DateTime(valorAnalise.getTimeMeasured()), new DateTime()).getHours() < 10) {
                    showMessage("Valor Systolic: " + valorAnalise.getdMeasure() + "         Valor Diastolic: " + valorAnalise.getsMeasure() + "           Data: " + valorAnalise.getTimeMeasured(), 2);
                }
            }
        }
        Date data = new Date();
        DateTime dtOrg = new DateTime(data.getTime());
        DateTime dtPlusOne = dtOrg.plusDays(1);
        data = dtPlusOne.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); //Or whatever format fits best your needs.

        dateModel.addSeries(series1);
        dateModel.addSeries(series2);
        dateModel.addSeries(series3);
        dateModel.setTitle("Pressão Arterial");
        dateModel.setZoom(true);
        dateModel.setAnimate(true);
        dateModel.setLegendPosition("se");
        DateAxis axis = new DateAxis();
        axis.setTickAngle(20);
        axis.setMax(sdf.format(data));
        axis.setTickFormat(" %#d %b, %H %M");
        dateModel.getAxes().put(AxisType.X, axis);

        return dateModel;
    }

    public boolean getTaExists() {
        List<TemperatureMeasure> mylist = getClass.chartValuesTemp(id);
        return mylist != null;
    }

    public boolean getPaExists() {
        List<BloodPressureMeasure> mylist = getClass.chartValuesBlood(id);
        return mylist != null;
    }

    public boolean getTaExistsofPrescript() {
        return taExistsofPrescript;
    }

    public boolean getTpExistsofPrescript() {
        return tpExistsofPrescript;
    }

    public boolean getGraphExit() {
        List<BloodPressureMeasure> mylist = getClass.chartValuesBlood(id);
        List<TemperatureMeasure> mylist1 = getClass.chartValuesTemp(id);
        return mylist != null || mylist1 != null;
    }

    public void showMessage(String x, int i) {
        FacesMessage message;
        if (i == 1) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Valores de Temperatura perigosos   ", x);
        } else if (i == 2) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Valores de Pressão Arterial perigosos   ", x);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Valores de Frequencia Cardiaca perigosos   ", x);
        }
        FacesContext.getCurrentInstance().addMessage("warning" + i, message);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public LineChartModel getLinearModelTempofPrescripte(int id, int idPrescriptAct) {
        tpExistsofPrescript = false;
        LineChartModel dateModel = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Valores");
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Max");
        // series2.setMarkerStyle("diamond");
        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Min");
        //series3.setMarkerStyle("diamond");
        List<TemperatureMeasure> mylist;
        if (id != -1) {
            mylist = getClass.chartValuesTemp(id);
        } else {
            mylist = null;
        }
        if (mylist == null) {
            series1.set(0, 0);
            series2.set(0, 0);
            series3.set(0, 0);
        } else {
            for (TemperatureMeasure mylist1 : mylist) {
                if (mylist1.getIdMeasurepresvript() == idPrescriptAct) {
                    Date data = new Date(mylist1.getTimeMeasured().getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd, HH:mm"); //Or whatever format fits best your needs.
                    String dateStr = sdf.format(data);
                    series2.set(dateStr, 34);
                    series1.set(dateStr, mylist1.gettMeasure());
                    series3.set(dateStr, 38);
                    tpExistsofPrescript = true;
                }
            }
        }
        Date data = new Date();

        DateTime dtOrg = new DateTime(data.getTime());
        DateTime dtPlusOne = dtOrg.plusDays(1);
        data = dtPlusOne.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); //Or whatever format fits best your needs.
        dateModel.addSeries(series1);
        dateModel.addSeries(series2);
        dateModel.addSeries(series3);
        dateModel.setTitle("Temperatura");
        dateModel.setZoom(true);
        dateModel.setAnimate(true);
        dateModel.setLegendPosition("se");
        DateAxis axis = new DateAxis();
        axis.setTickAngle(20);
        axis.setMax(sdf.format(data));
        axis.setTickFormat(" %#d %b, %H %M");
        dateModel.getAxes().put(AxisType.X, axis);

        return dateModel;
    }

    public CartesianChartModel getLinearModelTAofPrescripte(int id, int idPrescriptAct) {
        taExistsofPrescript = false;
        LineChartModel dateModel = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Systolic");
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Diastolic");
        //series2.setMarkerStyle("diamond");
        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Cardiac Freq.");
        //series3.setMarkerStyle("diamond");
        List<BloodPressureMeasure> mylist;
        if (id != -1) {
            mylist = getClass.chartValuesBlood(id);
        } else {
            mylist = null;
        }
        if (mylist == null) {
            series1.set(0, 0);
            series2.set(0, 0);
            series3.set(0, 0);
        } else {

            for (BloodPressureMeasure mylist1 : mylist) {
                if (mylist1.getIdMeasureprescript() == idPrescriptAct) {
                    Date data = new Date(mylist1.getTimeMeasured().getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd, HH:mm"); //Or whatever format fits best your needs.
                    String dateStr = sdf.format(data);
                    series2.set(dateStr, mylist1.getsMeasure());
                    series1.set(dateStr, mylist1.getdMeasure());
                    series3.set(dateStr, mylist1.getFreqMeasure());
                    taExistsofPrescript = true;
                }
            }
        }
        Date data = new Date();
        DateTime dtOrg = new DateTime(data.getTime());
        DateTime dtPlusOne = dtOrg.plusDays(1);
        data = dtPlusOne.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); //Or whatever format fits best your needs.

        dateModel.addSeries(series1);
        dateModel.addSeries(series2);
        dateModel.addSeries(series3);
        dateModel.setTitle("Pressão Arterial");
        dateModel.setZoom(true);
        dateModel.setAnimate(true);
        dateModel.setLegendPosition("se");
        DateAxis axis = new DateAxis();
        axis.setTickAngle(20);
        axis.setMax(sdf.format(data));
        axis.setTickFormat(" %#d %b, %H %M");
        dateModel.getAxes().put(AxisType.X, axis);

        return dateModel;
    }

    public void setTaExistsofPrescript(boolean taExistsofPrescript) {
        this.taExistsofPrescript = taExistsofPrescript;
    }

    public void setTpExistsofPrescript(boolean tpExistsofPrescript) {
        this.tpExistsofPrescript = tpExistsofPrescript;
    }

}
