package org.openmrs.module.appointments.dao;

import org.openmrs.Patient;
import org.openmrs.module.appointments.model.Appointment;
import org.openmrs.module.appointments.model.AppointmentService;

import java.util.Date;
import java.util.List;

public interface AppointmentStatusMarkerDao {

    List<String> getAllAppointmentsNotConsultedUuidList(Date startDateTime, Date endDateTime);

    List<String> getPatientFutureAppointmentUuidList(Patient patient, AppointmentService appointmentService, Date startDate);

    void markAppoinmentAsComplete(String uuid);

    void markAllMissedAppointments(String concatenatedUuidList);
}
