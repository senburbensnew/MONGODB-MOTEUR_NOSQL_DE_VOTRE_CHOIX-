package org.example.services;

import com.datastax.oss.driver.api.core.CqlSession;
import org.example.entities.Patient;

public class PatientService {
    private CqlSession session;

    public PatientService(CqlSession session) {
        this.session = session;
    }
}
