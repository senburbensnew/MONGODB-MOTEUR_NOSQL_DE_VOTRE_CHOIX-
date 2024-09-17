CREATE TABLE Medecin (
    numero_securite_sociale TEXT PRIMARY KEY,
    nom TEXT,
    sexe TEXT,
    date_naissance DATE,
    specialite TEXT,
    email TEXT,
    cv TEXT,
    adresse_numero INT,
    adresse_rue TEXT,
    adresse_code_postal INT,
    adresse_ville TEXT,
    list_telephones LIST<TEXT>,
    list_prenoms LIST<TEXT>,
    rendez_vous_patient_id TEXT,  -- Reference to Patient by numero_securite_sociale
    rendez_vous_date DATE,
    rendez_vous_motif TEXT,
    consultation_patient_id TEXT, -- Reference to Patient by numero_securite_sociale
    consultation_raison TEXT,
    consultation_diagnostic TEXT,
    consultation_date DATE,
    examen_details TEXT,
    examen_date DATE,
    prescription_details TEXT,
    prescription_date DATE
);


CREATE TABLE Patient (
    numero_securite_sociale TEXT PRIMARY KEY,
    nom TEXT,
    sexe TEXT,
    date_naissance DATE,
    email TEXT,
    poids FLOAT,
    hauteur FLOAT,
    list_telephones LIST<TEXT>,
    list_prenoms LIST<TEXT>,
    adresse_numero INT,
    adresse_rue TEXT,
    adresse_code_postal INT,
    adresse_ville TEXT,
    rendez_vous_medecin_id TEXT,  -- Reference to Medecin by numero_securite_sociale
    rendez_vous_date DATE,
    rendez_vous_motif TEXT,
    consultation_medecin_id TEXT, -- Reference to Medecin by numero_securite_sociale
    consultation_raison TEXT,
    consultation_diagnostic TEXT,
    consultation_date DATE,
    examen_details TEXT,
    examen_date DATE,
    prescription_details TEXT,
    prescription_date DATE,
    facture_montant_total FLOAT,
    facture_date DATE,
    antecedentsMedicaux LIST<FROZEN<MAP<TEXT, TEXT>>>, -- For antecedent type, description, and date
    allergies LIST<TEXT>
);
