package org.openmrs.module.appointments.service;

import org.openmrs.Patient;
import org.openmrs.module.appointments.model.Appointment;
import org.openmrs.module.appointments.model.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AppointmentStatusMarkerService {

    List<String> getAllAppointmentsNotConsultedUuidList(Date startDateTime, Date endDateTime);

    List<String> getPatientFutureAppointmentUuidList(Patient patient, AppointmentService appointmentService, Date startDateTime);

    void markAppoinmentAsComplete(String uuid);

    void markAllMissedAppointments(Date startDateTime, Date endDateTime);

    void markPatientFutureAppointments(Patient patient, AppointmentService appointmentService, Date startDateTime);

    Date schedulertStartDateTime(int minusDays);

    Date schedulerEndDateTime();

    String concatenateUuidList(List<String> uuidList);
}
