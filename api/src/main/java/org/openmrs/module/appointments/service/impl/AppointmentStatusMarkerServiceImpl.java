package org.openmrs.module.appointments.service.impl;

import org.openmrs.Patient;
import org.openmrs.module.appointments.dao.AppointmentStatusMarkerDao;
import org.openmrs.module.appointments.model.AppointmentService;
import org.openmrs.module.appointments.service.AppointmentStatusMarkerService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AppointmentStatusMarkerServiceImpl implements AppointmentStatusMarkerService {

    private AppointmentStatusMarkerDao appointmentStatusMarkerDao;

    public AppointmentStatusMarkerDao getAppointmentStatusMarkerDao() {
        return appointmentStatusMarkerDao;
    }

    public void setAppointmentStatusMarkerDao(AppointmentStatusMarkerDao appointmentStatusMarkerDao) {
        this.appointmentStatusMarkerDao = appointmentStatusMarkerDao;
    }

    @Override
    public List<String> getAllAppointmentsNotConsultedUuidList(Date startDateTime, Date endDateTime) {
        return appointmentStatusMarkerDao.getAllAppointmentsNotConsultedUuidList(startDateTime, endDateTime);
    }

    @Override
    public List<String> getPatientFutureAppointmentUuidList(Patient patient, AppointmentService appointmentService, Date startDateTime, Date endDateTime) {
        return appointmentStatusMarkerDao.getPatientFutureAppointmentUuidList(patient, appointmentService, startDateTime, endDateTime);
    }

    @Override
    public void markAppoinmentAsComplete(String uuid) {
        appointmentStatusMarkerDao.markAppoinmentAsComplete(uuid);
    }

    @Override
    public void markAllMissedAppointments(Date startDateTime, Date endDateTime) {
        List<String> missedAppointmentsUuid = getAllAppointmentsNotConsultedUuidList(startDateTime, endDateTime);

        String concatenatedUuidList = concatenateUuidList(missedAppointmentsUuid);

        if (!Objects.equals(concatenatedUuidList, ""))
            appointmentStatusMarkerDao.markAllMissedAppointments(concatenatedUuidList);

    }

    @Override
    public void markPatientFutureAppointments(Patient patient, AppointmentService appointmentService, Date startDateTime, Date endDateTime) {
        List<String> patientFutureAppointmentsUuid = getPatientFutureAppointmentUuidList(patient, appointmentService, startDateTime, endDateTime);

        patientFutureAppointmentsUuid.forEach(this::markAppoinmentAsComplete);
    }

    @Override
    public Date schedulertStartDateTime(int minusDays) {
        return Date.from((LocalDate.now().minusDays(minusDays).atStartOfDay()).atZone(ZoneId.systemDefault()).toInstant());
  }

    @Override
    public Date schedulerEndDateTime() {
        return Date.from((LocalDate.now().atTime(LocalTime.of(23, 59, 59, 999000000))).atZone(ZoneId.systemDefault()).toInstant());
    }

    public String concatenateUuidList(List<String> uuidList) {
        String concatenatedUuidList = "";

        int index = 0;
        for (String uuid : uuidList) {
            concatenatedUuidList += "'" + uuid + "'";

            if (index != (uuidList.size() - 1)) {
                concatenatedUuidList += ",";
            }
            index++;
        }

        return concatenatedUuidList;
    }
}
