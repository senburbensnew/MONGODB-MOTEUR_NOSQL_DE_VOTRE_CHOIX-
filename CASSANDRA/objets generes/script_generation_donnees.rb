require 'json'
require 'securerandom'
require 'date'

# Helper functions
def random_date(start_date, end_date)
  rand(start_date..end_date).to_s
end

def random_text(length)
  ('a'..'z').to_a.sample(length).join
end

def random_sex
  ['Male', 'Female'].sample
end

def random_phone
  "+#{rand(1000000000..9999999999)}"
end

def random_address
  { "numero" => rand(1..100).to_s, "street" => random_text(10), "city" => random_text(6), "postal_code" => rand(10000..99999).to_s }
end

def random_specialty
  ['Cardiology', 'Neurology', 'Pediatrics', 'Orthopedics', 'Dermatology'].sample
end

def random_motif
  ['Routine Checkup', 'Emergency Visit', 'Follow-up', 'Consultation'].sample
end

# Generating 1000 records for Medecin_By_Speciality
medecin_data = 1000.times.map do
  {
    "id" => SecureRandom.uuid,
    "nom" => random_text(8),
    "sexe" => random_sex,
    "date_naissance" => random_date(Date.new(1960, 1, 1), Date.new(1995, 12, 31)),
    "specialite" => random_specialty,
    "email" => "#{random_text(5)}@domain.com",
    "cv" => random_text(20),
    "adresse" => random_address,
    "list_telephones" => [random_phone, random_phone],
    "list_prenoms" => [random_text(6), random_text(5)]
  }
end

# Generating 1000 records for Patient_By_BirthDay
patient_data = 1000.times.map do
  {
    "id" => SecureRandom.uuid,
    "numero_securite_sociale" => rand(1000000000..9999999999).to_s,
    "nom" => random_text(8),
    "sexe" => random_sex,
    "date_naissance" => random_date(Date.new(1960, 1, 1), Date.new(2005, 12, 31)),
    "email" => "#{random_text(5)}@domain.com",
    "poids" => rand(50.0..100.0).round(2),
    "hauteur" => rand(150.0..200.0).round(2),
    "list_telephones" => [random_phone, random_phone],
    "list_prenoms" => [random_text(6), random_text(5)],
    "adresse" => random_address,
    "allergies" => [random_text(5), random_text(6)]
  }
end

# Generating 1000 records for RendezVous_By_Date
rendezvous_data = 1000.times.map do
  {
    "rendezvous_date" => random_date(Date.new(2020, 1, 1), Date.new(2024, 12, 31)),
    "patient_id" => SecureRandom.uuid,
    "doctor_id" => SecureRandom.uuid,
    "motif" => random_motif
  }
end

# Generating 1000 records for Consultation_By_Date
consultation_data = 1000.times.map do
  {
    "consultation_date" => random_date(Date.new(2020, 1, 1), Date.new(2024, 12, 31)),
    "patient_id" => SecureRandom.uuid,
    "doctor_id" => SecureRandom.uuid,
    "raison" => random_motif,
    "diagnostic" => random_text(20),
    "facture" => { "montant_total" => "#{rand(100..1000)} USD", "date_facture": random_date(Date.new(2020, 1, 1), Date.new(2024, 12, 31))},
    "prescriptions" => 3.times.map { { "details_prescription" => random_text(20), "date_prescription" => random_date(Date.new(2020, 1, 1), Date.new(2024, 12, 31))  } },
    "examens" => 2.times.map { { "details_examen" => random_text(20) , "date_examen" => random_date(Date.new(2020, 1, 1), Date.new(2024, 12, 31))  } }
  }
end

# Writing to JSON files
File.write('Medecin_By_Speciality.json', JSON.pretty_generate(medecin_data))
File.write('Patient_By_BirthDay.json', JSON.pretty_generate(patient_data))
File.write('RendezVous_By_Date.json', JSON.pretty_generate(rendezvous_data))
File.write('Consultation_By_Date.json', JSON.pretty_generate(consultation_data))

puts "Files generated successfully!"
