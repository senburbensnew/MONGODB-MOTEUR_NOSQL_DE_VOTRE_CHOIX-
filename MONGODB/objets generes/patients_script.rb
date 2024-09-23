require 'mongo'
require 'faker'
require 'json'
require 'date'

# Function to generate a random MongoDB document for a patient
def generate_random_patient
  {
    "_id" => BSON::ObjectId.new,
    "numero_securite_sociale" => Faker::IdNumber.valid,
    "nom" => Faker::Name.last_name,
    "sexe" => ["M", "F", "O"].sample,
    "date_naissance" => Faker::Date.birthday(min_age: 0, max_age: 100),
    "email" => Faker::Internet.email,
    "poids" => Faker::Number.between(from: 45.0, to: 120.0).round(2),
    "hauteur" => Faker::Number.between(from: 1.5, to: 2.0).round(2),
    "list_telephones" => [Faker::PhoneNumber.phone_number, Faker::PhoneNumber.phone_number],
    "list_prenoms" => [Faker::Name.first_name, Faker::Name.first_name],
    "adresse" => {
      "numero" => Faker::Address.building_number,
      "rue" => Faker::Address.street_name,
      "code_postal" => Faker::Address.zip_code,
      "ville" => Faker::Address.city
    },
    "list_rendez_vous" => [
      {
        "medecin" => BSON::ObjectId.new,
        "date_rendez_vous" => Faker::Date.forward(days: 23),
        "motif" => Faker::Lorem.sentence(word_count: 3)
      }
    ],
    "list_consultations" => generate_consultations(Faker),
    "antecedents_medicaux" => generate_antecedents_medicaux(Faker),
    "allergies" => [Faker::Lorem.word, Faker::Lorem.word]
  }
end

# Function to generate consultations for the patient
def generate_consultations(faker)
  consultations_list = []
  
  2.times do
    examens_list = [
      {
        "details_examen" => Faker::Lorem.sentence(word_count: 5),
        "date_examen" => Faker::Date.backward(days: 15)
      }
    ]
    
    prescriptions_list = [
      {
        "details_prescription" => Faker::Lorem.sentence(word_count: 5),
        "date_prescription" => Faker::Date.backward(days: 10)
      }
    ]
    
    facture = {
      "Montant_Total" => faker::Number.between(from: 50.0, to: 500.0).round(2),
      "date_facture" => Faker::Date.backward(days: 7)
    }

    consultations_list << {
      "medecin" => BSON::ObjectId.new,
      "raison" => Faker::Lorem.sentence(word_count: 3),
      "diagnostic" => Faker::Lorem.sentence(word_count: 5),
      "date_consultation" => Faker::Date.backward(days: 30),
      "list_examens" => examens_list,
      "list_prescriptions" => prescriptions_list,
      "facture" => facture
    }
  end

  consultations_list
end

# Function to generate medical history for the patient
def generate_antecedents_medicaux(faker)
  [
    {
      "type" => "Chronic",
      "description" => Faker::Lorem.sentence(word_count: 5),
      "date" => Faker::Date.backward(days: 500)
    },
    {
      "type" => "Acute",
      "description" => Faker::Lorem.sentence(word_count: 5),
      "date" => Faker::Date.backward(days: 300)
    }
  ]
end

# Generate 1000 patient documents
patients = Array.new(1000) { generate_random_patient }

# Optionally, save to a JSON file
File.open("patients_data.json", "w") do |f|
  f.write(JSON.pretty_generate(patients))
end

puts "Generated 1000 MongoDB patient documents!"
